package chess.agent;

import chess.board.LBoard;
import chess.board.LMove;
import chess.eval.ChessEval;

import java.util.*;

public class MiniMaxAgent extends ChessAgent {

    private int depth;

    public MiniMaxAgent(ChessEval evaluation, int depth) {
        setEvaluation(evaluation);
        setDepth(depth);
    }

    public void setDepth(int depth) {
        if(depth < 0)
            throw new IllegalArgumentException("invalid lookahead depth for AlphaBeta agent");
        this.depth = depth;
    }

    public Map<LMove, Double> rankMoves(LBoard board) {

        Map<LMove, Double> rankedMoves = new HashMap<>();
        List<LMove> allMoves = board.legalMoves();

        for(LMove nextMove: allMoves) {
            board.doMove(nextMove);
            double nextUtility = miniMax(board, depth, false);
            board.undoMove();
            rankedMoves.put(nextMove, nextUtility);
        }
        return rankedMoves;
    }

    private double miniMax(LBoard board, int depth, boolean myTurn) {

        if (depth == 0)
            return evaluationFunction.utility(board);

        double moveUtility = myTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        List<LMove> legalMoves = board.legalMoves();

        if(legalMoves.isEmpty())
            return evaluationFunction.utility(board);

        for (LMove choice : legalMoves) {
            board.doMove(choice);
            double nextMoveVal = miniMax(board, depth - 1, !myTurn);
            board.undoMove();
            if (myTurn ^ (nextMoveVal < moveUtility)) {
                moveUtility = nextMoveVal;
            }
        }

        return moveUtility;
    }
}