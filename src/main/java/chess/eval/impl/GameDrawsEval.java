package chess.eval.impl;

import chess.board.LBoard;
import chess.eval.ChessEval;

public class GameDrawsEval implements ChessEval {

    @Override
    public double utility(LBoard board) {
        return (isDraw(board) ? 1 : 0);
    }

    private boolean isDraw(LBoard board) {
        return board.isDraw();
    }
}
