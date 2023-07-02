package api.util;

import model.mappings.ApiError;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.route.HttpMethod;
import util.JsonConverter;

import java.util.Set;

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
        this.HTTP_METHODS = Set.of(methods);
    }

    @Override
    public Object handle(Request request, Response response) {
        try {
            response.header("Content-Type", "application/json");
            response.header("Server-Version", "1.1.0");
            HttpMethod method = HttpMethod.get(request.requestMethod().toLowerCase());
            switch (method) {
                case get:
                    return get(request, response);
                case post:
                    return post(request, response);
                case patch:
                    return patch(request, response);
                case delete:
                    return delete(request, response);
                case put:
                    return put(request, response);
                case head:
                    return head(request, response);
                case options:
                    return options(request, response);
                default:
                    response.status(405);
                    return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
            }
        }catch(Exception e) {
            e.printStackTrace();
            response.status(500);
            return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
        }
    }

    protected Object get(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    protected Object post(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    protected Object patch(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    protected Object delete(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    protected Object options(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    protected Object put(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }

    protected Object head(Request request, Response response) {
        response.status(405);
        return JsonConverter.toPrettyJson(new ApiError("METHOD_NOT_SUPPORTED"));
    }
}
