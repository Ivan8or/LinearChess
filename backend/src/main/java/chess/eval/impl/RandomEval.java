package chess.eval.impl;

import chess.board.LBoard;
import chess.eval.ChessEval;

import java.util.Random;

public class RandomEval implements ChessEval {

    final private Random random;


    public RandomEval(Random random) {
        this.random = random;
    }

    @Override
    public double utility(LBoard board) {
        return random.nextDouble();
    }
}
