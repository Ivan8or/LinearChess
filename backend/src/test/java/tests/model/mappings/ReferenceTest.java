package tests.model.mappings;

import com.google.gson.Gson;
import model.mappings.Endpoint;
import model.mappings.Reference;
import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

public class ReferenceTest {

    @Test
    public void readEmptyReference() {
        String from = ResourceAsString.at("model/mappings/reference/empty.json").get();
        Reference generated = JsonConverter.fromJson(from, Reference.class);

        Reference expectedReference = new Reference(new Endpoint[]{});

        Assert.assertEquals(expectedReference, generated);
    }

    @Test
    public void writeEmptyReference() {
        Reference from = new Reference(new Endpoint[]{});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at("model/mappings/reference/empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimpleReference() {
        String from = ResourceAsString.at("model/mappings/reference/simple.json").get();
        Reference generated = JsonConverter.fromJson(from, Reference.class);

        Reference expected = new Reference(new Endpoint[]{ new Endpoint("/api/v1", new String[]{"GET"} )});

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeSimpleReference() {
        Reference from = new Reference(new Endpoint[]{new Endpoint("/api/v1", new String[]{"GET"})});
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at("model/mappings/reference/simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLongReference() {
        String from = ResourceAsString.at("model/mappings/reference/long.json").get();
        Reference generated = JsonConverter.fromJson(from, Reference.class);

        Reference expected = new Reference(new Endpoint[]{
                new Endpoint("/api/v1", new String[]{"GET"} ),
                new Endpoint("/api/v2", new String[]{"GET", "APPLES"} ),
                new Endpoint("/api/v3/apples/:orange", new String[]{"GET","POST","PUT","DELETE"} ),
                new Endpoint("/api/v1", new String[]{"GET"} ),
                new Endpoint("/api/v1", new String[]{"GET"} ),
        });

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeLongReference() {
        Reference from = new Reference(new Endpoint[]{
                new Endpoint("/api/v1", new String[]{"GET"} ),
                new Endpoint("/api/v2", new String[]{"GET", "APPLES"} ),
                new Endpoint("/api/v3/apples/:orange", new String[]{"GET","POST","PUT","DELETE"} ),
                new Endpoint("/api/v1", new String[]{"GET"} ),
                new Endpoint("/api/v1", new String[]{"GET"} ),
        });
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at("model/mappings/reference/long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
