package model.socket;

import api.util.validator.InLobbyValidator;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.ApiResponse;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.socket.mappings.JoinLobbyMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import util.JsonConverter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServer extends WebSocketServer {

    final Model api;
    final Map<WebSocket, Session> connections;

    public SocketServer(Model api, int port) {
        super(new InetSocketAddress(port));
        this.api = api;
        this.connections = new ConcurrentHashMap<>();
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(
            WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {

        ServerHandshakeBuilder builder = super
                .onWebsocketHandshakeReceivedAsServer(conn, draft, request);
        builder.put("Access-Control-Allow-Origin", "*");
        return builder;
    }


    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Session session = connections.get(conn);
        Optional<ChessLobby> lobby = api.getSessions().sessionLobby(session);
        if(lobby.isEmpty())
            return;

        connections.remove(conn);
        lobby.get().getSocket().removeClient(session);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Optional<Map> body = JsonConverter.fromJson(message, Map.class);
        if(body.isEmpty() || !body.get().containsKey("key")) {
            return;
        }

        Map<String, Object> map = (Map<String, Object>) body.get();
        String key = (String) map.get("key");

        switch(key) {
            case "JOIN_LOBBY":
                onJoinLobby(conn, message);
                break;
        }
    }

    private void onJoinLobby(WebSocket conn, String message) {
        Optional<JoinLobbyMessage> body = JsonConverter.fromJson(message, JoinLobbyMessage.class);

        Optional<Session> session = body.map(JoinLobbyMessage::session);
        Optional<LobbyID> lobbyID = body.map(JoinLobbyMessage::lobby);
        Optional<ApiResponse> error = InLobbyValidator.validate(session, lobbyID, api);

        if(error.isPresent()) {
            return;
        }

        connections.put(conn, session.get());

        SocketWrapper clientWrapper = new SocketWrapper(conn, this);
        api.getLobby(lobbyID.get()).getSocket().addClient(session.get(), clientWrapper);
        clientWrapper.send(Map.of("key","connected","value","TRUE"));
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {

    }
    @Override
    public void onStart() {

    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    }
}
