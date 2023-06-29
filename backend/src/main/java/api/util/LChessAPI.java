package api.util;

import spark.RouteImpl;
import spark.Service;
import spark.route.HttpMethod;

import static spark.Service.ignite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class LChessAPI {

    private Service sparkService;

    private int port;
    private long timeout;

    private final List<APIEndpoint> endpoints;

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

    public void start() {
        sparkService = ignite()
                .port(port)
                .webSocketIdleTimeoutMillis(timeout);

        sparkService.before((request, response) -> response.header(
                "Access-Control-Allow-Origin", "*"));

        for(APIEndpoint endpoint : endpoints) {
            specifyOptions(endpoint);
            registerEndpoint(endpoint);
        }
    }

    private void registerEndpoint(APIEndpoint endpoint) {
        RouteImpl routeImpl = RouteImpl.create(endpoint.getPath(), endpoint);
        for(HttpMethod method : endpoint.getMethods())
            sparkService.addRoute(method, routeImpl);
    }

    private void specifyOptions(APIEndpoint endpoint) {
        if(endpoint.getMethods().contains(HttpMethod.options))
            return;

        Set<String> methodNames = endpoint.getMethods().stream()
                .map(Enum::toString)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        String allowedMethods = String.join(", ", methodNames);

        RouteImpl optionsHandler = RouteImpl.create(endpoint.getPath(), (req, res) -> {
            res.header("Access-Control-Allow-Methods", allowedMethods);
            res.header("Access-Control-Allow-Headers", "*");
            res.header("Access-Control-Allow-Credentials", "true");
            return res;
        });
        sparkService.addRoute(HttpMethod.options, optionsHandler);
    }
}
