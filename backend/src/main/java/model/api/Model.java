package model.api;

import model.lobby.ChessLobby;
import model.session.SessionTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Model {

    final private SessionTracker sessions;
    final private Map<String, ChessLobby> lobbies;

    public Model() {
        sessions = new SessionTracker();
        lobbies = new ConcurrentHashMap<>();
    }

    private String generateLobbyId(int length) {
        String from = "aeiou";
        String sideline = "bcdfghjklmnpqrstvwxyz";

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

        return id;
    }

    public ChessLobby spawnLobby() {
        String lobbyId = generateLobbyId(7);
        ChessLobby newLobby = new ChessLobby(lobbyId, sessions);
        lobbies.put(lobbyId, newLobby);
        return newLobby;
    }

    public SessionTracker getSessions() {
        return sessions;
    }


}
