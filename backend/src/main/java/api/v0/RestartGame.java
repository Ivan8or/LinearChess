package api.v0;

import api.util.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class RestartGame extends APIEndpoint {

    final private ChessGame game;

    public RestartGame(ChessGame game) {
        super("/api/v0/reset", HttpMethod.patch);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) {
        synchronized(game) {
            game.resetBoard();
            return game.getBoard().getFen();
        }
    }

}
