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

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesBoardsTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/boards/v1LobbiesBoards/";

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
        lenient().when(request.requestMethod()).thenReturn("DELETE");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"get/body.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.headers("lobby")).thenReturn(body);
        lenient().when(model.lobbyExists(lobbyId)).thenReturn(true);
        lenient().when(model.getLobby(lobbyId)).thenReturn(lobby);
        lenient().when(lobby.hasStarted()).thenReturn(true);
        lenient().when(lobby.getGame()).thenReturn(Optional.of(game));
        lenient().when(game.getChess()).thenReturn(chess);
        lenient().when(chess.getBoard()).thenReturn(board);
        lenient().when(board.getFen()).thenReturn("8/board/fen/8");

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getNoLobbyId() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.headers("lobby")).thenReturn(null);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getNoLobbyId/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getBadLobbyId() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"getBadLobbyId/body.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.headers("lobby")).thenReturn(body);
        lenient().when(model.getLobby(lobbyId)).thenReturn(null);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getBadLobbyId/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getLobbyNotStarted() {
        V1LobbiesBoards endpoint = new V1LobbiesBoards(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"getLobbyNotStarted/body.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.headers("lobby")).thenReturn(body);
        lenient().when(model.getLobby(lobbyId)).thenReturn(lobby);
        lenient().when(lobby.hasStarted()).thenReturn(false);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getLobbyNotStarted/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
