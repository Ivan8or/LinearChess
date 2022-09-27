package chess.agent;

import chess.board.LBoard;
import chess.board.LMove;
import chess.eval.ChessEval;

import java.util.*;

public class AlphaBetaAgent extends ChessAgent {

    private int depth = 2;
    public AlphaBetaAgent(ChessEval evaluation) {
        setEvaluation(evaluation);
    }

    public AlphaBetaAgent(ChessEval evaluation, int depth) {
        setEvaluation(evaluation);
        setDepth(depth);
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public LMove decideMove(LBoard board) {

        Map<LMove, Double> allMoveUtilities = rankMoves(board);
        LMove bestMove = null;

        double bestUtility = Integer.MIN_VALUE;

        for(LMove nextMove: allMoveUtilities.keySet()) {

            if(bestUtility < allMoveUtilities.get(nextMove)) {
                bestMove = nextMove;
                bestUtility = allMoveUtilities.get(nextMove);

            }
        }
        return bestMove;
    }

    public Map<LMove, Double> rankMoves(LBoard board) {

        Map<LMove, Double> rankedMoves = new HashMap<>();
        List<LMove> allMoves = board.legalMoves();

        for(LMove nextMove: allMoves) {
            board.doMove(nextMove);
            double nextUtility = miniMax(board, depth, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undoMove();
            rankedMoves.put(nextMove, nextUtility);
        }
        return rankedMoves;
    }

    private double miniMax(LBoard board, int depth, boolean isMaximizingPlayer, double alpha, double beta) {

        if (depth == 0)
            return evaluationFunction.utility(board);

        double moveUtility = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        List<LMove> legalMoves = board.legalMoves();

        if(legalMoves.isEmpty())
            return evaluationFunction.utility(board);

        for (LMove choice : legalMoves) {
            board.doMove(choice);
            double nextMoveVal = miniMax(board, depth - 1, !isMaximizingPlayer, alpha, beta);
            board.undoMove();

            if(isMaximizingPlayer) {
                moveUtility = Math.max(nextMoveVal, moveUtility);
                alpha = Math.max(alpha, moveUtility);
            }
            else {
                moveUtility = Math.min(nextMoveVal, moveUtility);
                beta = Math.min(beta, moveUtility);
            }

            if(beta <= alpha)
                return moveUtility;
        }

        return moveUtility;
    }
}