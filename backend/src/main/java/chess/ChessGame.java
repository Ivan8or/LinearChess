package chess;

import chess.agent.ChessAgent;
import chess.agent.impl.AlphaBetaAgent;
import chess.board.LBoard;
import chess.board.LMove;
import chess.board.LSide;
import chess.eval.impl.RandomEval;

import java.util.Random;

public class ChessGame {

    private ChessAgent whiteAgent;
    private ChessAgent blackAgent;

    private LBoard board;

    public ChessGame() {
        board = new LBoard();
        whiteAgent = new AlphaBetaAgent(new RandomEval(new Random()), 2);
        blackAgent = new AlphaBetaAgent(new RandomEval(new Random()), 2);
    }

    public void setWhiteAgent(ChessAgent whiteAgent) {
        this.whiteAgent = whiteAgent;
    }
    public void setBlackAgent(ChessAgent blackAgent) {
        this.blackAgent = blackAgent;
    }

    public void setAgent(ChessAgent agent, LSide side) {
        if(side == LSide.BLACK)
            this.blackAgent = agent;
        else
            this.whiteAgent = agent;
    }

    public boolean increment() {
        ChessAgent turnAgent = turnAgent();
        LMove bestMove = turnAgent.decideMove(board);
        board.doMove(bestMove);
        return isOver();
    }

    public boolean isOver() {
        return board.isMated() || board.isDraw();
    }

    public void resetBoard() {
        board = new LBoard();
    }

    public LBoard getBoard() {
        return board;
    }

    private ChessAgent turnAgent() {
        if(board.getSideToMove() == LSide.WHITE)
            return whiteAgent;
        return blackAgent;
    }
}
