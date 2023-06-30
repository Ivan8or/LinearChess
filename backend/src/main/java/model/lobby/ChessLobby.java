package model.lobby;

import chess.ChessGame;
import model.mappings.Session;
import model.session.SessionTracker;
import model.shop.LobbyShop;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChessLobby {

    private final String lobbyId;
    private final LobbyShop shop;
    private ChessGame game;

    private Set<Session> players;
    private SessionTracker allSessions;

    public ChessLobby(String lobbyId, SessionTracker allSessions) {
        this.lobbyId = lobbyId;
        this.shop = new LobbyShop(List.of());

        this.allSessions = allSessions;
        this.players = ConcurrentHashMap.newKeySet();
    }

    public int lobbyCount() {
        return players.size();
    }

    public boolean addPlayer(Session session) {
        if(players.size() >= 2){
            return false;
        }

        players.add(session);
        allSessions.joinLobby(session, this);
        return true;
    }
}
