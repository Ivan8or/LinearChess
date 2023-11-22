package api.v1.sessions.updates;

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

import static spark.route.HttpMethod.*;

@Deprecated
public class V1SessionsStatus extends APIEndpoint {

    final private Model model;

    public V1SessionsStatus(Model model) {
        super("/api/v1/sessions/status", put);
        this.model = model;
    }

    protected Object put(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);


        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyID = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model)
                .or(() -> LobbyValidator.validate(lobbyID, model));

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby lobby = model.getLobby(lobbyID.get());
        if(!lobby.hasPlayer(session.get()))
            return new ApiResponse(403, "SESSION_NOT_IN_LOBBY");


        String bodyJson = request.body();
        Optional<ReadyStatus> optionalTransaction = JsonConverter.fromJson(bodyJson, ReadyStatus.class);

        if(optionalTransaction.isEmpty())
            return new ApiResponse(400,"NO_READY_STATUS_BODY");

        boolean currentStatus = lobby.isReady(session.get());
        if(currentStatus != optionalTransaction.get().ready()) {
            lobby.toggleReady(session.get());
        }

        return new ApiResponse(200, "SUCCESS");
    }
}
