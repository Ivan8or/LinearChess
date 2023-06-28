package chess.eval;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;

import java.util.List;

public abstract class DiscriminatingChessEval implements ChessEval {

    final public List<LPieceType> validTypes;
    final public List<LSide> validSides;

    public DiscriminatingChessEval(LPieceType[] validTypes, LSide[] validSides) {
        this.validTypes = List.of(validTypes);
        this.validSides = List.of(validSides);
    }
    public DiscriminatingChessEval(LPieceType validType, LSide[] validSides) {
        this.validTypes = List.of(validType);
        this.validSides = List.of(validSides);
    }
    public DiscriminatingChessEval(LPieceType[] validTypes, LSide validSide) {
        this.validTypes = List.of(validTypes);
        this.validSides = List.of(validSide);
    }
    public DiscriminatingChessEval(LPieceType validType, LSide validSide) {
        this.validTypes = List.of(validType);
        this.validSides = List.of(validSide);
    }
}
