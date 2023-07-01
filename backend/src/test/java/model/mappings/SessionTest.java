package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.Optional;
import java.util.UUID;

public class SessionTest {

    static final String RESOURCE_PATH = "model/mappings/session/";

    @Test
    public void readSession() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        Optional<Session> generated = JsonConverter.fromJson(from, Session.class);
        Assert.assertTrue(generated.isPresent());

        Session expected = new Session(UUID.fromString("7347bd1a-bc39-41db-b031-f2b306ac8c73"));

        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeSession() {
        Session from = new Session(UUID.fromString("7347bd1a-bc39-41db-b031-f2b306ac8c73"));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
