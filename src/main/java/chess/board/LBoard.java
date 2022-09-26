package chess.board;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;

import java.util.List;

public class LBoard {

    final protected Board boardImp;

    public LBoard() {
        boardImp = new Board();
    }
    public LBoard(Board board) {
        boardImp = board;
    }
    public boolean isDraw() {
        return boardImp.isDraw();
    }
    public boolean isMated() {
        return boardImp.isMated();
    }
    public LSide getSideToMove() {
        Side side = boardImp.getSideToMove();
        return (side == Side.WHITE) ? LSide.WHITE : LSide.BLACK;
    }
    public LPiece getPiece(LSquare square) {

        Square squareImp = Square.valueOf(square.name());
        Piece pieceImp = boardImp.getPiece(squareImp);
        return new LPiece(pieceImp);
    }

    public boolean doMove(LMove move) {
        return boardImp.doMove(move.moveImp);
    }

    public LMove undoMove() {
        return new LMove(boardImp.undoMove());
    }

    public List<LMove> legalMoves() {
        return boardImp.legalMoves().stream().map(LMove::new).toList();
    }
    public void loadFromFen(String fen) {
        boardImp.loadFromFen(fen);
    }
    public String getFen() {
        return boardImp.getFen();
    }
    public LBoard clone() {
        return new LBoard(boardImp.clone());
    }

    public String toString() {
        return boardImp.toString();
    }
}
