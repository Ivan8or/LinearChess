package model.session;

import model.lobby.ChessLobby;
import model.mappings.Session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionTracker {

    final private Map<Session, Optional<ChessLobby>> sessions;

    public SessionTracker() {
        sessions = new ConcurrentHashMap<>();
    }

    public Session startSession() {
        Session newSession = Session.spawn();
        sessions.put(newSession, Optional.empty());
        return newSession;
    }

    public void endSession(Session session) {
        sessions.remove(session);
    }

    public boolean validSession(Session session) {
        return sessions.containsKey(session);
    }

    public boolean joinLobby(Session session, ChessLobby lobby) {
        if(!validSession(session))
            return false;

        Optional<ChessLobby> oldLobby = sessions.get(session);
        oldLobby.ifPresent(l -> {
            if(!lobby.getLobbyId().equals(l.getLobbyId()))
                l.removePlayer(session);
        });

        sessions.put(session, Optional.of(lobby));
        return true;
    }

    public boolean leaveLobby(Session session) {
        if(!validSession(session))
            return false;

        Optional<ChessLobby> lobby = sessions.get(session);
        if(lobby.isEmpty())
            return false;

        lobby.get().removePlayer(session);
        sessions.put(session, Optional.empty());
        return true;
    }

    public Optional<ChessLobby> sessionLobby(Session session) {
        return sessions.get(session);
    }
}
