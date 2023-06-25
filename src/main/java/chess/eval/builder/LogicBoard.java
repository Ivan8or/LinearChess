package chess.eval.builder;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;
import chess.eval.impl.*;

import java.util.List;
import java.util.Map;

public class LogicBoard {


    public ChessEval parseEval(Map<String, Object> eval) {
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

    public ChessEval parseDistanceAcrossEval(Map<String, Object> context) {
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

    public ChessEval parseDistanceToMidEval(Map<String, Object> context) {
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

    public ChessEval parseGameMatedEval(Map<String, Object> context) {
        return new GameEndsEval(LSide.valueOf((String) context.get("side")));
    }
    public ChessEval parseNumPiecesEval(Map<String, Object> context) {

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

    public float parseMultiplier(Map<String, Object> multiplier) {
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

    public Map<String,Object> extractContext(Map<String,Object> jsonObject) {
        return (Map<String, Object>) jsonObject.getOrDefault("context", Map.of());
    }
}
