package api.v1.lobbies;

import model.api.Model;
import model.lobby.ChessLobby;
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

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/v1Lobbies/";

    @Mock
    Model model;

    @Mock
    SessionTracker sessions;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupportedMethod() {
        V1Lobbies endpoint = new V1Lobbies(model);
        lenient().when(request.requestMethod()).thenReturn("PATCH");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1Lobbies endpoint = new V1Lobbies(model);
        lenient().when(request.requestMethod()).thenReturn("GET");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void postNoSession() {
        V1Lobbies endpoint = new V1Lobbies(model);
        lenient().when(request.requestMethod()).thenReturn("POST");
        lenient().when(request.headers("session")).thenReturn(null);
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"postNoSession/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void postBadSession() {
        V1Lobbies endpoint = new V1Lobbies(model);
        Session session = Session.spawn();
        lenient().when(request.requestMethod()).thenReturn("POST");
        lenient().when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        lenient().when(model.getSessions()).thenReturn(sessions);
        lenient().when(sessions.validSession(session)).thenReturn(false);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"postBadSession/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void post() {
        V1Lobbies endpoint = new V1Lobbies(model);
        Session session = Session.spawn();
        ChessLobby lobby = new ChessLobby(new LobbyID("alabama"), sessions, model);
        lenient().when(request.requestMethod()).thenReturn("POST");
        lenient().when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        lenient().when(model.getSessions()).thenReturn(sessions);
        lenient().when(sessions.validSession(session)).thenReturn(true);
        lenient().when(model.spawnLobby()).thenReturn(lobby);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"post/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
