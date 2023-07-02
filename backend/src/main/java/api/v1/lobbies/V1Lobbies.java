package api.v1.lobbies;

import api.util.APIEndpoint;
import api.util.validator.SessionValidator;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.ApiResponse;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;
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
    protected Object get(Request request, Response response) {
        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies/boards", "GET"),
                new Endpoint("/api/v1/lobbies/inventories", "GET", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops", "GET"));
        return rootReference;
    }

    @Override
    protected Object post(Request request, Response response) {
        Optional<ApiResponse> invalid = SessionValidator.validate(request, model);

        if(invalid.isPresent())
            return invalid.get();

        ChessLobby newLobby = model.spawnLobby();
        return newLobby.getLobbyId();
    }
}
