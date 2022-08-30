package tests;

import chess.ChessGame;
import chess.agent.MiniMaxAgent;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.*;
import org.junit.Test;

import java.util.Random;

public class ChessGameTest {

    @Test
    public void simpleGame() {

        NumPiecesEval numWhite = new NumPiecesEval(
                new LPieceType[]{LPieceType.KNIGHT},
                new LSide[]{LSide.BLACK}
        );
        NumPiecesEval numBlack = new NumPiecesEval(
                new LPieceType[]{LPieceType.ROOK},
                new LSide[]{LSide.WHITE}
        );
        RandomEval rand = new RandomEval(new Random(422));

        GameEndsEval whiteEnds = new GameEndsEval(LSide.WHITE);
        GameEndsEval blackEnds = new GameEndsEval(LSide.BLACK);

        GameDrawsEval draws = new GameDrawsEval();

        CumulativeEval w = new CumulativeEval(
                whiteEnds,
                new WeightedEval(draws, -5),
                new WeightedEval(numWhite, -1),
                rand
        );
        CumulativeEval b = new CumulativeEval(
                new WeightedEval(numBlack, -1),
                new WeightedEval(draws, -5),
                blackEnds,
                rand
        );

        MiniMaxAgent white = new MiniMaxAgent(w);
        MiniMaxAgent black = new MiniMaxAgent(b);

        white.setDepth(0);
        black.setDepth(0);

        double total = 1;
        int tied = 0;
        int blackWins = 0;
        int whiteWins = 0;

        for (int numGames = 0; numGames < total; numGames++) {
            ChessGame game = new ChessGame();
            game.setWhiteAgent(white);
            game.setBlackAgent(black);

            do {
                //System.out.println(game.getBoard() + "\n");
            } while (!game.increment());

            //System.out.println(game.getBoard() + "\n");

            if (game.getBoard().isDraw()) {
                //System.out.println("tied");
                tied++;
            } else {
                LSide winner = game.getBoard().getSideToMove().flip();
                //System.out.println(winner + " won");
                if (winner == LSide.WHITE)
                    whiteWins++;
                else
                    blackWins++;
            }
        }
//        System.out.println("STATS");
//        System.out.println("count: " + total + " (1.00)");
//        System.out.println("black: " + blackWins + " (" + (blackWins / total) + ")");
//        System.out.println("white: " + whiteWins + " (" + (whiteWins / total) + ")");
//        System.out.println("tied games: " + tied + " (" + (tied / total) + ")");

    }

    public static void main(String[] args) {
        new ChessGameTest().simpleGame();
    }
}
