package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Route;
import spark.Service;
import spark.route.HttpMethod;

public class GetBoardFen extends APIEndpoint {

    private ChessGame game;

    public GetBoardFen(ChessGame game) {
        super("/fen", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Route route() {
        return (request, response) -> {
            synchronized(game) {
                return game.getBoard().getFen();
            }
        };
    }
}
