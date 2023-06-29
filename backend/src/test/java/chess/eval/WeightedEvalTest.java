package chess.eval;

import chess.board.LBoard;
import chess.eval.impl.ConstantEval;
import chess.eval.impl.WeightedEval;
import org.junit.Assert;
import org.junit.Test;

public class WeightedEvalTest {


    @Test
    public void valuesTest() {

        LBoard board = new LBoard();

        double[] values = {1, 5, -3, 7, 0, 1.34, -2.77, 5.5};
        double[] weights = {7, 2.2, 4, 0, 1.5, -3, -2.45, 1.0};

        for(int i = 0; i < values.length; i++) {
            double value = values[i];
            double weight = weights[i];

            ConstantEval constEval = new ConstantEval(values[i]);

            double expected = value * weight;

            WeightedEval weighted = new WeightedEval(constEval, weight);
            Assert.assertEquals(expected, weighted.utility(board), 0.0);
        }
    }
}
