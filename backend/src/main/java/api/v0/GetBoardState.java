package api.v0;

import api.util.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class GetBoardState extends APIEndpoint {

    private final ChessGame game;

    public GetBoardState(ChessGame game) {
        super("/api/v0/fen", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) {
        synchronized(game) {
            return game.getBoard().getFen();
        }
    }
}
