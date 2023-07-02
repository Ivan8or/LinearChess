package api.util.validator;

import model.api.Model;
import model.mappings.ApiError;
import model.mappings.LobbyID;
import spark.Request;
import util.JsonConverter;

import java.util.Optional;

public class LobbyValidator implements Validator {

    @Override
    public Optional<ApiError> verify(Request request, Model api) {

        String lobbyJson = request.headers("lobby");
        Optional<LobbyID> lobbyId = JsonConverter.fromJson(lobbyJson, LobbyID.class);

        if(lobbyId.isEmpty())
            return Optional.of(new ApiError(400,"NO_LOBBY_ID"));

        if(!api.lobbyExists(lobbyId.get()))
            return  Optional.of(new ApiError(403,"BAD_LOBBY_ID"));

        return Optional.empty();
    }
}
