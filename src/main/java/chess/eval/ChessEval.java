package chess.eval;

import chess.board.LBoard;

public interface ChessEval {

    double utility(LBoard board);
}
