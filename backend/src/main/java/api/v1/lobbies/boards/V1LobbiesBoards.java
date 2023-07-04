package api.v1.lobbies.boards;

import api.util.APIEndpoint;
import api.util.validator.LobbyValidator;
import chess.ChessGame;
import chess.board.LBoard;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.ApiResponse;
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

    protected Object get(Request request, Response response) {
        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = LobbyValidator.validate(lobbyId, model);
        if(invalid.isPresent())
            return invalid.get();

        ChessLobby lobby = model.getLobby(lobbyId.get());
        Optional<VersusMode> possibleGame = lobby.getGame();
        if(possibleGame.isEmpty()) {
            return new ApiResponse(404, "LOBBY_NOT_YET_STARTED");
        }
        VersusMode game = possibleGame.get();
        ChessGame chess = game.getChess();
        LBoard board = chess.getBoard();
        BoardFEN fen = new BoardFEN(board.getFen());

//        if(lobby.hasStarted()) {
//            VersusMode game = lobby.getGame().get();
//            toReturn.putAll(Map.of(
//                    "round",  game.getRound(),
//                    "phase", game.getPhase(),
//                    "shop-time", game.getShopTime(),
//                    "timePassed", game.getPhaseDeltaTime(),
//                    game.get
//            ));
//        }

        return fen;
    }
}
