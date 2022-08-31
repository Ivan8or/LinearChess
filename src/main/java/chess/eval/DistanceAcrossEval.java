package chess.eval;

import chess.board.LBoard;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.board.LSquare;

import java.util.Arrays;
import java.util.List;

public class DistanceAcrossEval implements ChessEval {

    final private List<LPieceType> validTypes;
    final private List<LSide> validSides;

    public DistanceAcrossEval(LPieceType[] validTypes, LSide[] validSides) {
        this.validTypes = List.of(validTypes);
        this.validSides = List.of(validSides);
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
                    int row = square.toString().charAt(1) - '1';
                    if(board.getPiece(square).getPieceSide() == LSide.BLACK)
                        return 7 - row;
                    return row;
                })
                .sum();
    }

}
