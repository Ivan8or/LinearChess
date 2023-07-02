package api.util.validator;

import model.api.Model;
import model.mappings.ApiResponse;
import model.mappings.LobbyID;
import spark.Request;
import util.JsonConverter;

import java.util.Optional;

public class LobbyValidator {

    public static Optional<ApiResponse> validate(Request request, Model api) {
        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);
        return validate(lobbyId, api);
    }

    public static Optional<ApiResponse> validate(Optional<LobbyID> lobbyId, Model api) {
        if(lobbyId.isEmpty())
            return Optional.of(new ApiResponse(400,"NO_LOBBY_ID"));

        if(!api.lobbyExists(lobbyId.get()))
            return  Optional.of(new ApiResponse(403,"BAD_LOBBY_ID"));

        return Optional.empty();
    }
}
