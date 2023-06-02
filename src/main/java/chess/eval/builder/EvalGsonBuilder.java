package chess.eval.builder;

import chess.eval.ChessEval;
import com.google.gson.Gson;

import java.util.Map;

public class EvalGsonBuilder implements EvalBuilder {

    @Override
    public ChessEval fromString(String jsonEval) {
        Gson gson = new Gson();
        Map<String, Object> deserialized = gson.fromJson(jsonEval, Map.class);
        return null;
    }

    public ChessEval parseEval(Map<String, Object> body) {
        if(!body.get("object").equals("eval"))
            throw new IllegalArgumentException("Can't parse non-chess eval object as chess eval.");

        String type = (String) body.getOrDefault("type", "DOES_NOT_EXIST");


    }

    @Override
    public String toString(ChessEval eval) {
        return null;
    }
}
