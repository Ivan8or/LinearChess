package model.socket;

import org.java_websocket.WebSocket;
import util.JsonConverter;

public class SocketWrapper {

    final private SocketServer tracker;
    final private WebSocket client;

    public SocketWrapper(WebSocket client, SocketServer tracker) {
        this.client = client;
        this.tracker = tracker;
    }

    public void send(Object message) {
        client.send(JsonConverter.toJson(message));
    }

    public void disconnect() {
        client.close(100, "DISCONNECTED");
    }
}
