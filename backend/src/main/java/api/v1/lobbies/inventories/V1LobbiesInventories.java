package api.v1.lobbies.inventories;

import api.util.APIEndpoint;
import api.util.validator.LobbyValidator;
import api.util.validator.SessionValidator;
import model.api.Model;
import model.lobby.ChessLobby;
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

    @Override
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
        if(!lobby.hasStarted())
            return new ApiResponse(404,"LOBBY_NOT_YET_STARTED");

        Inventory composition = lobby.getGame().get().getInventory(session.get());
        return composition;
    }
}
