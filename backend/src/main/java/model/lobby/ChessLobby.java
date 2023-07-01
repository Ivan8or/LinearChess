package model.lobby;

import chess.ChessGame;
import model.mappings.Session;
import model.session.SessionTracker;
import model.shop.ItemShop;
import model.shop.ShopView;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChessLobby {

    private final String lobbyId;
    transient final private ItemShop shop;
    transient private ChessGame game;

    transient private Set<Session> players;
    transient private SessionTracker allSessions;

    public ChessLobby(String lobbyId, SessionTracker allSessions) {
        this.lobbyId = lobbyId;
        this.shop = new ItemShop(List.of());

        this.allSessions = allSessions;
        this.players = ConcurrentHashMap.newKeySet();
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public ItemShop getShop() {
        return shop;
    }

    public ShopView getShop(Session player) {
        return shop.playerView(player);
    }

    public int numPlayers() {
        return players.size();
    }

    public boolean hasPlayer(Session session) {
        return players.contains(session);
    }

    public boolean addPlayer(Session session) {
        if(players.size() >= 2)
            return false;

        if(players.contains(session))
            return false;

        players.add(session);
        allSessions.joinLobby(session, this);
        return true;
    }

    public boolean removePlayer(Session session) {
        if(!players.contains(session))
            return false;

        players.remove(session);
        allSessions.leaveLobby(session);
        return true;
    }
}
