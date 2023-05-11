package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import chess.board.LMove;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

import java.util.Optional;

public class MakeMove extends APIEndpoint {

    private final ChessGame game;

    public MakeMove(ChessGame game) {
        super("/move/:move", HttpMethod.get);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        synchronized(game) {
            String moveString = request.params(":move");

            Optional<LMove> move = game.getBoard().legalMoves().stream()
                    .filter(s -> s.toString().equals(moveString))
                    .findFirst();

            if (!move.isPresent())
                return "bad-move";

            game.getBoard().doMove(move.get());

            return game.getBoard().getFen();
        }
    }
}
