package tests.agent;

import chess.ChessGame;
import chess.agent.impl.AlphaBetaAgent;
import chess.agent.ChessAgent;
import chess.agent.impl.MiniMaxAgent;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.*;
import chess.eval.impl.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class MiniMaxAgentTest {

    @Test
    public void nullEval() {
        try {
            new MiniMaxAgent(null, 3);
        }catch(Exception e) {
            e.printStackTrace();
            return;
        }
        Assert.fail();
    }

    @Test
    public void depth0AndUp() {
        try {
            ChessEval eval = new ConstantEval(1);
            new MiniMaxAgent(eval, 0);
            new MiniMaxAgent(eval, 3);
            new AlphaBetaAgent(eval, 30);
        }catch(Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void depthNegative() {
        try {
            ChessEval eval = new ConstantEval(1);
            new MiniMaxAgent(eval, -1);
        }catch(IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }
        Assert.fail();
    }

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

        ChessAgent white = new MiniMaxAgent(w, 1);
        ChessAgent black = new MiniMaxAgent(b, 1);

        double total = 1;

        for (int numGames = 0; numGames < total; numGames++) {
            ChessGame game = new ChessGame();
            game.setWhiteAgent(white);
            game.setBlackAgent(black);

            while(!game.increment());
        }
    }
}
