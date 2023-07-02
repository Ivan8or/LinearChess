package api.v1.lobbies.shops.buy;

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

import static spark.route.HttpMethod.patch;

public class V1LobbiesShopsBuy extends APIEndpoint {

    final private Model model;

    public V1LobbiesShopsBuy(Model model) {
        super("/api/v1/lobbies/shops/buy", patch);
        this.model = model;
    }

    protected Object patch(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> optionalSession = JsonConverter.fromJson(sessionJson, Session.class);

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> optionalLobbyID = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(optionalSession, model)
                .or(() -> LobbyValidator.validate(optionalLobbyID, model));

        if(invalid.isPresent())
            return invalid.get();

        String bodyJson = request.body();
        Optional<MoveItem> optionalTransaction = JsonConverter.fromJson(bodyJson, MoveItem.class);

        if(optionalTransaction.isEmpty())
            return new ApiResponse(400,"NO_TRANSACTION_BODY");

        LobbyID lobbyId = optionalLobbyID.get();
        ChessLobby lobby = model.getLobby(lobbyId);

        if(!lobby.hasStarted() || model.getLobby(lobbyId).getGame().isEmpty())
            return new ApiResponse(423,"LOBBY_NOT_YET_STARTED");

        VersusMode game = model.getLobby(lobbyId).getGame().get();

        if(!game.canChangeInventory())
            return new ApiResponse(423,"MODIFICATION_DENIED_UNTIMELY");

        MoveItem transaction = optionalTransaction.get();
        Session session = optionalSession.get();
        boolean success = game.purchaseItem(session, transaction.slotFrom(), transaction.slotTo());

        if(!success)
            return new ApiResponse(400,"MODIFICATION_DENIED_NONSENSICAL");

        return new ApiResponse(200,"SUCCESS");
    }
}
