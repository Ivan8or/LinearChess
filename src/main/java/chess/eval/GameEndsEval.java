package chess.eval;

import chess.board.LBoard;
import chess.board.LSide;

public class GameEndsEval implements ChessEval {

    final private LSide mySide;

    public GameEndsEval(LSide mySide) {
        this.mySide = mySide;
    }
    @Override
    public double utility(LBoard board) {

        if(!isGameOver(board))
            return 0;

        LSide turn = board.getSideToMove();
        return (turn == mySide) ? -1 : 1;
    }

    private boolean isGameOver(LBoard board) {
        return board.isMated();
    }
}
