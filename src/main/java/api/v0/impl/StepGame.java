package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Route;
import spark.RouteImpl;
import spark.Service;
import spark.route.HttpMethod;

public class StepGame extends APIEndpoint {

    private ChessGame game;

    public StepGame(ChessGame game) {
        super("/increment", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Route route() {
        return (request, response) -> {
            synchronized(game) {
                if (!game.isOver())
                    game.increment();

                return game.getBoard().getFen();
            }
        };
    }
}
