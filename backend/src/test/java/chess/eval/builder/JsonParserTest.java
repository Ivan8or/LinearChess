package chess.eval.builder;

import chess.eval.builder.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class JsonParserTest {

    @Test
    public void parseSimpleObject() {
        String object = "{\"cats\": \"seven\"}";
        Map<String, Object> result = JsonParser.parseJsonObject(object);
        Assert.assertEquals(result.size(), 1);
        Assert.assertNotNull(result.get("cats"));
        Assert.assertEquals(result.get("cats"), "seven");
    }
}
