package tests.eval;

import chess.board.LBoard;
import chess.eval.impl.ConstantEval;
import org.junit.Assert;
import org.junit.Test;

public class ConstantEvalTest {


    @Test
    public void testUtilityForDoubles() {

        LBoard board = new LBoard();

        for(int i = 0; i < 100; i++) {

            double randomValue = ((int)((Math.random() / Math.random()) * 1000))/1000.0;
            ConstantEval constantEval = new ConstantEval(randomValue);
            Assert.assertEquals(randomValue, constantEval.utility(board), 0.0);

        }
    }
}
