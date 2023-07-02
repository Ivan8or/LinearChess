package api.util.validator;

import model.api.Model;
import model.mappings.ApiError;
import spark.Request;

import java.util.Optional;

public interface Validator {

    Optional<ApiError> verify(Request request, Model api);

}
