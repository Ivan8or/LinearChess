package chess.eval.impl;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.board.LSquare;
import chess.eval.DiscriminatingChessEval;

import java.util.Arrays;
import java.util.List;

public class NumPiecesEval extends DiscriminatingChessEval {

    public NumPiecesEval(LPieceType[] validTypes, LSide[] validSides) {
        super(validTypes, validSides);
    }
    public NumPiecesEval(LPieceType validTypes, LSide[] validSides) {
        super(validTypes, validSides);
    }
    public NumPiecesEval(LPieceType[] validTypes, LSide validSides) {
        super(validTypes, validSides);
    }
    public NumPiecesEval(LPieceType validTypes, LSide validSides) {
        super(validTypes, validSides);
    }
    @Override
    public double utility(LBoard board) {
        return piecesOnBoard(board);
    }

    private int piecesOnBoard(LBoard board) {

        return (int) Arrays.stream(LSquare.values())
                .filter(square -> square != LSquare.NONE)
                .filter(square -> board.getPiece(square).exists())
                .filter(square -> List.of(validTypes).contains(board.getPiece(square).getPieceType()) )
                .filter(square -> List.of(validSides).contains(board.getPiece(square).getPieceSide()) )
                .count();
    }
}
