package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Optional;

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

    public static <T> Optional<T> fromJson(String json, Class<T> type) {
        T object = prettyGson.fromJson(json, type);
        return (object == null) ? Optional.empty() : Optional.of(object);
    }

    public static String minimize(String json) {
        return json.replaceAll("\\s", "");
    }
}
