package api.v0;

import api.util.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

import java.util.Arrays;

public class GetPossibleMoves extends APIEndpoint {

    private final ChessGame game;

    public GetPossibleMoves(ChessGame game) {
        super("/api/v0/moves", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) {
        synchronized(game) {
            return Arrays.toString(game.getBoard().legalMoves().toArray());
        }
    }
}
