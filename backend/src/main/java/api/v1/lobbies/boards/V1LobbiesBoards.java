package api.v1.lobbies.boards;

import api.util.APIEndpoint;
import api.util.validator.LobbyValidator;
import api.util.validator.SessionValidator;
import chess.ChessGame;
import chess.board.LBoard;
import chess.board.LSide;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.ApiResponse;
import model.mappings.BoardFEN;
import model.mappings.LobbyID;
import model.mappings.Session;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import java.util.Map;
import java.util.Optional;

import static spark.route.HttpMethod.get;

public class V1LobbiesBoards extends APIEndpoint {

    final private Model model;

    public V1LobbiesBoards(Model model) {
        super("/api/v1/lobbies/boards", get);
        this.model = model;
    }

    protected Object get(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model)
                .or(() -> LobbyValidator.validate(lobbyId, model));

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby lobby = model.getLobby(lobbyId.get());
        Session player = session.get();
        Optional<VersusMode> possibleGame = lobby.getGame();

        if(!lobby.hasPlayer(player))
            return new ApiResponse(403, "SESSION_NOT_IN_LOBBY");

        if(possibleGame.isEmpty()) {
            return new ApiResponse(404, "LOBBY_NOT_YET_STARTED");
        }

        VersusMode game = possibleGame.get();
        ChessGame chess = game.getChess();
        LBoard board = chess.getBoard();
        BoardFEN fen = new BoardFEN(board.getFen());
        LSide side = game.getSide(player);

        return Map.of(
                "fen", fen,
                "round", game.getRound(),
                "phase", game.getPhase(),
                "side", side,
                "my-inventory", game.getSpectatableInventory(side),
                "opp-inventory", game.getSpectatableInventory(side.flip())
        );


    }
}
