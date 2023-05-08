package chess.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.board.LSquare;

import java.util.Arrays;

public class DistanceAcrossEval extends DiscriminatingChessEval {

    public DistanceAcrossEval(LPieceType[] validTypes, LSide[] validSides) {
        super(validTypes, validSides);
    }
    public DistanceAcrossEval(LPieceType validTypes, LSide[] validSides) {
        super(validTypes, validSides);
    }
    public DistanceAcrossEval(LPieceType[] validTypes, LSide validSides) {
        super(validTypes, validSides);
    }
    public DistanceAcrossEval(LPieceType validTypes, LSide validSides) {
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
                    int rank = square.rank;
                    if(board.getPiece(square).getPieceSide() == LSide.BLACK)
                        return 7 - rank;
                    return rank;
                })
                .sum();
    }

}
