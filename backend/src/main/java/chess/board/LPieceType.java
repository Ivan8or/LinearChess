package chess.board;

import com.github.bhlangonijr.chesslib.PieceType;

public enum LPieceType {

    PAWN,

    KNIGHT,

    BISHOP,

    ROOK,

    QUEEN,

    KING,

    NONE;

    final static private LPieceType[] pieces = LPieceType.values();

    protected static LPieceType wrap(PieceType piece) {
        if(piece == null)
            return null;
        return pieces[piece.ordinal()];
    }

}
