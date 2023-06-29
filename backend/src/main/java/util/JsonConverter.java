package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {

    static final private Gson prettyGson;
    static final private Gson compactGson;

    static {
        GsonBuilder builder = new GsonBuilder();
        prettyGson = builder.setPrettyPrinting().create();
        compactGson = new Gson();
    }

    public static String toJson(Object object) {
        return compactGson.toJson(object);
    }
    public static String toPrettyJson(Object object) {
        return prettyGson.toJson(object);
    }
    public static <T> T fromJson(String json, Class<T> type) {
        return prettyGson.fromJson(json, type);
    }
}
