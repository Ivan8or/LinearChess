package api;

import spark.Route;
import spark.RouteImpl;
import spark.Service;

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

        sparkService.after((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        for(APIEndpoint endpoint : endpoints)
            registerEndpoint(endpoint);

        return this;
    }

    private void registerEndpoint(APIEndpoint endpoint) {
        RouteImpl routeImpl = RouteImpl.create(endpoint.getPath(), endpoint.route());
        sparkService.addRoute(endpoint.getMethod(), routeImpl);
    }
}
