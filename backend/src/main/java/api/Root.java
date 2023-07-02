package api;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;
import util.JsonConverter;

public class Root extends APIEndpoint {

    public Root() {
        super("/api", HttpMethod.get);
    }

    public Object get(Request request, Response response) {
        response.header("Content-Type","application/json");
        response.header("Server-Version","1.1.0");

        Reference rootReference = new Reference(
                new Endpoint("/api/v1", "GET"));
        return rootReference;
    }
}
