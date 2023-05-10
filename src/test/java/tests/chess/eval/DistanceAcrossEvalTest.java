package tests.chess.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.impl.DistanceAcrossEval;
import org.junit.Assert;
import org.junit.Test;

public class DistanceAcrossEvalTest {

    @Test
    public void whitePawnInitDistance() {
        LBoard board = new LBoard();
        DistanceAcrossEval pawnDistance = new DistanceAcrossEval(
                new LPieceType[]{LPieceType.PAWN},
                new LSide[]{LSide.WHITE});

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(8, utility, 0.0);
    }

    @Test
    public void blackPawnInitDistance() {
        LBoard board = new LBoard();
        DistanceAcrossEval pawnDistance = new DistanceAcrossEval(
                new LPieceType[]{LPieceType.PAWN},
                new LSide[]{LSide.BLACK});

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(8, utility, 0.0);
    }

    @Test
    public void allPawnInitDistance() {
        LBoard board = new LBoard();
        DistanceAcrossEval pawnDistance = new DistanceAcrossEval(
                new LPieceType[]{LPieceType.PAWN},
                LSide.values());

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(16, utility, 0.0);
    }

    @Test
    public void allQueenInitDistance() {
        LBoard board = new LBoard();
        DistanceAcrossEval pawnDistance = new DistanceAcrossEval(
                new LPieceType[]{LPieceType.QUEEN},
                LSide.values());

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }

    @Test
    public void allPieceDistance() {
        LBoard board = new LBoard();
        board.loadFromFen("8/8/qQqQnNrR/8/8/8/k6K/qQ6 b - - 0 1");
        DistanceAcrossEval pawnDistance = new DistanceAcrossEval(
                LPieceType.values(),
                LSide.values());

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(42, utility, 0.0);
    }
}
