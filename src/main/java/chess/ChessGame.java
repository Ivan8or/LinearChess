package chess;

import chess.agent.ChessAgent;
import chess.board.LBoard;
import chess.board.LMove;
import chess.board.LSide;

public class ChessGame {

    private ChessAgent whiteAgent;
    private ChessAgent blackAgent;

    private LBoard board;

    public ChessGame() {
        board = new LBoard();
    }

    public void setWhiteAgent(ChessAgent whiteAgent) {
        this.whiteAgent = whiteAgent;
    }
    public void setBlackAgent(ChessAgent blackAgent) {
        this.blackAgent = blackAgent;
    }

    public boolean increment() {
        ChessAgent turnAgent = turnAgent();
        LMove bestMove = turnAgent.decideMove(board);
        board.doMove(bestMove);
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
