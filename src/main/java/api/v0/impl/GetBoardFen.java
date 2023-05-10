package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Service;

public class GetBoardFen extends APIEndpoint {

    private ChessGame game;
    public final String ENDPOINT_PATH = "/fen";

    public GetBoardFen(ChessGame game) {
        this.game = game;
    }

    @Override
    public void start(Service sparkService) {

        String fullPath = commonPath + ENDPOINT_PATH;

        sparkService.get(fullPath, (request, response) -> {
            synchronized(game) {
                return game.getBoard().getFen();
            }
        });
    }
}
