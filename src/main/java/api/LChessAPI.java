package api;

import spark.RouteImpl;
import spark.Service;
import spark.route.HttpMethod;

import static spark.Service.ignite;

import java.util.ArrayList;
import java.util.List;


public class LChessAPI {

    private Service sparkService;

    private int port;
    private long timeout;

    private List<APIEndpoint> endpoints;

    public LChessAPI() {
        port = 8080;
        timeout = 8000;
        endpoints = new ArrayList<>();
    }

    public LChessAPI withPort(int port) {
        this.port = port;
        return this;
    }
    public LChessAPI withTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }
    public LChessAPI withEndpoint(APIEndpoint endpoint) {
        endpoints.add(endpoint);
        return this;
    }

    public LChessAPI start() {
        sparkService = ignite()
                .port(port)
                .webSocketIdleTimeoutMillis(timeout);

        sparkService.before((request, response) -> response.header(
                "Access-Control-Allow-Origin", "*"));

        for(APIEndpoint endpoint : endpoints) {
            handlePreflight(endpoint);
            registerEndpoint(endpoint);
        }

        return this;
    }

    private void registerEndpoint(APIEndpoint endpoint) {
        RouteImpl routeImpl = RouteImpl.create(endpoint.getPath(), endpoint);
        sparkService.addRoute(endpoint.getMethod(), routeImpl);
    }

    private void handlePreflight(APIEndpoint endpoint) {
        RouteImpl optionsHandler = RouteImpl.create(endpoint.getPath(), (req, res) -> {
            res.header("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE");
            return res;
        });
        sparkService.addRoute(HttpMethod.options, optionsHandler);
    }
}
