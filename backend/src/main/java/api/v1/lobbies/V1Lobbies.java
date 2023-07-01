package api.v1.lobbies;

import api.util.APIEndpoint;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.ApiError;
import model.mappings.Endpoint;
import model.mappings.Reference;
import model.mappings.Session;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;
import util.JsonConverter;

import java.util.Optional;

import static spark.route.HttpMethod.get;
import static spark.route.HttpMethod.post;

public class V1Lobbies extends APIEndpoint {

    final private Model model;

    public V1Lobbies(Model model) {
        super("/api/v1/lobbies", get, post);
        this.model = model;
    }

    @Override
    public Object handle(Request request, Response response) {
        response.header("Content-Type","application/json");
        response.header("Server-Version","1.1.0");

        HttpMethod method = HttpMethod.get(request.requestMethod().toLowerCase());
        switch(method) {
            case get: return get(request, response);
            case post: return post(request, response);
        }
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    private String get(Request request, Response response) {
        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies/boards", "GET"),
                new Endpoint("/api/v1/lobbies/inventories", "GET", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops", "GET"));
        return JsonConverter.toPrettyJson(rootReference);
    }

    private String post(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        if(sessionJson == null || session.isEmpty()) {
            response.status(401);
            return JsonConverter.toPrettyJson(new ApiError("NO_SESSION_HEADER"));
        }

        if(!model.getSessions().validSession(session.get())) {
            response.status(403);
            return JsonConverter.toPrettyJson(new ApiError("BAD_SESSION_HEADER"));
        }

        ChessLobby newLobby = model.spawnLobby();
        return JsonConverter.toPrettyJson(newLobby.getLobbyId());
    }
}
