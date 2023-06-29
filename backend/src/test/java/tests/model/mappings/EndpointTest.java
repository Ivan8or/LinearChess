package tests.model.mappings;

import com.google.gson.Gson;
import model.mappings.Endpoint;
import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

public class EndpointTest {

    @Test
    public void readEmptyEndpoint() {
        String from = ResourceAsString.at("model/mappings/endpoint/empty.json").get();
        Endpoint generated = JsonConverter.fromJson(from, Endpoint.class);

        Endpoint expected = new Endpoint("", new String[]{});
        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeEmptyEndpoint() {
        Endpoint from = new Endpoint("", new String[]{});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at("model/mappings/endpoint/empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimpleEndpoint() {
        String from = ResourceAsString.at("model/mappings/endpoint/simple.json").get();
        Endpoint generated = JsonConverter.fromJson(from, Endpoint.class);

        Assert.assertEquals("/api/v1", generated.getEndpoint());
        Assert.assertArrayEquals(new String[]{"GET"} , generated.getMethods());
    }

    @Test
    public void writeSimpleEndpoint() {
        Endpoint from = new Endpoint("/api/v1", new String[]{"GET"});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at("model/mappings/endpoint/simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLongEndpoint() {
        String from = ResourceAsString.at("model/mappings/endpoint/long.json").get();
        Endpoint generated = JsonConverter.fromJson(from, Endpoint.class);

        Endpoint expected = new Endpoint(
                "THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",
                new String[]{"GET","POST","PUT","DELETE"});

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeLongEndpoint() {
        Endpoint from = new Endpoint(
                "THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",
                new String[]{"GET","POST","PUT","DELETE"});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at("model/mappings/endpoint/long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
