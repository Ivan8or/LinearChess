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

    @Override
    public Object handle(Request request, Response response) {
        response.header("Content-Type","application/json");

        Reference rootReference = new Reference(new Endpoint[]{
                new Endpoint("/api/v1", new String[]{"GET"})
        });
        return JsonConverter.toPrettyJson(rootReference);
    }
}
