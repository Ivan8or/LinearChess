package api.util.validator;

import model.api.Model;
import model.mappings.ApiResponse;
import model.mappings.Session;
import spark.Request;
import util.JsonConverter;

import java.util.Optional;

public class SessionValidator {

    public static Optional<ApiResponse> validate(Request request, Model api) {

        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);
        return validate(session, api);
    }

    public static Optional<ApiResponse> validate(Optional<Session> session, Model api) {
        if(session.isEmpty())
            return Optional.of(new ApiResponse(401, "NO_SESSION_HEADER"));

        if(!api.getSessions().validSession(session.get()))
            return Optional.of(new ApiResponse(403, "BAD_SESSION_HEADER"));

        return Optional.empty();
    }
}
