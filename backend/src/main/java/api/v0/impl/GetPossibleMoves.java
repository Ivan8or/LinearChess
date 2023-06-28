package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

import java.util.Arrays;

public class GetPossibleMoves extends APIEndpoint {

    private final ChessGame game;

    public GetPossibleMoves(ChessGame game) {
        super("/moves", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        synchronized(game) {
            return Arrays.toString(game.getBoard().legalMoves().toArray());
        }
    }
}
