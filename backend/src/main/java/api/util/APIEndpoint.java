package api.util;

import model.mappings.ApiResponse;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.route.HttpMethod;
import util.JsonConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static spark.route.HttpMethod.options;

public abstract class APIEndpoint implements Route {

    protected final Set<HttpMethod> HTTP_METHODS;
    protected final String ENDPOINT_PATH;

    public String getPath() {
        return ENDPOINT_PATH;
    }

    public Set<HttpMethod> getMethods() {
        return HTTP_METHODS;
    }

    public APIEndpoint(String path, HttpMethod... methods) {
        this.ENDPOINT_PATH = path;
        this.HTTP_METHODS = new HashSet<>(List.of(methods));
        HTTP_METHODS.add(options);
    }

    @Override
    public Object handle(Request request, Response response) {
        response.header("Content-Type", "application/json");
        response.header("Server-Version", "1.1.0");

        Object result = null;

        try {
            HttpMethod method = HttpMethod.get(request.requestMethod().toLowerCase());
            result = switch (method) {
                case get -> get(request, response);
                case post -> post(request, response);
                case patch -> patch(request, response);
                case delete -> delete(request, response);
                case put -> put(request, response);
                case head -> head(request, response);
                case options -> options(request, response);
                default -> new ApiResponse(405, "METHOD_NOT_SUPPORTED");
            };
        }catch(Exception e) {
            e.printStackTrace();
            result = new ApiResponse(500, "INTERNAL_ERROR_OCCURED");
        }

        if(result instanceof ApiResponse)
            response.status(((ApiResponse) result).status());

        return JsonConverter.toPrettyJson(result);
    }

    protected Object options(Request request, Response response) {
        Set<String> methodNames = getMethods().stream()
                .map(Enum::toString)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        String allowedMethods = String.join(", ", methodNames);

        response.header("Access-Control-Allow-Methods", allowedMethods);
        response.header("Access-Control-Allow-Headers", "*");
        response.header("Access-Control-Allow-Credentials", "true");
        return null;
    }

    protected Object get(Request request, Response response) {
        return new ApiResponse(405,"METHOD_NOT_SUPPORTED");
    }

    protected Object post(Request request, Response response) {
        return new ApiResponse(405,"METHOD_NOT_SUPPORTED");
    }

    protected Object patch(Request request, Response response) {
        return new ApiResponse(405,"METHOD_NOT_SUPPORTED");
    }

    protected Object delete(Request request, Response response) {
        return new ApiResponse(405,"METHOD_NOT_SUPPORTED");
    }

    protected Object put(Request request, Response response) {
        return new ApiResponse(405,"METHOD_NOT_SUPPORTED");
    }

    protected Object head(Request request, Response response) {
        return new ApiResponse(405,"METHOD_NOT_SUPPORTED");
    }
}
