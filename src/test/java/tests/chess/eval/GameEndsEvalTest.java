package tests.chess.eval;

import chess.board.LBoard;
import chess.board.LSide;
import chess.eval.impl.GameEndsEval;
import org.junit.Assert;
import org.junit.Test;

public class GameEndsEvalTest {


    @Test
    public void whiteNewBoard() {
        LBoard board = new LBoard();
        GameEndsEval whiteEnd = new GameEndsEval(LSide.WHITE);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }

    @Test
    public void whiteWins() {
        LBoard board = new LBoard();
        board.loadFromFen("6Qk/7Q/8/K7/8/8/8/8 b - - 0 1");
        GameEndsEval whiteEnd = new GameEndsEval(LSide.WHITE);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(1, utility, 0.0);
    }

    @Test
    public void whiteLoses() {
        LBoard board = new LBoard();
        board.loadFromFen("6qK/7q/8/k7/8/8/8/8 w - - 0 1");
        GameEndsEval whiteEnd = new GameEndsEval(LSide.WHITE);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(-1, utility, 0.0);
    }

    @Test
    public void blackNewBoard() {
        LBoard board = new LBoard();
        GameEndsEval whiteEnd = new GameEndsEval(LSide.BLACK);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }

    @Test
    public void whiteDraws() {
        LBoard board = new LBoard();
        board.loadFromFen("7k/5K2/8/8/8/8/8/8 w - - 0 1");
        GameEndsEval whiteEnd = new GameEndsEval(LSide.WHITE);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }

    @Test
    public void blackWins() {
        LBoard board = new LBoard();
        board.loadFromFen("6qK/7q/8/k7/8/8/8/8 w - - 0 1");
        GameEndsEval whiteEnd = new GameEndsEval(LSide.BLACK);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(1, utility, 0.0);
    }

    @Test
    public void blackLoses() {
        LBoard board = new LBoard();
        board.loadFromFen("6Qk/7Q/8/K7/8/8/8/8 b - - 0 1");
        GameEndsEval whiteEnd = new GameEndsEval(LSide.BLACK);
        double utility = whiteEnd.utility(board);
        Assert.assertEquals(-1, utility, 0.0);
    }
}
