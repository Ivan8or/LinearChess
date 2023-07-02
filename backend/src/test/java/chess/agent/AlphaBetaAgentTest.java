package chess.agent;

import chess.agent.impl.AlphaBetaAgent;
import chess.eval.ChessEval;
import chess.eval.impl.ConstantEval;
import org.junit.Assert;
import org.junit.Test;

public class AlphaBetaAgentTest {


    @Test
    public void nullEval() {
        try {
            new AlphaBetaAgent(null, 3);
        }catch(Exception e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void depth0AndUp() {
        try {
            ChessEval eval = new ConstantEval(1);
            new AlphaBetaAgent(eval, 0);
            new AlphaBetaAgent(eval, 3);
            new AlphaBetaAgent(eval, 30);
        }catch(Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void depthNegative() {
        try {
            ChessEval eval = new ConstantEval(1);
            AlphaBetaAgent a = new AlphaBetaAgent(eval, -1);
        }catch(IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
