package chess.eval.impl;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.board.LSquare;
import chess.eval.DiscriminatingChessEval;

import java.util.Arrays;
import java.util.List;

public class DistanceToMidEval extends DiscriminatingChessEval {

    public DistanceToMidEval(LPieceType[] validTypes, LSide[] validSides) {
        super(validTypes, validSides);
    }
    public DistanceToMidEval(LPieceType validTypes, LSide[] validSides) {
        super(validTypes, validSides);
    }
    public DistanceToMidEval(LPieceType[] validTypes, LSide validSides) {
        super(validTypes, validSides);
    }
    public DistanceToMidEval(LPieceType validTypes, LSide validSides) {
        super(validTypes, validSides);
    }

    @Override
    public double utility(LBoard board) {
        return distanceFromMiddle(board);
    }

    private double distanceFromMiddle(LBoard board) {

        return Arrays.stream(LSquare.values())
                .filter(square -> square != LSquare.NONE)
                .filter(square -> board.getPiece(square).exists())
                .filter(square -> List.of(validTypes).contains(board.getPiece(square).getPieceType()) )
                .filter(square -> List.of(validSides).contains(board.getPiece(square).getPieceSide()) )
                .mapToDouble(square -> {
                    double col = Math.abs(square.file - 3.5);
                    double row = Math.abs(square.rank - 3.5);
                    return Math.max(col, row);
                })
                .sum();
    }
}
