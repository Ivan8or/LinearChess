package api;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.route.HttpMethod;

public abstract class APIEndpoint implements Route {

    protected String COMMON_PATH;
    protected String ENDPOINT_PATH;
    protected String FULL_PATH;
    protected HttpMethod HTTP_METHOD;

    public String getPath() {
        return FULL_PATH;
    }
    public HttpMethod getMethod() {
        return HTTP_METHOD;
    }

    public APIEndpoint(String ENDPOINT_PATH, HttpMethod HTTP_METHOD) {
        this.ENDPOINT_PATH = ENDPOINT_PATH;
        this.HTTP_METHOD = HTTP_METHOD;
        this.COMMON_PATH = null;
    }

    public APIEndpoint withCommonPath(String commonPath) {
        COMMON_PATH = commonPath;
        FULL_PATH = COMMON_PATH + ENDPOINT_PATH;
        return this;
    }
    public boolean commonPathSet() {
        return COMMON_PATH != null;
    }

    @Override
    abstract public Object handle(Request request, Response response) throws Exception;
}
