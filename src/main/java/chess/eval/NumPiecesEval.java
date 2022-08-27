package chess.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.board.LSquare;

import java.util.Arrays;
import java.util.List;

public class NumPiecesEval implements ChessEval {

    final private List<LPieceType> validTypes;
    final private List<LSide> validSides;
    public NumPiecesEval(LPieceType[] validTypes, LSide[] validSides) {
        this.validTypes = List.of(validTypes);
        this.validSides = List.of(validSides);
    }
    @Override
    public double utility(LBoard board) {
        return piecesOnBoard(board);
    }

    private int piecesOnBoard(LBoard board) {

        return (int) Arrays.stream(LSquare.values())
                .filter(square -> square != LSquare.NONE)
                .filter(square -> board.getPiece(square).getPieceSide() != null)
                .filter(square -> validTypes.contains(board.getPiece(square).getPieceType()) )
                .filter(square -> validSides.contains(board.getPiece(square).getPieceSide()) )
                .count();
    }
}
