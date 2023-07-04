package api.v1.lobbies.shops;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;

import static spark.route.HttpMethod.options;

public class V1LobbiesShops extends APIEndpoint {

    public V1LobbiesShops() {
        super("/api/v1/lobbies/shops", options);
    }

    protected Object options(Request request, Response response) {
        super.options(request, response);

        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies/shops/view", "OPTIONS", "GET"),
                new Endpoint("/api/v1/lobbies/shops/buy", "OPTIONS", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops/sell", "OPTIONS", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops/refresh", "OPTIONS", "PATCH"));
        return rootReference;
    }
}
