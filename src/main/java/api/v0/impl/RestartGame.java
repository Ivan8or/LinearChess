package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class RestartGame extends APIEndpoint {

    final private ChessGame game;

    public RestartGame(ChessGame game) {
        super("/reset", HttpMethod.post);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        synchronized(game) {
            game.resetBoard();
            return game.getBoard().getFen();
        }
    }

}
