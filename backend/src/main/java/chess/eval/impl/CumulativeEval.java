package chess.eval.impl;

import chess.board.LBoard;
import chess.eval.ChessEval;

import java.util.Arrays;

public class CumulativeEval implements ChessEval {

    final public ChessEval[] evaluations;

    public CumulativeEval(ChessEval... evaluations) {
        this.evaluations = evaluations;
    }


    @Override
    public double utility(LBoard board) {
        return Arrays.stream(evaluations)
                .mapToDouble(eval -> eval.utility(board))
                .sum();
    }
}
