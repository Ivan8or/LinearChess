package chess.eval.builder;

import chess.eval.ChessEval;

public interface EvalBuilder {

    ChessEval fromString(String stringedEval);
    String toString(ChessEval eval);
}
