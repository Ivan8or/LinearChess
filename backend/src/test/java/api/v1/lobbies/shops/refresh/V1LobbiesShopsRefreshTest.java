package api.v1.lobbies.shops.refresh;

import api.v1.lobbies.shops.view.V1LobbiesShopsView;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.Inventory;
import model.mappings.LobbyID;
import model.mappings.MoveItem;
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
public class V1LobbiesShopsRefreshTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/shops/refresh/endpoint/";

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
        V1LobbiesShopsRefresh endpoint = new V1LobbiesShopsRefresh(model);
        when(request.requestMethod()).thenReturn("CONNECT");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void patch() {
        V1LobbiesShopsRefresh endpoint = new V1LobbiesShopsRefresh(model);

        String sessionHeader = ResourceAsString.at(RESOURCE_PATH+"patch/sessionHeader.json").get();
        String lobbyHeader = ResourceAsString.at(RESOURCE_PATH+"patch/lobbyHeader.json").get();

        Session session = JsonConverter.fromJson(sessionHeader, Session.class).get();
        LobbyID lobbyID = JsonConverter.fromJson(lobbyHeader, LobbyID.class).get();

        when(request.requestMethod()).thenReturn("PATCH");
        when(request.headers("session")).thenReturn(sessionHeader);
        when(request.headers("lobby")).thenReturn(lobbyHeader);
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);
        when(model.lobbyExists(lobbyID)).thenReturn(true);
        when(model.getLobby(lobbyID)).thenReturn(lobby);
        when(lobby.hasPlayer(session)).thenReturn(true);
        when(lobby.getGame()).thenReturn(Optional.of(game));
        when(lobby.hasStarted()).thenReturn(true);
        when(game.restockShop(session)).thenReturn(true);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"patch/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
