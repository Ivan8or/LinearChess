package model.mappings;

import model.mappings.Endpoint;
import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

public class EndpointTest {

    static final String RESOURCE_PATH = "model/mappings/endpoint/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        Endpoint generated = JsonConverter.fromJson(from, Endpoint.class);

        Endpoint expected = new Endpoint("", new String[]{});
        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeEmpty() {
        Endpoint from = new Endpoint("", new String[]{});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        Endpoint generated = JsonConverter.fromJson(from, Endpoint.class);

        Endpoint expected = new Endpoint("/api/v1", new String[]{"GET"});

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeSimple() {
        Endpoint from = new Endpoint("/api/v1", new String[]{"GET"});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLong() {
        String from = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        Endpoint generated = JsonConverter.fromJson(from, Endpoint.class);

        Endpoint expected = new Endpoint(
                "THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",
                new String[]{"GET","POST","PUT","DELETE"});

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeLong() {
        Endpoint from = new Endpoint(
                "THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",
                new String[]{"GET","POST","PUT","DELETE"});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
