package chess.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.board.LSquare;

import java.util.Arrays;

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
        return distanceFromStartSide(board);
    }

    private int distanceFromStartSide(LBoard board) {

        return Arrays.stream(LSquare.values())
                .filter(square -> square != LSquare.NONE)
                .filter(square -> board.getPiece(square).exists())
                .filter(square -> validTypes.contains(board.getPiece(square).getPieceType()) )
                .filter(square -> validSides.contains(board.getPiece(square).getPieceSide()) )
                .mapToInt(square -> {
                    double col = Math.abs((square.toString().charAt(0) - 'D') - 0.5);
                    double row = Math.abs((square.toString().charAt(1) - '4') - 0.5);
                    return Math.max((int)col, (int)row);
                })
                .sum();
    }

}
