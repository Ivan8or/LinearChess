package chess.board;

import com.github.bhlangonijr.chesslib.Side;

public enum LSide {
    WHITE,
    BLACK;

    final static private LSide[] sides = LSide.values();

    public LSide flip() {
        return sides[(this.ordinal() + 1) % 2];
    }
    protected static LSide wrap(Side side) {
        if(side == null)
            return null;
        return sides[side.ordinal()];
    }
}
