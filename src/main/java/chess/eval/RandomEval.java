package chess.eval;

import chess.board.LBoard;

import java.util.Random;

public class RandomEval implements ChessEval {

    final private Random random;


    public RandomEval(Random random) {
        this.random = random;
    }

    @Override
    public double utility(LBoard board) {
        return random.nextDouble() * 0.1;
    }
}
