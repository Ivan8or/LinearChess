package chess.board;

import com.github.bhlangonijr.chesslib.Piece;

public class LPiece {

    final protected Piece pieceImp;
    public LPiece(Piece piece) {
        this.pieceImp = piece;
    }

    public LSide getPieceSide() {
        return LSide.wrap(pieceImp.getPieceSide());
    }

    public LPieceType getPieceType() {
        return LPieceType.wrap(pieceImp.getPieceType());
    }

    public boolean exists() {
        return pieceImp.getPieceSide() != null;
    }
    public String toString() {
        return pieceImp.toString();
    }
}

