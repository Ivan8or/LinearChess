package model.lobby;

import model.api.Model;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.session.SessionTracker;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ChessLobby {

    private final Model api;

    private final LobbyID lobbyId;
    private VersusMode game;
    private boolean started;

    final private Map<Session,Boolean> players;
    final private SessionTracker allSessions;

    public ChessLobby(LobbyID lobbyId, SessionTracker allSessions, Model api) {
        this.api = api;
        this.lobbyId = lobbyId;
        this.allSessions = allSessions;

        this.game = null;
        this.started = false;
        this.players = new ConcurrentHashMap<>(2);
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean start() {
        if(!full())
            return false;

        started = true;
        Session[] indexable = players.keySet().toArray(Session[]::new);
        int whitePlayer = (int) (Math.random() * 2);
        this.game = new VersusMode(indexable[whitePlayer], indexable[(whitePlayer + 1) % 2], this);
        game.progressRound();
        return true;
    }

    public void end() {
        for(Session player : players.keySet())
            allSessions.leaveLobby(player);
        api.closeLobby(lobbyId);
    }

    public LobbyID getLobbyId() {
        return lobbyId;
    }

    public Optional<VersusMode> getGame() {
        return Optional.ofNullable(game);
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

        players.put(session, false);
        allSessions.joinLobby(session, this);
        return true;
    }

    public boolean toggleReady(Session session) {
        return players.merge(session, false, (old,i) -> !old);
    }

    public boolean isReady(Session session) {
        return players.get(session);
    }

    public boolean removePlayer(Session session) {
        if(hasPlayer(session)) {
            players.remove(session);
            allSessions.leaveLobby(session);
            return true;
        }
        return false;
    }
}
