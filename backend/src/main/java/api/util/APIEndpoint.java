package api.util;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.route.HttpMethod;

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
    abstract public Object handle(Request request, Response response);
}
