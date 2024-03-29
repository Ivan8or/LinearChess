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

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/endpoint/";

    @Mock
    Model model;

    @Mock
    SessionTracker sessions;

    @Mock
    ChessLobby lobby;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupportedMethod() {
        V1Lobbies endpoint = new V1Lobbies(model);
        when(request.requestMethod()).thenReturn("CONNECT");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void options() {
        V1Lobbies endpoint = new V1Lobbies(model);
        when(request.requestMethod()).thenReturn("OPTIONS");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"options/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void postNoSession() {
        V1Lobbies endpoint = new V1Lobbies(model);
        when(request.requestMethod()).thenReturn("POST");
        when(request.headers("session")).thenReturn(null);
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
        when(request.requestMethod()).thenReturn("POST");
        when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(false);

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
        ChessLobby lobby = new ChessLobby(new LobbyID("alabama"), model);
        when(request.requestMethod()).thenReturn("POST");
        when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);
        when(model.spawnLobby()).thenReturn(lobby);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"post/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void patch() {
        V1Lobbies endpoint = new V1Lobbies(model);
        String sessionHeader = ResourceAsString.at(RESOURCE_PATH+"patch/sessionHeader.json").get();
        String lobbyHeader = ResourceAsString.at(RESOURCE_PATH+"patch/lobbyHeader.json").get();

        Session session = JsonConverter.fromJson(sessionHeader, Session.class).get();
        LobbyID lobbyID = JsonConverter.fromJson(lobbyHeader, LobbyID.class).get();

        when(request.requestMethod()).thenReturn("PATCH");
        when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        when(request.headers("lobby")).thenReturn(JsonConverter.toJson(lobbyID));
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);
        when(model.lobbyExists(lobbyID)).thenReturn(true);
        when(model.getLobby(lobbyID)).thenReturn(lobby);
        when(lobby.hasStarted()).thenReturn(false);
        when(lobby.hasPlayer(session)).thenReturn(true);

        String generated = (String) endpoint.handle(request, response);
        System.out.println(generated);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"patch/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertTrue(JsonConverter.equals(expected, generated));
    }

    @Test
    public void put() {
        V1Lobbies endpoint = new V1Lobbies(model);
        String sessionHeader = ResourceAsString.at(RESOURCE_PATH+"put/sessionHeader.json").get();
        String lobbyHeader = ResourceAsString.at(RESOURCE_PATH+"put/lobbyHeader.json").get();

        Session session = JsonConverter.fromJson(sessionHeader, Session.class).get();
        LobbyID lobbyID = JsonConverter.fromJson(lobbyHeader, LobbyID.class).get();

        when(request.requestMethod()).thenReturn("PUT");
        when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        when(request.headers("lobby")).thenReturn(JsonConverter.toJson(lobbyID));
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);
        when(model.lobbyExists(lobbyID)).thenReturn(true);
        when(model.getLobby(lobbyID)).thenReturn(lobby);
        when(lobby.hasStarted()).thenReturn(false);
        when(lobby.full()).thenReturn(false);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"put/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
        verify(lobby).addPlayer(session);
    }

    @Test
    public void delete() {
        V1Lobbies endpoint = new V1Lobbies(model);
        String sessionHeader = ResourceAsString.at(RESOURCE_PATH+"delete/sessionHeader.json").get();
        String lobbyHeader = ResourceAsString.at(RESOURCE_PATH+"delete/lobbyHeader.json").get();

        Session session = JsonConverter.fromJson(sessionHeader, Session.class).get();
        LobbyID lobbyID = JsonConverter.fromJson(lobbyHeader, LobbyID.class).get();

        when(request.requestMethod()).thenReturn("DELETE");
        when(request.headers("session")).thenReturn(JsonConverter.toJson(session));
        when(request.headers("lobby")).thenReturn(JsonConverter.toJson(lobbyID));
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);
        when(model.lobbyExists(lobbyID)).thenReturn(true);
        when(model.getLobby(lobbyID)).thenReturn(lobby);
        when(lobby.hasPlayer(session)).thenReturn(true);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"delete/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
        verify(lobby).removePlayer(session);
    }
}
