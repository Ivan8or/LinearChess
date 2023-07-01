package api.v1.lobbies.inventories;

import api.util.APIEndpoint;
import model.api.Model;
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

        if(session.isEmpty()) {
            response.status(401);
            return JsonConverter.toPrettyJson(new ApiError("NO_SESSION_HEADER"));
        }

        String lobbyJson = request.body();
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        if(lobbyId.isEmpty()) {
            response.status(400);
            return JsonConverter.toPrettyJson(new ApiError("NO_LOBBY_ID"));
        }

        if(!model.lobbyExists(lobbyId.get())) {
            response.status(403);
            return JsonConverter.toPrettyJson(new ApiError("BAD_LOBBY_ID"));
        }

        //Inventory composition = model.getLobby(lobbyId.get());
        return JsonConverter.toPrettyJson("");
    }
}
