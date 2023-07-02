package model.lobby;

import model.api.Model;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.session.SessionTracker;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChessLobby {

    private final Model api;

    private final LobbyID lobbyId;
    private Optional<VersusMode> game;
    private boolean started;

    final private Set<Session> players;
    final private SessionTracker allSessions;

    public ChessLobby(LobbyID lobbyId, SessionTracker allSessions, Model api) {
        this.api = api;
        this.lobbyId = lobbyId;
        this.allSessions = allSessions;

        this.game = Optional.empty();
        this.started = false;
        this.players = ConcurrentHashMap.newKeySet();
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean start() {
        if(!full())
            return false;

        started = true;
        Session[] indexable = players.toArray(Session[]::new);
        int whitePlayer = (int) (Math.random() * 2);
        this.game = Optional.of(new VersusMode(indexable[whitePlayer], indexable[(whitePlayer + 1) % 2], this));
        game.get().progressRound();

        return true;
    }

    public void end() {
        for(Session player : players)
            allSessions.leaveLobby(player);
        api.closeLobby(lobbyId);
    }

    public LobbyID getLobbyId() {
        return lobbyId;
    }

    public Optional<VersusMode> getGame() {
        return game;
    }

    public int numPlayers() {
        return players.size();
    }

    public boolean hasPlayer(Session session) {
        return players.contains(session);
    }

    public boolean full() {
        return players.size() >= 2;
    }

    public boolean addPlayer(Session session) {
        if(full())
            return false;

        if(hasPlayer(session))
            return false;

        players.add(session);
        allSessions.joinLobby(session, this);

        if(full())
            start();

        return true;
    }

    public void removePlayer(Session session) {
        if(hasPlayer(session)) {
            players.remove(session);
            allSessions.leaveLobby(session);
        }
    }
}
