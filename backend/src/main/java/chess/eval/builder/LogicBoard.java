package chess.eval.builder;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;
import chess.eval.impl.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicBoard {

    public static ChessEval parseBoard(Map<String, Object> logicboard) {
        if(!logicboard.get("object").equals("logicboard"))
            throw new IllegalStateException("OBJECT IS NOT OF TYPE LOGICBOARD: "+logicboard);

        List<Map<String, Object>> activeEvals = (List<Map<String, Object>>) logicboard.getOrDefault("active-evals", List.of());
        List<Map<String, Object>> activeMultipliers = (List<Map<String, Object>>) logicboard.getOrDefault("active-multipliers", List.of());

        Map<Integer, ChessEval> chessEvals = new HashMap<>(activeEvals.size());

        for(Map<String, Object> evalObject : activeEvals) {
            ChessEval parsedEval = parseEval(evalObject);
            int evalSlot = (int) (double) evalObject.get("slot");

            chessEvals.put(evalSlot, parsedEval);
        }

        for(Map<String, Object> multiplierObject : activeMultipliers) {
            float parsedMultiplier = parseMultiplier(multiplierObject);
            int multiplierSlot = (int) (double) multiplierObject.get("slot");

            ChessEval originalEval = chessEvals.get(multiplierSlot);
            if(!chessEvals.containsKey(multiplierSlot)) {
                continue;
            }

            ChessEval multipliedEval = new WeightedEval(originalEval, parsedMultiplier);
            chessEvals.put(multiplierSlot, multipliedEval);
        }

        ChessEval cumulativeEval = new CumulativeEval(chessEvals.values().toArray(new ChessEval[0]));
        return cumulativeEval;
    }

    public static float parseMultiplier(Map<String, Object> multiplier) {
        String type = (String) multiplier.getOrDefault("type", "NONEXISTENT");

        switch(type) {
            case "SINGLE": return 1;
            case "DOUBLE": return 2;
            case "TRIPLE": return 3;
            case "QUADRUPLE": return 4;

            case "SINGLE_INVERTED": return -1;
            case "DOUBLE_INVERTED": return -2;
            case "TRIPLE_INVERTED": return -3;
            case "QUADRUPLE_INVERTED": return -4;
        }
        throw new IllegalStateException("INVALID MULTIPLIER TYPE "+type);
    }

    public static ChessEval parseEval(Map<String, Object> eval) {
        String type = (String) eval.getOrDefault("type", "NONEXISTENT");
        Map<String,Object> context = extractContext(eval);

        switch(type) {
            case "GAME_DRAWS": return new GameDrawsEval();
            case "GAME_MATED": return parseGameMatedEval(context);
            case "NUM_PIECES": return parseNumPiecesEval(context);
            case "DISTANCE_TO_MID": return parseDistanceToMidEval(context);
            case "DISTANCE_ACROSS": return parseDistanceAcrossEval(context);
        }
        throw new IllegalStateException("INVALID EVAL OF TYPE "+type);
    }

    public static ChessEval parseDistanceAcrossEval(Map<String, Object> context) {
        LPieceType[] types = ((List<Object>) context.get("discriminate-types"))
                .stream()
                .map(String.class::cast)
                .map(LPieceType::valueOf)
                .toArray(LPieceType[]::new);

        LSide[] sides = ((List<Object>) context.get("discriminate-sides"))
                .stream()
                .map(String.class::cast)
                .map(LSide::valueOf)
                .toArray(LSide[]::new);

        return new DistanceAcrossEval(types, sides);
    }

    public static ChessEval parseDistanceToMidEval(Map<String, Object> context) {
        LPieceType[] types = ((List<Object>) context.get("discriminate-types"))
                .stream()
                .map(String.class::cast)
                .map(LPieceType::valueOf)
                .toArray(LPieceType[]::new);

        LSide[] sides = ((List<Object>) context.get("discriminate-sides"))
                .stream()
                .map(String.class::cast)
                .map(LSide::valueOf)
                .toArray(LSide[]::new);

        return new DistanceToMidEval(types, sides);
    }

    public static ChessEval parseGameMatedEval(Map<String, Object> context) {
        return new GameEndsEval(LSide.valueOf((String) context.get("side")));
    }
    public static ChessEval parseNumPiecesEval(Map<String, Object> context) {

        LPieceType[] types = ((List<Object>) context.get("discriminate-types"))
                .stream()
                .map(String.class::cast)
                .map(LPieceType::valueOf)
                .toArray(LPieceType[]::new);

        LSide[] sides = ((List<Object>) context.get("discriminate-sides"))
                .stream()
                .map(String.class::cast)
                .map(LSide::valueOf)
                .toArray(LSide[]::new);

        return new NumPiecesEval(types, sides);
    }

    public static Map<String,Object> extractContext(Map<String,Object> jsonObject) {
        return (Map<String, Object>) jsonObject.getOrDefault("context", Map.of());
    }
}
