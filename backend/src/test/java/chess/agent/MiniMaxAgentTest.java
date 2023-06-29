package chess.agent;

import chess.ChessGame;
import chess.agent.impl.AlphaBetaAgent;
import chess.agent.ChessAgent;
import chess.agent.impl.MiniMaxAgent;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.*;
import chess.eval.impl.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

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
