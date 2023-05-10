package api;

import spark.Route;
import spark.route.HttpMethod;

public abstract class APIEndpoint {

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
    }

    public APIEndpoint withCommonPath(String commonPath) {
        FULL_PATH = commonPath + ENDPOINT_PATH;
        return this;
    }

    public abstract Route route();
}
