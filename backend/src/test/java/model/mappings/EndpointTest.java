package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.Optional;

public class EndpointTest {

    static final String RESOURCE_PATH = "model/mappings/endpoint/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        Optional<Endpoint> generated = JsonConverter.fromJson(from, Endpoint.class);
        Assert.assertTrue(generated.isPresent());

        Endpoint expected = new Endpoint("");
        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeEmpty() {
        Endpoint from = new Endpoint("");
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        Optional<Endpoint> generated = JsonConverter.fromJson(from, Endpoint.class);
        Assert.assertTrue(generated.isPresent());

        Endpoint expected = new Endpoint("/api/v1", "GET");

        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeSimple() {
        Endpoint from = new Endpoint("/api/v1", "GET");
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLong() {
        String from = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        Optional<Endpoint> generated = JsonConverter.fromJson(from, Endpoint.class);
        Assert.assertTrue(generated.isPresent());

        Endpoint expected = new Endpoint(
                "THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",
                "GET","POST","PUT","DELETE");

        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeLong() {
        Endpoint from = new Endpoint(
                "THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",
                "GET","POST","PUT","DELETE");
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
