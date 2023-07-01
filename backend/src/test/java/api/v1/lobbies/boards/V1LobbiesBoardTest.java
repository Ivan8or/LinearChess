package api.v1.lobbies.boards;

import api.v1.lobbies.V1Lobbies;
import chess.ChessGame;
import chess.board.LBoard;
import model.api.Model;
import model.lobby.ChessLobby;
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

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesBoardTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/boards/v1LobbiesBoards/";

    @Mock
    Model model;

    @Mock
    ChessLobby lobby;

    @Mock
    ChessGame game;

    @Mock
    LBoard board;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupported() {
        V1LobbiesBoard endpoint = new V1LobbiesBoard(model);
        lenient().when(request.requestMethod()).thenReturn("DELETE");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1LobbiesBoard endpoint = new V1LobbiesBoard(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"get/body.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.body()).thenReturn(body);
        lenient().when(model.getLobby(lobbyId)).thenReturn(lobby);
        lenient().when(lobby.getGame()).thenReturn(game);
        lenient().when(game.getBoard()).thenReturn(board);
        lenient().when(board.getFen()).thenReturn("8/board/fen/8");

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getNoLobbyId() {
        V1LobbiesBoard endpoint = new V1LobbiesBoard(model);
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.body()).thenReturn(null);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getNoLobbyId/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void getBadLobbyId() {
        V1LobbiesBoard endpoint = new V1LobbiesBoard(model);
        String body = ResourceAsString.at(RESOURCE_PATH+"getBadLobbyId/body.json").get();
        LobbyID lobbyId = JsonConverter.fromJson(body, LobbyID.class).get();
        lenient().when(request.requestMethod()).thenReturn("GET");
        lenient().when(request.body()).thenReturn(body);
        lenient().when(model.getLobby(lobbyId)).thenReturn(null);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"getBadLobbyId/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
