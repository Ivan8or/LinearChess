package api.v1.lobbies.boards;

import api.util.APIEndpoint;
import model.api.Model;
import model.mappings.ApiError;
import model.mappings.BoardFEN;
import model.mappings.LobbyID;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;
import util.JsonConverter;

import java.util.Optional;

import static spark.route.HttpMethod.get;

public class V1LobbiesBoard extends APIEndpoint {

    final private Model model;

    public V1LobbiesBoard(Model model) {
        super("/api/v1/lobbies/:lobby/board", get);
        this.model = model;
    }

    protected String get(Request request, Response response) {
        String lobbyJson = request.body();
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        if(lobbyId.isEmpty()) {
            response.status(401);
            return JsonConverter.toPrettyJson(new ApiError("NO_LOBBY_ID"));
        }

        if(model.getLobby(lobbyId.get()) == null) {
            response.status(403);
            return JsonConverter.toPrettyJson(new ApiError("BAD_LOBBY_ID"));
        }

        BoardFEN board = new BoardFEN(model.getLobby(lobbyId.get()).getGame().getBoard().getFen());
        return JsonConverter.toPrettyJson(board);
    }

}
