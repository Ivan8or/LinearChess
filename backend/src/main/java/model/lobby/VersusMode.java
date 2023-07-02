package model.lobby;

import chess.ChessGame;
import chess.board.LSide;
import model.mappings.Inventory;
import model.mappings.Item;
import model.mappings.Session;
import model.mappings.SlottedItem;
import model.shop.ItemShop;
import model.shop.ShopView;
import util.JsonConverter;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VersusMode {

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    final private ChessLobby lobby;

    final private ChessGame chessGame;
    final private ItemShop shop;
    final private int shopTimeMillis = 45000;
    final private int playDelayMillis = 500;

    private GamePhase phase;

    private int round;

    final private Map<Session, LSide> players;
    final private Map<LSide, Inventory> spectatableInventories;

    final private Map<Session, Inventory> inventories;
    final private Map<Session, GameWebSocket> sockets;

    final private Set<Integer> EVAL_SLOTS = Set.of(100,101,102,103);
    final private Set<Integer> MULT_SLOTS = Set.of(200,201,202,203);
    final private Set<Integer> SHOP_SLOTS = Set.of(500,501,502);

    public VersusMode(Session white, Session black, ChessLobby lobby) {
        this.lobby = lobby;
        chessGame = new ChessGame();
        shop = new ItemShop(List.of(new Item("eval", 1001)));
        players = Map.of(white, LSide.WHITE, black, LSide.BLACK);
        sockets = Map.of(white, new GameWebSocket(), black, new GameWebSocket());
        inventories = new HashMap<>(Map.of(white, new Inventory(), black, new Inventory()));
        spectatableInventories = new HashMap<>(Map.of(LSide.WHITE, new Inventory(), LSide.BLACK, new Inventory()));
        round = 0;
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

    public LSide getSide(Session player) {
        return players.get(player);
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

    public void phaseChange(GamePhase newPhase) {
        synchronized(this) {
            phase = newPhase;
            for (GameWebSocket socket : sockets.values())
                socket.send(JsonConverter.toPrettyJson(phase));
        }
    }

    public void boardChange() {
        for(GameWebSocket socket : sockets.values())
            socket.send(JsonConverter.toPrettyJson(chessGame.getBoard().getFen()));
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
        phaseChange(GamePhase.SHOP);
        scheduler.schedule(() -> playPhase(shopTime, playDelay), shopTime, TimeUnit.MILLISECONDS);
    }

    public void playPhase(int shopTime, int playDelay) {
        phaseChange(GamePhase.PLAY);
        synchronized(this) {
            for(Session player : players.keySet()) {
                LSide playerSide = players.get(player);
                Inventory playerInventory = inventories.get(player);
                spectatableInventories.put(playerSide, playerInventory);
            }
        }
        for(int i = 0; i < 10; i++) {
            if(!chessGame.isOver()) {
                chessGame.increment();
                boardChange();
            }
        }
        scheduler.schedule(() -> progressRound(shopTime, playDelay), playDelay, TimeUnit.MILLISECONDS);
    }
}
