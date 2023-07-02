package api.v1;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class V1 extends APIEndpoint {

    public V1() {
        super("/api/v1", HttpMethod.get);
    }

    public Object get(Request request, Response response) {
        response.header("Content-Type","application/json");
        response.header("Server-Version","1.1.0");

        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies", "GET", "POST", "PUT"),
                new Endpoint("/api/v1/sessions", "POST", "DELETE"));
        return rootReference;
    }
}
