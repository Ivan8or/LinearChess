package tests.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.impl.DistanceToMidEval;
import org.junit.Assert;
import org.junit.Test;

public class DistanceToMidEvalTest {

    @Test
    public void singleWhitePawnDistance() {
        LBoard board = new LBoard();
        board.loadFromFen("8/4P3/8/8/8/8/8/k6K b - - 0 1");
        DistanceToMidEval pawnDistance = new DistanceToMidEval(
                LPieceType.PAWN,
                LSide.values());

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(2.5, utility, 0.0);
    }

    @Test
    public void singleBlackPawnDistance() {
        LBoard board = new LBoard();
        board.loadFromFen("8/8/8/p7/8/8/8/k6K b - - 0 1");
        DistanceToMidEval pawnDistance = new DistanceToMidEval(
                LPieceType.PAWN,
                LSide.BLACK);

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(3.5, utility, 0.0);
    }

    @Test
    public void whitePawnsInitDistance() {
        LBoard board = new LBoard();
        DistanceToMidEval pawnDistance = new DistanceToMidEval(
                LPieceType.PAWN,
                LSide.WHITE);

        double utility = pawnDistance.utility(board);
        Assert.assertEquals(22, utility, 0.0);
    }

    @Test
    public void allWhitePieceInitDistance() {
        LBoard board = new LBoard();

        DistanceToMidEval allDistance = new DistanceToMidEval(
                LPieceType.values(),
                LSide.WHITE);

        double utility = allDistance.utility(board);
        Assert.assertEquals(50, utility, 0.0);
    }

    @Test
    public void allPieceInitDistance() {
        LBoard board = new LBoard();

        DistanceToMidEval allDistance = new DistanceToMidEval(
                LPieceType.values(),
                LSide.values());

        double utility = allDistance.utility(board);
        Assert.assertEquals(100, utility, 0.0);
    }
}
