package api.v1.lobbies.boards;

import api.util.APIEndpoint;
import chess.ChessGame;
import chess.board.LBoard;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.ApiError;
import model.mappings.BoardFEN;
import model.mappings.LobbyID;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import java.util.Optional;

import static spark.route.HttpMethod.get;

public class V1LobbiesBoards extends APIEndpoint {

    final private Model model;

    public V1LobbiesBoards(Model model) {
        super("/api/v1/lobbies/boards", get);
        this.model = model;
    }

    protected String get(Request request, Response response) {
        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        if(lobbyId.isEmpty()) {
            response.status(400);
            return JsonConverter.toPrettyJson(new ApiError("NO_LOBBY_ID"));
        }

        if(!model.lobbyExists(lobbyId.get())) {
            response.status(403);
            return JsonConverter.toPrettyJson(new ApiError("BAD_LOBBY_ID"));
        }

        ChessLobby lobby = model.getLobby(lobbyId.get());
        Optional<VersusMode> possibleGame = lobby.getGame();
        if(possibleGame.isEmpty()) {
            response.status(404);
            return JsonConverter.toPrettyJson(new ApiError("LOBBY_NOT_YET_STARTED"));
        }
        VersusMode game = possibleGame.get();
        ChessGame chess = game.getChess();
        LBoard board = chess.getBoard();
        BoardFEN fen = new BoardFEN(board.getFen());

        return JsonConverter.toPrettyJson(fen);
    }
}
