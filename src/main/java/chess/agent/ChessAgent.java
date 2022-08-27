package chess.agent;

import chess.board.LBoard;
import chess.board.LMove;
import chess.eval.ChessEval;

import java.util.Map;

public abstract class ChessAgent {

    protected ChessEval evaluationFunction;

    public void setEvaluation(ChessEval evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
    }

    public abstract LMove decideMove(LBoard board);

    public abstract Map<LMove, Double> rankMoves(LBoard board);

}
