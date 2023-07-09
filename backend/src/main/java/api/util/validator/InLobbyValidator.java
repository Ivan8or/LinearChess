package api.util.validator;

import model.api.Model;
import model.mappings.ApiResponse;
import model.mappings.LobbyID;
import model.mappings.Session;

import java.util.Optional;

public class InLobbyValidator {

    public static Optional<ApiResponse> validate(Optional<Session> session, Optional<LobbyID> lobbyId, Model api) {

        if(session.isEmpty())
            return Optional.of(new ApiResponse(401, "NO_SESSION_HEADER"));

        if(!api.getSessions().validSession(session.get()))
            return Optional.of(new ApiResponse(403, "BAD_SESSION_HEADER"));

        if(lobbyId.isEmpty())
            return Optional.of(new ApiResponse(400,"NO_LOBBY_ID"));

        if(!api.lobbyExists(lobbyId.get()))
            return  Optional.of(new ApiResponse(403,"BAD_LOBBY_ID"));

        if(!api.getLobby(lobbyId.get()).hasPlayer(session.get()))
            return Optional.of(new ApiResponse(403, "SESSION_NOT_IN_LOBBY"));

        return Optional.empty();
    }

}
