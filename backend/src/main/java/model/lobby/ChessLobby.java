package model.lobby;

import chess.ChessGame;
import model.mappings.Inventory;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.session.SessionTracker;
import model.shop.ItemShop;
import model.shop.ShopView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChessLobby {

    private final LobbyID lobbyId;
    final private ItemShop shop;
    private ChessGame game;

    private Map<Session, Inventory> players;
    private SessionTracker allSessions;

    public ChessLobby(LobbyID lobbyId, SessionTracker allSessions) {
        this.lobbyId = lobbyId;
        this.allSessions = allSessions;

        this.shop = new ItemShop(List.of());
        this.game = new ChessGame();
        this.players = new ConcurrentHashMap<>();
    }

    public LobbyID getLobbyId() {
        return lobbyId;
    }

    public ItemShop getShop() {
        return shop;
    }

    public ChessGame getGame() {
        return game;
    }

    public ShopView getShop(Session player) {
        return shop.playerView(player);
    }

    public int numPlayers() {
        return players.size();
    }

    public boolean hasPlayer(Session session) {
        return players.containsKey(session);
    }

    public boolean full() {
        return players.size() >= 2;
    }

    public boolean addPlayer(Session session) {
        if(full())
            return false;

        if(hasPlayer(session))
            return false;

        players.put(session, new Inventory());
        allSessions.joinLobby(session, this);
        return true;
    }

    public void removePlayer(Session session) {
        if(hasPlayer(session)) {
            players.remove(session);
            allSessions.leaveLobby(session);
        }
    }
}
