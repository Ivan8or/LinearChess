package model.lobby;

import chess.ChessGame;
import chess.board.LSide;
import chess.eval.ChessEval;
import model.mappings.*;
import model.shop.ItemShop;
import model.shop.ShopView;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VersusMode {

    final private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    final private static ExecutorService executor = Executors.newCachedThreadPool();

    final private Set<Integer> EVAL_SLOTS = Set.of(100,101,102,103);
    final private int[] EVAL_SLOTS_ARRAY = {100,101,102,103};

    final private Set<Integer> MULT_SLOTS = Set.of(200,201,202,203);
    final private int[] MULT_SLOTS_ARRAY = {200,201,202,203};

    final private Set<Integer> SHOP_SLOTS = Set.of(500,501,502);

    final private int shopTimeMillis = 45000;
    final private int playDelayMillis = 1000;

    final private ChessLobby lobby;
    final private ChessGame chessGame;
    final private ItemShop shop;

    private int round;
    private GamePhase phase;
    private long phaseStarted;

    final private Map<Session, LSide> players;
    final private Map<LSide, Inventory> spectatableInventories;

    final private Map<Session, Inventory> inventories;



    public VersusMode(Session white, Session black, ChessLobby lobby) {
        this.lobby = lobby;
        chessGame = new ChessGame();

        String itemPoolJson = ResourceAsString.at("model/shop/itemPool.json").get();
        ItemPool[] shopPool = JsonConverter.fromJson(itemPoolJson, ItemPool[].class).get();
        shop = new ItemShop(shopPool);

        players = Map.of(white, LSide.WHITE, black, LSide.BLACK);
        inventories = new HashMap<>(Map.of(white, new Inventory(), black, new Inventory()));
        spectatableInventories = new HashMap<>(Map.of(LSide.WHITE, new Inventory(), LSide.BLACK, new Inventory()));
        round = 0;
        phase = GamePhase.STANDBY;
        phaseStarted = System.currentTimeMillis();
    }

    public ChessGame getChess() {
        return chessGame;
    }

    public boolean isOver() {
        return chessGame.isOver();
    }

    public int getRound() {
        return round;
    }

    public boolean canChangeInventory() {
        return phase == GamePhase.SHOP;
    }

    public Inventory getSpectatableInventory(LSide side) {
        return spectatableInventories.get(side);
    }

    public Inventory getInventory(Session player) {
        return inventories.get(player);
    }

    public ShopView getShop(Session player) {
        return shop.playerView(player);
    }

    public GamePhase getPhase() {
        return phase;
    }

    public int getShopTime() {
        return shopTimeMillis;
    }

    public int getPhaseTimeLeft() {
        long curTime = System.currentTimeMillis();
        long phaseTime = phase == GamePhase.PLAY ? playDelayMillis : shopTimeMillis;
        return (int) (phaseTime + phaseStarted - curTime);
    }

    public LSide getSide(Session player) {
        return players.get(player);
    }

    public boolean modifyInventory(Session player, MoveItem move) {
        return modifyInventory(player, move.slotFrom(), move.slotTo());
    }

    public boolean modifyInventory(Session player, int slotFrom, int slotTo) {
        if(!canChangeInventory())
            return false;

        if(slotFrom == slotTo)
            return false;

        Inventory playerInventory = inventories.get(player);
        if(playerInventory.getSlot(slotTo).isPresent())
            return false;

        if( (EVAL_SLOTS.contains(slotFrom) && EVAL_SLOTS.contains(slotTo))
        ||  (MULT_SLOTS.contains(slotFrom) && MULT_SLOTS.contains(slotTo)) ) {
            Inventory newInventory = playerInventory.moveItem(slotFrom, slotTo);
            inventories.put(player, newInventory);
            return true;
        }
        return false;
    }

    public boolean purchaseItem(Session player, int shopSlotFrom, int slotTo) {
        if(!canChangeInventory())
            return false;

        if(!SHOP_SLOTS.contains(shopSlotFrom))
            return false;

        Inventory playerInventory = inventories.get(player);
        if(playerInventory.getSlot(slotTo).isPresent())
            return false;

        ShopView shop = getShop(player);
        Optional<SlottedItem> shopSlot = shop.getWares().getSlot(shopSlotFrom);
        if(shopSlot.isEmpty())
            return false;

        if(!shop.canAffordBuy(shopSlotFrom))
            return false;

        SlottedItem ware = shopSlot.get();
        if( (ware.item().type().equals("eval") && EVAL_SLOTS.contains(slotTo))
        ||  (ware.item().type().equals("multiplier") && MULT_SLOTS.contains(slotTo)) ) {
            shop.buy(shopSlotFrom);
            Inventory newInventory = playerInventory.addItem(new SlottedItem(slotTo, ware.item()));
            inventories.put(player, newInventory);
            return true;
        }
        return false;
    }

    public boolean sellItem(Session player, int slotSold) {
        if(!canChangeInventory())
            return false;

        Inventory playerInventory = inventories.get(player);
        Optional<SlottedItem> slotToSell = playerInventory.getSlot(slotSold);
        if(slotToSell.isEmpty())
            return false;

        getShop(player).sell(slotToSell.get());
        Inventory newInventory = playerInventory.removeItem(slotSold);
        inventories.put(player, newInventory);
        return true;
    }

    public boolean restockShop(Session player) {
        if(!canChangeInventory())
            return false;

        return getShop(player).restockWares();
    }

    public void phaseChange(GamePhase newPhase) {
        synchronized(this) {
            phase = newPhase;
            lobby.getSocket().sendAll(Map.of(
                    "key", "phase",
                    "value", JsonConverter.toPrettyJson(phase)));
            phaseStarted = System.currentTimeMillis();
        }
    }

    public void boardChange() {
        lobby.getSocket().sendAll(Map.of(
                "key", "board",
                "value", chessGame.getBoard().getFen()));
    }

    public void progressRound() {
        progressRound(shopTimeMillis, playDelayMillis);
    }

    public void progressRound(int shopTime, int playDelay) {

        if(chessGame.isOver()) {
            phaseChange(GamePhase.STANDBY);
            lobby.end();
            return;
        }
        round++;

        for(Session player : players.keySet())
            shop.playerView(player).refreshShopStatus();

        phaseChange(GamePhase.SHOP);
        scheduler.schedule(() -> playPhase(shopTime, playDelay), shopTime, TimeUnit.MILLISECONDS);
    }

    public void playPhase(int shopTime, int playDelay) {
        synchronized(this) {
            phaseChange(GamePhase.PLAY);
            for (Session player : players.keySet()) {
                LSide playerSide = players.get(player);
                Inventory playerInventory = inventories.get(player);
                spectatableInventories.put(playerSide, playerInventory);
            }
        }

        ChessEval whiteEval = spectatableInventories.get(LSide.WHITE).translate(EVAL_SLOTS_ARRAY, MULT_SLOTS_ARRAY, LSide.WHITE);
        ChessEval blackEval = spectatableInventories.get(LSide.BLACK).translate(EVAL_SLOTS_ARRAY, MULT_SLOTS_ARRAY, LSide.BLACK);

        chessGame.setWhiteEval(whiteEval);
        chessGame.setBlackEval(blackEval);

        executor.submit(() -> {
            for(int i = 0; i < 10; i++) {
                if(!chessGame.isOver()) {
                    synchronized (chessGame) {
                        chessGame.increment();
                    }
                    boardChange();
                }
            }
            scheduler.schedule(() -> progressRound(shopTime, playDelay), playDelay, TimeUnit.MILLISECONDS);
        });
    }
}
