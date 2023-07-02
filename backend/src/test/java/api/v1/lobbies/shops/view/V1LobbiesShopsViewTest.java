package api.v1.lobbies.shops.view;

import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.Inventory;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.session.SessionTracker;
import model.shop.ShopView;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesShopsViewTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/shops/view/endpoint/";

    @Mock
    Model model;

    @Mock
    SessionTracker sessions;

    @Mock
    ChessLobby lobby;

    @Mock
    VersusMode game;

    @Mock
    ShopView shop;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupportedMethod() {
        V1LobbiesShopsView endpoint = new V1LobbiesShopsView(model);
        when(request.requestMethod()).thenReturn("CONNECT");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1LobbiesShopsView endpoint = new V1LobbiesShopsView(model);

        String sessionHeader = ResourceAsString.at(RESOURCE_PATH+"get/sessionHeader.json").get();
        String lobbyHeader = ResourceAsString.at(RESOURCE_PATH+"get/lobbyHeader.json").get();
        String inventoryJson = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();

        Session session = JsonConverter.fromJson(sessionHeader, Session.class).get();
        LobbyID lobbyID = JsonConverter.fromJson(lobbyHeader, LobbyID.class).get();
        Inventory wares = JsonConverter.fromJson(inventoryJson, Inventory.class).get();

        when(request.requestMethod()).thenReturn("GET");
        when(request.headers("session")).thenReturn(sessionHeader);
        when(request.headers("lobby")).thenReturn(lobbyHeader);
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);
        when(model.lobbyExists(lobbyID)).thenReturn(true);
        when(model.getLobby(lobbyID)).thenReturn(lobby);
        when(lobby.getGame()).thenReturn(Optional.of(game));
        when(lobby.hasStarted()).thenReturn(true);
        when(game.getShop(session)).thenReturn(shop);
        when(shop.getWares()).thenReturn(wares);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
