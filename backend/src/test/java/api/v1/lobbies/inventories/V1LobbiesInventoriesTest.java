package api.v1.lobbies.inventories;

import api.v1.lobbies.V1Lobbies;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.Inventory;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.session.SessionTracker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.Optional;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesInventoriesTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/inventories/v1LobbiesInventories/";

    @Mock
    Model model;

    @Mock
    SessionTracker sessions;

    @Mock
    ChessLobby lobby;

    @Mock
    VersusMode game;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupportedMethod() {
        V1LobbiesInventories endpoint = new V1LobbiesInventories(model);
        lenient().when(request.requestMethod()).thenReturn("PATCH");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1LobbiesInventories endpoint = new V1LobbiesInventories(model);
        String sessionHeader = ResourceAsString.at(RESOURCE_PATH+"get/sessionHeader.json").get();
        String lobbyHeader = ResourceAsString.at(RESOURCE_PATH+"get/lobbyHeader.json").get();

        Session session = JsonConverter.fromJson(sessionHeader, Session.class).get();
        LobbyID lobbyID = JsonConverter.fromJson(lobbyHeader, LobbyID.class).get();

        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        lenient().when(request.headers("lobby")).thenReturn(JsonConverter.toJson(lobbyID));
        lenient().when(model.getSessions()).thenReturn(sessions);
        lenient().when(sessions.validSession(session)).thenReturn(true);
        lenient().when(model.lobbyExists(lobbyID)).thenReturn(true);
        lenient().when(model.getLobby(lobbyID)).thenReturn(lobby);
        lenient().when(lobby.hasStarted()).thenReturn(true);
        lenient().when(lobby.getGame()).thenReturn(Optional.of(game));

        String inventoryJson = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        Inventory inventory = JsonConverter.fromJson(inventoryJson, Inventory.class).get();
        lenient().when(game.getInventory(session)).thenReturn(inventory);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
