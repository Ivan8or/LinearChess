package api.v1;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;
import util.JsonConverter;

public class V1 extends APIEndpoint {

    public V1() {
        super("/api/v1", HttpMethod.get);
    }

    @Override
    public Object handle(Request request, Response response) {
        response.header("Content-Type","application/json");
        response.header("Server-Version","1.1.0");

        Reference rootReference = new Reference(new Endpoint[]{
                new Endpoint("/api/v1/lobbies", new String[]{"GET", "POST"}),
                new Endpoint("/api/v1/sessions", new String[]{"POST", "DELETE"})
        });
        return JsonConverter.toPrettyJson(rootReference);
    }
}
