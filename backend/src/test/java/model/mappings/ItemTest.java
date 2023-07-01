package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.Optional;

public class ItemTest {

    static final String RESOURCE_PATH = "model/mappings/item/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        Optional<Item> generated = JsonConverter.fromJson(from, Item.class);
        Assert.assertTrue(generated.isPresent());

        Item expected = new Item("", 0);
        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeEmpty() {
        Item from = new Item("", 0);
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        Optional<Item> generated = JsonConverter.fromJson(from, Item.class);
        Assert.assertTrue(generated.isPresent());

        Item expected = new Item("eval", 4001);

        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeSimple() {
        Item from = new Item("eval", 4001);
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLong() {
        String from = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        Optional<Item> generated = JsonConverter.fromJson(from, Item.class);
        Assert.assertTrue(generated.isPresent());

        Item expected = new Item("THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",10002000);

        Assert.assertEquals(expected, generated.get());
    }

    @Test
    public void writeLong() {
        Item from = new Item("THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",10002000);
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
