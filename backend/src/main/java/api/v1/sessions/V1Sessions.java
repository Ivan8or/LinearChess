package api.v1.sessions;

import api.util.APIEndpoint;
import api.util.validator.SessionValidator;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.ApiResponse;
import model.mappings.Session;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import java.util.Map;
import java.util.Optional;

import static spark.route.HttpMethod.*;

public class V1Sessions extends APIEndpoint {

    final private Model model;

    public V1Sessions(Model model) {
        super("/api/v1/sessions", get, post, delete);
        this.model = model;
    }

    protected Object get(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model);
        if(invalid.isPresent())
            return invalid.get();

        Optional<ChessLobby> lobby = model.getSessions().sessionLobby(session.get());

        if(lobby.isEmpty())
            return new ApiResponse(200, "VALID_SESSION");

        return Map.of("status", 200, "message","VALID_SESSION","lobby", lobby.get().getLobbyId());
    }

    protected Object post(Request request, Response response) {
        Session newSession = model.getSessions().startSession();
        return newSession;
    }

    protected Object delete(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model);
        if(invalid.isPresent())
            return invalid.get();

        model.getSessions().endSession(session.get());
        return new ApiResponse(200, "SUCCESS");
    }
}
