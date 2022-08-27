package chess.agent;

import chess.board.LBoard;
import chess.board.LMove;
import chess.eval.ChessEval;

import java.util.*;

public class MiniMaxAgent extends ChessAgent {

    private int depth = 2;
    public MiniMaxAgent(ChessEval evaluation) {
        setEvaluation(evaluation);
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
        if(bestMove == null) {
            System.out.println("move will be null, map is "+allMoveUtilities);
        }
        return bestMove;
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