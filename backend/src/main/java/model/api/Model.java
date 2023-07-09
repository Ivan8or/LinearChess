package model.api;

import model.lobby.ChessLobby;
import model.mappings.LobbyID;
import model.session.SessionTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Model {

    final private SessionTracker sessions;
    final private Map<LobbyID, ChessLobby> lobbies;

    public Model() {
        sessions = new SessionTracker();
        lobbies = new ConcurrentHashMap<>();
    }

    private LobbyID generateLobbyId(int length) {
        String from = "bcdfghjklmnpqrstvwxyz";
        String sideline = "aeiou";

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            builder.append(from.charAt((int) (Math.random() * from.length())));
            String temp = from;
            from = sideline;
            sideline = temp;
        }
        String id = builder.toString();
        if(lobbies.containsKey(id))
            return generateLobbyId(length);

        return new LobbyID(id);
    }

    public ChessLobby spawnLobby() {
        LobbyID lobbyId = generateLobbyId(7);
        ChessLobby newLobby = new ChessLobby(lobbyId, this);
        lobbies.put(lobbyId, newLobby);
        return newLobby;
    }

    public boolean lobbyExists(LobbyID lobbyId) {
        return lobbies.containsKey(lobbyId);
    }

    public ChessLobby getLobby(LobbyID lobbyId) {
        return lobbies.get(lobbyId);
    }

    public void closeLobby(LobbyID lobbyId) {
        lobbies.remove(lobbyId);
    }

    public SessionTracker getSessions() {
        return sessions;
    }

}
