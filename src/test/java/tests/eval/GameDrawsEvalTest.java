package tests.eval;

import chess.board.LBoard;
import chess.eval.impl.GameDrawsEval;
import org.junit.Assert;
import org.junit.Test;

public class GameDrawsEvalTest {


    @Test
    public void emptyBoard() {
        LBoard board = new LBoard();
        GameDrawsEval drawEval = new GameDrawsEval();
        double utility = drawEval.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }

    @Test
    public void drawnBoard() {
        LBoard board = new LBoard();
        board.loadFromFen("7k/8/8/K7/8/8/8/8 b - - 0 1");
        GameDrawsEval drawEval = new GameDrawsEval();
        double utility = drawEval.utility(board);
        Assert.assertEquals(1, utility, 0.0);
    }

    @Test
    public void nonDrawnBoard() {
        LBoard board = new LBoard();
        board.loadFromFen("7k/8/8/K7/8/8/6R1/8 b - - 0 1");
        GameDrawsEval drawEval = new GameDrawsEval();
        double utility = drawEval.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }
}
