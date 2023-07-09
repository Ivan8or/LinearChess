package model.socket;

import model.lobby.ChessLobby;
import model.mappings.Session;
import util.JsonConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LobbySocket {

    final private ChessLobby lobby;
    final private Map<Session, SocketWrapper> clients;

    public LobbySocket(ChessLobby lobby) {
        this.clients = new ConcurrentHashMap<>(2);
        this.lobby = lobby;
    }

    public boolean hasClient(Session s) {
        return clients.containsKey(s);
    }

    public void addClient(Session session, SocketWrapper socket) {
        clients.put(session, socket);
    }

    public void removeClient(Session session) {
        SocketWrapper removed = clients.remove(session);
        if(removed != null)
            removed.disconnect();
    }

    public void sendAll(Object message) {
        for(SocketWrapper c : clients.values())
            c.send(message);
    }

    public void send(Session session, Object message) {
        clients.get(session).send(message);
    }
}
