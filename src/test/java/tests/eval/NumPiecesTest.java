package tests.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.NumPiecesEval;
import org.junit.Assert;
import org.junit.Test;

public class NumPiecesTest {

    @Test
    public void totalCountAllPieces() {
        LBoard board = new LBoard();
        NumPiecesEval numTotal = new NumPiecesEval(LPieceType.values(), LSide.values());

        double utility = numTotal.utility(board);
        Assert.assertSame(32, utility);
    }

    @Test
    public void totalCountNoPieces() {
        LBoard board = new LBoard();
        NumPiecesEval numTotal = new NumPiecesEval(LPieceType.values(), LSide.values());

        board.loadFromFen("8/8/8/8/8/8/8/8 b - - 0 1");
        double utility = numTotal.utility(board);
        Assert.assertSame(0, utility);
    }

    @Test
    public void whiteCountAllPieces() {
        LBoard board = new LBoard();
        NumPiecesEval numWhite = new NumPiecesEval(
                LPieceType.values(),
                new LSide[]{LSide.WHITE}
        );

        double utility = numWhite.utility(board);
        Assert.assertSame(16, utility);
    }

    @Test
    public void totalCountRooksKnights() {
        LBoard board = new LBoard();
        NumPiecesEval numWhite = new NumPiecesEval(
                new LPieceType[]{LPieceType.ROOK, LPieceType.KNIGHT},
                LSide.values()
        );

        double utility = numWhite.utility(board);
        Assert.assertSame(8, utility);
    }

    @Test
    public void whiteCountPawns() {
        LBoard board = new LBoard();
        NumPiecesEval numWhite = new NumPiecesEval(
                new LPieceType[]{LPieceType.PAWN},
                new LSide[]{LSide.WHITE}
        );

        double utility = numWhite.utility(board);
        Assert.assertSame(8, utility);
    }
}
