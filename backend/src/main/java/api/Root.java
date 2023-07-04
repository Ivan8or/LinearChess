package api;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;

import static spark.route.HttpMethod.options;

public class Root extends APIEndpoint {

    public Root() {
        super("/api", options);
    }

    public Object options(Request request, Response response) {
        super.options(request, response);

        Reference rootReference = new Reference(
                new Endpoint("/api/v1", "OPTIONS"));
        return rootReference;
    }
}
