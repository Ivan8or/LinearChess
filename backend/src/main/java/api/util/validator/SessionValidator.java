package api.util.validator;

import model.api.Model;
import model.mappings.ApiError;
import model.mappings.Session;
import spark.Request;
import util.JsonConverter;

import java.util.Optional;

public class SessionValidator implements Validator {

    @Override
    public Optional<ApiError> verify(Request request, Model api) {

        String sessionJson = request.headers("session");
        Optional<Session> session = JsonConverter.fromJson(sessionJson, Session.class);

        if(session.isEmpty())
            return Optional.of(new ApiError(401, "NO_SESSION_HEADER"));

        if(!api.getSessions().validSession(session.get()))
            return Optional.of(new ApiError(403, "BAD_SESSION_HEADER"));

        return Optional.empty();
    }
}
