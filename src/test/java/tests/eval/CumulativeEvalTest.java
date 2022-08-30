package tests.eval;

import chess.board.LBoard;
import chess.eval.ChessEval;
import chess.eval.ConstantEval;
import chess.eval.CumulativeEval;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CumulativeEvalTest {


    @Test
    public void sumConstantEvals() {
        LBoard board = new LBoard();

        for(int i = 0; i < 100; i++) {

            double expectedSum = 0;
            List<ConstantEval> constantEvals = new ArrayList<>();

            int NUM_EVALS = 10;
            for(int j = 0; j < NUM_EVALS; j++) {
                double randomValue = ((int) ((Math.random() / Math.random()) * 1000)) / 1000.0;
                expectedSum += randomValue;

                ConstantEval constantEval = new ConstantEval(randomValue);
                constantEvals.add(constantEval);
            }

            ConstantEval[] evalsArray = constantEvals.toArray(
                    value -> constantEvals.toArray(new ConstantEval[NUM_EVALS])
            );
            CumulativeEval cumulEval = new CumulativeEval(evalsArray);
            Assert.assertEquals(expectedSum, cumulEval.utility(board), 0.001);
        }
    }
}
