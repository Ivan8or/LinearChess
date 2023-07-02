package api.v1.lobbies.shops.view;

import api.util.APIEndpoint;
import api.util.validator.LobbyValidator;
import api.util.validator.SessionValidator;
import model.api.Model;
import model.lobby.ChessLobby;
import model.lobby.VersusMode;
import model.mappings.*;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import java.util.Optional;

import static spark.route.HttpMethod.get;

public class V1LobbiesShopsView extends APIEndpoint {

    final private Model model;

    public V1LobbiesShopsView(Model model) {
        super("/api/v1/lobbies/shops/view", get);
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

        if(!lobby.hasStarted())
            return new ApiResponse(404,"LOBBY_NOT_YET_STARTED");

        VersusMode game = model.getLobby(lobbyId.get()).getGame().get();
        Inventory wares = game.getShop(session.get()).getWares();
        return wares;
    }
}
