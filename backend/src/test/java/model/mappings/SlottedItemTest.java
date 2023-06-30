package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

public class SlottedItemTest {

    static final String RESOURCE_PATH = "model/mappings/slottedItem/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        SlottedItem generated = JsonConverter.fromJson(from, SlottedItem.class);

        SlottedItem expected = new SlottedItem(0, new Item("", 0));
        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeEmpty() {
        SlottedItem from = new SlottedItem(0, new Item("", 0));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        SlottedItem generated = JsonConverter.fromJson(from, SlottedItem.class);

        SlottedItem expected = new SlottedItem(101, new Item("eval", 3001));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeSimple() {
        SlottedItem from = new SlottedItem(101, new Item("eval", 3001));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLong() {
        String from = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        SlottedItem generated = JsonConverter.fromJson(from, SlottedItem.class);

        SlottedItem expected = new SlottedItem(10003000, new Item("THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",30001000));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeLong() {
        SlottedItem from = new SlottedItem(10003000, new Item("THIS_IS_AN_EXTREMELY_LONG_STRING_AAAAAAAAAAAAAAAAAAAAA",30001000));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
