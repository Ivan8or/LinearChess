package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

public class ReferenceTest {

    static final String RESOURCE_PATH = "model/mappings/reference/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        Reference generated = JsonConverter.fromJson(from, Reference.class);

        Reference expectedReference = new Reference();

        Assert.assertEquals(expectedReference, generated);
    }

    @Test
    public void writeEmpty() {
        Reference from = new Reference();
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        Reference generated = JsonConverter.fromJson(from, Reference.class);

        Reference expected = new Reference(new Endpoint("/api/v1", "GET"));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeSimple() {
        Reference from = new Reference(new Endpoint("/api/v1", "GET"));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLong() {
        String from = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        Reference generated = JsonConverter.fromJson(from, Reference.class);

        Reference expected = new Reference(
                new Endpoint("/api/v1", "GET"),
                new Endpoint("/api/v2", "GET", "APPLES"),
                new Endpoint("/api/v3/apples/:orange", "GET","POST","PUT","DELETE"),
                new Endpoint("/api/v1", "GET"),
                new Endpoint("/api/v1", "GET"));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeLong() {
        Reference from = new Reference(
                new Endpoint("/api/v1", "GET"),
                new Endpoint("/api/v2", "GET", "APPLES"),
                new Endpoint("/api/v3/apples/:orange", "GET", "POST", "PUT", "DELETE"),
                new Endpoint("/api/v1", "GET"),
                new Endpoint("/api/v1", "GET"));

        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
