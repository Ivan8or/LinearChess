package tests.agent;

import chess.ChessGame;
import chess.agent.impl.AlphaBetaAgent;
import chess.agent.ChessAgent;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.*;
import chess.eval.impl.*;
import org.junit.Test;
import org.junit.Assert;

import java.util.Random;

public class AlphaBetaAgentTest {


    @Test
    public void nullEval() {
        try {
            new AlphaBetaAgent(null, 3);
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
            new AlphaBetaAgent(eval, 0);
            new AlphaBetaAgent(eval, 3);
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
            AlphaBetaAgent a = new AlphaBetaAgent(eval, -1);
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

        DistanceAcrossEval blackDist =
                new DistanceAcrossEval(LPieceType.values(), new LSide[]{LSide.BLACK});

        DistanceAcrossEval whiteDist =
                new DistanceAcrossEval(LPieceType.values(), new LSide[]{LSide.WHITE});

        RandomEval rand = new RandomEval(new Random(422));

        GameEndsEval whiteEnds = new GameEndsEval(LSide.WHITE);
        GameEndsEval blackEnds = new GameEndsEval(LSide.BLACK);

        GameDrawsEval draws = new GameDrawsEval();

        CumulativeEval w = new CumulativeEval(
                whiteEnds,
                new WeightedEval(draws, -5),
                new WeightedEval(numWhite, -1),
                new WeightedEval(blackDist, 0.5),
                rand
        );
        CumulativeEval b = new CumulativeEval(
                new WeightedEval(numBlack, -1),
                new WeightedEval(draws, -5),
                blackEnds,
                new WeightedEval(whiteDist, 0.5),
                rand
        );

        ChessAgent white = new AlphaBetaAgent(w, 1);
        ChessAgent black = new AlphaBetaAgent(b, 1);

        double total = 1;

        for (int numGames = 0; numGames < total; numGames++) {
            ChessGame game = new ChessGame();
            game.setWhiteAgent(white);
            game.setBlackAgent(black);

            while(!game.increment());
        }
    }
}
