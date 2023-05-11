package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class StepGame extends APIEndpoint {

    private final ChessGame game;

    public StepGame(ChessGame game) {
        super("/increment", HttpMethod.patch);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        synchronized(game) {
            if (!game.isOver())
                game.increment();
            return game.getBoard().getFen();
        }
    }
}
