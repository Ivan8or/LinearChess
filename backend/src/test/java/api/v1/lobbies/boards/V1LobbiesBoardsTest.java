package api.v1.lobbies.boards;

import chess.ChessGame;
import chess.board.LBoard;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.LobbyID;
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
public class V1LobbiesBoardsTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/boards/endpoint/";

    @Mock
    Model model;

    @Mock
    ChessLobby lobby;

    @Mock
    VersusMode game;

    @Mock
    ChessGame chess;

    @Mock
    LBoard board;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupported() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        when(request.requestMethod()).thenReturn("DELETE");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"get/lobbyHeader.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        when(request.requestMethod()).thenReturn("GET");
        when(request.headers("lobby")).thenReturn(body);
        when(model.lobbyExists(lobbyId)).thenReturn(true);
        when(model.getLobby(lobbyId)).thenReturn(lobby);
        when(lobby.getGame()).thenReturn(Optional.of(game));
        when(game.getChess()).thenReturn(chess);
        when(chess.getBoard()).thenReturn(board);
        when(board.getFen()).thenReturn("8/board/fen/8");

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getNoLobbyId() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        when(request.requestMethod()).thenReturn("GET");
        when(request.headers("lobby")).thenReturn(null);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getNoLobbyId/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getBadLobbyId() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"getBadLobbyId/lobbyHeader.json").get();
        when(request.requestMethod()).thenReturn("GET");
        when(request.headers("lobby")).thenReturn(body);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getBadLobbyId/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getLobbyNotStarted() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"getLobbyNotStarted/lobbyHeader.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        when(request.requestMethod()).thenReturn("GET");
        when(request.headers("lobby")).thenReturn(body);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getLobbyNotStarted/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
