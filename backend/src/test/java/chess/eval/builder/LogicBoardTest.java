package chess.eval.builder;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;
import chess.eval.builder.JsonParser;
import chess.eval.builder.LogicBoard;
import chess.eval.impl.*;
import com.github.bhlangonijr.chesslib.game.Game;
import org.junit.Assert;
import org.junit.Test;
import util.ResourceAsString;

import java.util.List;
import java.util.Map;

public class LogicBoardTest {


    @Test
    public void parseGeneratedBoard1() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/complex/client-generated-1.json").get();
        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        CumulativeEval eval = (CumulativeEval) lb.parseBoard(jsonObject);

        Assert.assertNotNull(eval);
        Assert.assertEquals(eval.evaluations.length, 1);
    }

    @Test
    public void parseBasicBoard() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/complex/basic.json").get();
        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        CumulativeEval eval = (CumulativeEval) lb.parseBoard(jsonObject);

        Assert.assertNotNull(eval);
        Assert.assertEquals(eval.evaluations.length, 2);
    }

    @Test
    public void parseDistanceAcrossEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/individual/distance-across/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        DistanceAcrossEval eval = (DistanceAcrossEval) lb.parseDistanceAcrossEval(context);

        Assert.assertEquals(eval.validSides.length, 1);
        Assert.assertEquals(eval.validTypes.length, 1);

        Assert.assertTrue(List.of(eval.validTypes).contains(LPieceType.PAWN));
        Assert.assertTrue(List.of(eval.validSides).contains(LSide.WHITE));
    }

    @Test
    public void parseDistanceToMidEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/individual/distance-to-mid/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        DistanceToMidEval eval = (DistanceToMidEval) lb.parseDistanceToMidEval(context);

        Assert.assertEquals(eval.validSides.length, 1);
        Assert.assertEquals(eval.validTypes.length, 1);

        Assert.assertTrue(List.of(eval.validTypes).contains(LPieceType.KNIGHT));
        Assert.assertTrue(List.of(eval.validSides).contains(LSide.WHITE));
    }

    @Test
    public void parseGameMatedEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/individual/game-mated/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        GameEndsEval eval = (GameEndsEval) lb.parseGameMatedEval(context);
        Assert.assertEquals(eval.mySide, LSide.BLACK);
    }

    @Test
    public void parseNumPiecesEval() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/individual/num-pieces/simple.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        Map<String, Object> context = lb.extractContext(jsonObject);

        NumPiecesEval eval = (NumPiecesEval) lb.parseNumPiecesEval(context);

        Assert.assertEquals(eval.validTypes.length, 2);
        Assert.assertEquals(eval.validSides.length, 1);

        Assert.assertTrue(List.of(eval.validTypes).contains(LPieceType.PAWN));
        Assert.assertTrue(List.of(eval.validTypes).contains(LPieceType.ROOK));
        Assert.assertTrue(List.of(eval.validSides).contains(LSide.WHITE));
    }

    @Test
    public void parseMultiplierDouble() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/individual/multiplier/double.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        float multiplier = lb.parseMultiplier(jsonObject);

        Assert.assertEquals(multiplier, 2, 0.00001);
    }

    @Test
    public void parseMultiplierTripleInverted() {
        LogicBoard lb = new LogicBoard();
        String jsonText = ResourceAsString.at("json/individual/multiplier/triple-inverted.json").get();

        Map<String, Object> jsonObject = JsonParser.parseJsonObject(jsonText);
        float multiplier = lb.parseMultiplier(jsonObject);

        Assert.assertEquals(multiplier, -3, 0.00001);
    }
}
