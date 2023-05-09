package chess.board;

import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class LMove {

    final protected Move moveImp;

    public LMove(String move, LSide side) {
        moveImp = new Move(move, Side.values()[side.ordinal()]);
    }

    public LMove(Move move) {
        this.moveImp = move;
    }

    public String toString() {
        return moveImp.toString();
    }
}
