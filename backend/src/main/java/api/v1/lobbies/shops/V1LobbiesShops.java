package api.v1.lobbies.shops;

import api.util.APIEndpoint;
import model.mappings.Endpoint;
import model.mappings.Reference;
import spark.Request;
import spark.Response;

import static spark.route.HttpMethod.get;

public class V1LobbiesShops extends APIEndpoint {

    public V1LobbiesShops() {
        super("/api/v1/lobbies/shops", get);
    }

    protected Object get(Request request, Response response) {
        Reference rootReference = new Reference(
                new Endpoint("/api/v1/lobbies/shops/view", "GET"),
                new Endpoint("/api/v1/lobbies/shops/buy", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops/sell", "PATCH"),
                new Endpoint("/api/v1/lobbies/shops/refresh", "PATCH"));
        return rootReference;
    }
}
