package chess.agent;

import chess.board.LBoard;
import chess.board.LMove;
import chess.eval.ChessEval;

import java.util.Map;

public abstract class ChessAgent {

    protected ChessEval evaluationFunction;

    public void setEvaluation(ChessEval evaluationFunction) {
        if(evaluationFunction == null)
            throw new IllegalArgumentException("ChessAgent may not have null evaluationFunction");

        this.evaluationFunction = evaluationFunction;
    }

    public LMove decideMove(LBoard board) {

        Map<LMove, Double> allMoveUtilities = rankMoves(board);

        LMove bestMove = null;
        double bestUtility = Double.NEGATIVE_INFINITY;

        for(LMove nextMove: allMoveUtilities.keySet()) {
            double nextUtility = allMoveUtilities.get(nextMove);
            if(nextUtility > bestUtility) {
                bestMove = nextMove;
                bestUtility = nextUtility;
            }
        }
        return bestMove;
    }

    public abstract Map<LMove, Double> rankMoves(LBoard board);

}
