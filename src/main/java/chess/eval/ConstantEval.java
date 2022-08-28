package chess.eval;

import chess.board.LBoard;

public class ConstantEval implements ChessEval {

    final private double constant;

    public ConstantEval(double constant) {
        this.constant = constant;
    }

    @Override
    public double utility(LBoard board) {
        return constant;
    }
}
