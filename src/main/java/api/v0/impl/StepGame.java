package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import spark.Service;

public class StepGame extends APIEndpoint {

    private ChessGame game;
    public final String ENDPOINT_PATH = "/increment";

    public StepGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public void start(Service sparkService) {

        String fullPath = commonPath + ENDPOINT_PATH;

        sparkService.get(fullPath, (request, response) -> {
            synchronized(game) {
                System.out.println(game.getBoard().getSideToMove() + " goes!");
                if (!game.isOver())
                    game.increment();

                return game.getBoard().getFen();
            }
        });
    }
}
