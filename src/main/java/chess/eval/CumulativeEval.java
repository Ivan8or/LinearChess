package chess.eval;

import chess.board.LBoard;
import chess.board.LSide;

import java.util.Arrays;

public class CumulativeEval implements ChessEval {

    final private ChessEval[] evaluations;

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
