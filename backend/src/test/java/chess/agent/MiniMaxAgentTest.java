package chess.agent;

import chess.agent.impl.AlphaBetaAgent;
import chess.agent.impl.MiniMaxAgent;
import chess.eval.ChessEval;
import chess.eval.impl.ConstantEval;
import org.junit.Assert;
import org.junit.Test;

public class MiniMaxAgentTest {

    @Test
    public void nullEval() {
        try {
            new MiniMaxAgent(null, 3);
        }catch(Exception e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void depth0AndUp() {
        try {
            ChessEval eval = new ConstantEval(1);
            new MiniMaxAgent(eval, 0);
            new MiniMaxAgent(eval, 3);
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
            new MiniMaxAgent(eval, -1);
        }catch(IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
