package api.v1;

import api.util.APIEndpoint;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class V1 extends APIEndpoint {

    public V1() {
        super("/api/v1", HttpMethod.get);
    }

    @Override
    public Object handle(Request request, Response response) {
        return "this is v1";
    }
}
