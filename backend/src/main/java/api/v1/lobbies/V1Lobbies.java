package api.v1.lobbies;

import api.util.APIEndpoint;
import api.util.validator.LobbyValidator;
import api.util.validator.SessionValidator;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.*;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.route.HttpMethod.*;

public class V1Lobbies extends APIEndpoint {

    final private Model model;

    public V1Lobbies(Model model) {
        super("/api/v1/lobbies", options, get, post, put, patch, delete);
        this.model = model;
    }

    @Override
    protected Object options(Request request, Response response) {
        super.options(request, response);

        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies/boards", "OPTIONS"),
                new Endpoint("/api/v1/lobbies/inventories", "OPTIONS", "GET", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops", "OPTIONS"));
        return rootReference;
    }

    @Override
    protected Object get(Request request, Response response) {
        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyID = JsonConverter.fromJson(lobbyJson, LobbyID.class);
        Optional<ApiResponse> error = LobbyValidator.validate(lobbyID, model);

        if(error.isPresent())
            return error.get();

        ChessLobby lobby = model.getLobby(lobbyID.get());

        Map<String, Object> toReturn = new HashMap<>(Map.of(
                "status", 200,
                "message", "VALID_LOBBY",
                "gameType", "VERSUS",
                "isLobbyStarted", lobby.hasStarted(),
                "playerCount", lobby.numPlayers(),
                "playersReady", lobby.numReady(),
                "isPlayerInLobby", false));

        String sessionJson = request.headers("session");
        Optional<Session> sessionID = JsonConverter.fromJson(sessionJson, Session.class);

        synchronized(lobby) {
            Optional<ApiResponse> sessionError = SessionValidator.validate(sessionID, model);

            if (sessionError.isEmpty()) {
                boolean readyState = lobby.isReady(sessionID.get());
                toReturn.putAll(Map.of(
                        "isPlayerInLobby", true,
                        "isPlayerReady", readyState));
            }
        }
        return toReturn;
    }

    @Override
    protected Object post(Request request, Response response) {
        Optional<ApiResponse> invalid = SessionValidator.validate(request, model);

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby newLobby = model.spawnLobby();
        return newLobby.getLobbyId();
    }

    @Override
    protected Object patch(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model)
                .or(() -> LobbyValidator.validate(lobbyId, model));

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby lobby = model.getLobby(lobbyId.get());

        if (!lobby.hasPlayer(session.get()))
            return new ApiResponse(403, "SESSION_NOT_IN_LOBBY");

        if (lobby.hasStarted())
            return new ApiResponse(423, "LOBBY_ALREADY_STARTED");

        boolean newReadyState = lobby.toggleReady(session.get());

        return Map.of(
                "status", 200,
                "message", "SUCCESS",
                "isPlayerReady", newReadyState);
    }

    @Override
    protected Object put(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model)
                .or(() -> LobbyValidator.validate(lobbyId, model));

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby lobby = model.getLobby(lobbyId.get());
        synchronized(lobby) {
            if (lobby.hasPlayer(session.get()))
                return new ApiResponse(400, "SESSION_ALREADY_IN_LOBBY");

            if (lobby.hasStarted())
                return new ApiResponse(423, "LOBBY_ALREADY_STARTED");

            if (lobby.full())
                return new ApiResponse(423, "LOBBY_FULL");

            lobby.addPlayer(session.get());
            if(lobby.full()) {
                lobby.start();
            }
        }
        return new ApiResponse(200,"SUCCESS");
    }

    @Override
    protected Object delete(Request request, Response response) {
        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        Optional<ApiResponse> invalid = SessionValidator.validate(session, model)
                .or(() -> LobbyValidator.validate(lobbyId, model));

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby lobby = model.getLobby(lobbyId.get());
        synchronized(lobby) {
            if (!lobby.hasPlayer(session.get()))
                return new ApiResponse(400, "SESSION_NOT_IN_LOBBY");

            lobby.removePlayer(session.get());
        }
        return new ApiResponse(200,"SUCCESS");
    }
}
