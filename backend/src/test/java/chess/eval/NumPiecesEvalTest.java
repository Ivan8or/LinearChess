package chess.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.impl.NumPiecesEval;
import org.junit.Assert;
import org.junit.Test;

public class NumPiecesEvalTest {

    @Test
    public void totalCountAllPieces() {
        LBoard board = new LBoard();
        NumPiecesEval numTotal = new NumPiecesEval(LPieceType.values(), LSide.values());

        double utility = numTotal.utility(board);
        Assert.assertEquals(32, utility, 0.0);
    }

    @Test
    public void totalCountNoPieces() {
        LBoard board = new LBoard();
        NumPiecesEval numTotal = new NumPiecesEval(LPieceType.values(), LSide.values());

        board.loadFromFen("8/8/8/8/8/8/8/8 b - - 0 1");
        double utility = numTotal.utility(board);
        Assert.assertEquals(0, utility, 0.0);
    }

    @Test
    public void whiteCountAllPieces() {
        LBoard board = new LBoard();
        NumPiecesEval numWhite = new NumPiecesEval(
                LPieceType.values(),
                new LSide[]{LSide.WHITE}
        );

        double utility = numWhite.utility(board);
        Assert.assertEquals(16, utility, 0.0);
    }

    @Test
    public void totalCountRooksKnights() {
        LBoard board = new LBoard();
        NumPiecesEval numWhite = new NumPiecesEval(
                new LPieceType[]{LPieceType.ROOK, LPieceType.KNIGHT},
                LSide.values()
        );

        double utility = numWhite.utility(board);
        Assert.assertEquals(8, utility, 0.0);
    }

    @Test
    public void whiteCountPawns() {
        LBoard board = new LBoard();
        NumPiecesEval numWhite = new NumPiecesEval(
                new LPieceType[]{LPieceType.PAWN},
                new LSide[]{LSide.WHITE}
        );

        double utility = numWhite.utility(board);
        Assert.assertEquals(8, utility, 0.0);
    }
}
