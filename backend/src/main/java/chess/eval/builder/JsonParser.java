package chess.eval.builder;

import com.google.gson.Gson;
import java.util.Map;

public class JsonParser {

    public static Map<String, Object> parseJsonObject(String jsonText) {
        Gson gson = new Gson();
        Map<String, Object> deserialized = gson.fromJson(jsonText, Map.class);
        return deserialized;
    }
}
