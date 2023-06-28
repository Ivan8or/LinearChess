package chess.eval.impl;

import chess.board.LBoard;
import chess.eval.ChessEval;

public class WeightedEval implements ChessEval {

    final public ChessEval baseEval;
    final public double coefficient;

    public WeightedEval(ChessEval baseEvaluation, double coefficient) {
        this.baseEval = baseEvaluation;
        this.coefficient = coefficient;
    }

    @Override
    public double utility(LBoard board) {
        return baseEval.utility(board) * coefficient;
    }
}
