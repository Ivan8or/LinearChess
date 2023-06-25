package tests.chess.eval.builder;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.builder.JsonParser;
import chess.eval.builder.LogicBoard;
import chess.eval.impl.DistanceAcrossEval;
import chess.eval.impl.DistanceToMidEval;
import chess.eval.impl.GameEndsEval;
import chess.eval.impl.NumPiecesEval;
import org.junit.Assert;
import org.junit.Test;
import util.ResourceAsString;

import java.util.Map;

public class LogicBoardTest {

    @Test
    public void parseDistanceAcrossEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/distance-across/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        DistanceAcrossEval eval = (DistanceAcrossEval) lb.parseDistanceAcrossEval(context);

        Assert.assertEquals(eval.validSides.size(), 1);
        Assert.assertEquals(eval.validTypes.size(), 1);

        Assert.assertTrue(eval.validTypes.contains(LPieceType.PAWN));
        Assert.assertTrue(eval.validSides.contains(LSide.WHITE));
    }

    @Test
    public void parseDistanceToMidEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/distance-to-mid/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        DistanceToMidEval eval = (DistanceToMidEval) lb.parseDistanceToMidEval(context);

        Assert.assertEquals(eval.validSides.size(), 1);
        Assert.assertEquals(eval.validTypes.size(), 1);

        Assert.assertTrue(eval.validTypes.contains(LPieceType.KNIGHT));
        Assert.assertTrue(eval.validSides.contains(LSide.WHITE));
    }

    @Test
    public void parseGameMatedEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/game-mated/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        GameEndsEval eval = (GameEndsEval) lb.parseGameMatedEval(context);
        Assert.assertEquals(eval.mySide, LSide.BLACK);
    }

    @Test
    public void parseNumPiecesEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/num-pieces/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

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
