package api.v1;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;

import static spark.route.HttpMethod.options;

public class V1 extends APIEndpoint {

    public V1() {
        super("/api/v1", options);
    }

    public Object options(Request request, Response response) {
        super.options(request, response);

        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies", "OPTIONS", "POST", "PATCH", "PUT"),
                new Endpoint("/api/v1/sessions", "GET", "POST", "DELETE"));
        return rootReference;
    }
}
