package chess.eval;

import chess.board.LBoard;

public class WeightedEval implements ChessEval {

    final private ChessEval baseEval;
    final private double coefficient;

    public WeightedEval(ChessEval baseEvaluation, double coefficient) {
        this.baseEval = baseEvaluation;
        this.coefficient = coefficient;
    }

    @Override
    public double utility(LBoard board) {
        return baseEval.utility(board) * coefficient;
    }
}
