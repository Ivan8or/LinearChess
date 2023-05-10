package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class GetBoardFen extends APIEndpoint {

    private final ChessGame game;

    public GetBoardFen(ChessGame game) {
        super("/fen", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        synchronized(game) {
            return game.getBoard().getFen();
        }
    }
}
