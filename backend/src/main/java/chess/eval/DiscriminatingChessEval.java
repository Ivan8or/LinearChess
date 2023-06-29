package chess.eval;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;

import java.util.List;

public abstract class DiscriminatingChessEval implements ChessEval {

    final public LPieceType[] validTypes;
    final public LSide[] validSides;

    public DiscriminatingChessEval(LPieceType[] validTypes, LSide[] validSides) {
        this.validTypes = validTypes;
        this.validSides = validSides;
    }
    public DiscriminatingChessEval(LPieceType validType, LSide[] validSides) {
        this.validTypes = new LPieceType[] {validType};
        this.validSides = validSides;
    }
    public DiscriminatingChessEval(LPieceType[] validTypes, LSide validSide) {
        this.validTypes = validTypes;
        this.validSides = new LSide[] {validSide};
    }
    public DiscriminatingChessEval(LPieceType validType, LSide validSide) {
        this.validTypes = new LPieceType[] {validType};
        this.validSides = new LSide[] {validSide};
    }
}
