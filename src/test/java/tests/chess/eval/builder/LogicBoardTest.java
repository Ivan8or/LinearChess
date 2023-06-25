package tests.chess.eval.builder;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.builder.JsonParser;
import chess.eval.builder.LogicBoard;
import chess.eval.impl.NumPiecesEval;
import org.junit.Assert;
import org.junit.Test;
import util.ResourceAsString;

import java.util.Map;

public class LogicBoardTest {


    @Test
    public void parseNumPiecesEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/num-pieces/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = (Map<String, Object>) jsonObject.get("context");

        NumPiecesEval eval = (NumPiecesEval) lb.parseNumPiecesEval(context);

        Assert.assertEquals(eval.validTypes.size(), 2);
        Assert.assertEquals(eval.validSides.size(), 1);

        Assert.assertTrue(eval.validTypes.contains(LPieceType.PAWN));
        Assert.assertTrue(eval.validTypes.contains(LPieceType.ROOK));
        Assert.assertTrue(eval.validSides.contains(LSide.WHITE));
    }

    @Test
    public void parseMultiplierDouble() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/multiplier/double.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        float multiplier = lb.parseMultiplier(jsonObject);

        Assert.assertEquals(multiplier, 2, 0.00001);
    }
}
