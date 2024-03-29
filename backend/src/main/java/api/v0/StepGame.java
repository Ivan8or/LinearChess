package api.v0;

import api.util.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class StepGame extends APIEndpoint {

    private final ChessGame game;

    public StepGame(ChessGame game) {
        super("/api/v0/increment", HttpMethod.patch);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) {
        synchronized(game) {
            if (!game.isOver())
                game.increment();
            return game.getBoard().getFen();
        }
    }
}
