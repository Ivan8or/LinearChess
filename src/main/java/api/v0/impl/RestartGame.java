package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Route;
import spark.RouteImpl;
import spark.Service;
import spark.route.HttpMethod;

public class RestartGame extends APIEndpoint {

    final private ChessGame game;

    public RestartGame(ChessGame game) {
        super("/reset", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Route route() {
        return (request, response) -> {
            synchronized(game) {
                game.resetBoard();
                return game.getBoard().getFen();
            }
        };
    }
}
