package api;

import api.util.APIEndpoint;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class Root extends APIEndpoint {

    public Root() {
        super("/api", HttpMethod.get);
    }

    @Override
    public Object handle(Request request, Response response) {
        return "test";
    }
}
