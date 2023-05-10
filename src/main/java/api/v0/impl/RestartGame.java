package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Service;

public class RestartGame extends APIEndpoint {

    private ChessGame game;
    public final String ENDPOINT_PATH = "/reset";

    public RestartGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public void start(Service sparkService) {

        String fullPath = commonPath + ENDPOINT_PATH;

        sparkService.get(fullPath, (request, response) -> {
            synchronized(game) {
                game.resetBoard();
                return game.getBoard().getFen();
            }
        });
    }
}
