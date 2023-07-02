package api.v1.lobbies.inventories;

import api.util.APIEndpoint;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.*;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import java.util.Optional;

import static spark.route.HttpMethod.get;

public class V1LobbiesInventories extends APIEndpoint {

    final private Model model;

    public V1LobbiesInventories(Model model) {
        super("/api/v1/lobbies/inventories", get);
        this.model = model;
    }

    protected String get(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        if(session.isEmpty()) {
            response.status(401);
            return JsonConverter.toPrettyJson(new ApiError("NO_SESSION_HEADER"));
        }

        if(lobbyId.isEmpty()) {
            response.status(400);
            return JsonConverter.toPrettyJson(new ApiError("NO_LOBBY_ID"));
        }

        if(!model.lobbyExists(lobbyId.get())) {
            response.status(403);
            return JsonConverter.toPrettyJson(new ApiError("BAD_LOBBY_ID"));
        }

        ChessLobby lobby = model.getLobby(lobbyId.get());
        if(!lobby.hasStarted()) {
            response.status(404);
            return JsonConverter.toPrettyJson(new ApiError("LOBBY_NOT_YET_STARTED"));
        }

        Inventory composition = lobby.getGame().get().getInventory(session.get());
        return JsonConverter.toPrettyJson(composition);
    }
}
