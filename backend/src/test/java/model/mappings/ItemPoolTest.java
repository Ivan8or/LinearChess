package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

public class ItemPoolTest {

    static final String RESOURCE_PATH = "model/mappings/itemPool/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        ItemPool generated = JsonConverter.fromJson(from, ItemPool.class);

        ItemPool expected = new ItemPool(new Item("", 0),0);

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        ItemPool generated = JsonConverter.fromJson(from, ItemPool.class);

        ItemPool expected = new ItemPool(new Item("eval", 4001),3);

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readArray() {
        String from = ResourceAsString.at(RESOURCE_PATH+"array.json").get();
        ItemPool[] generated = JsonConverter.fromJson(from, ItemPool[].class);

        ItemPool[] expected = new ItemPool[] {
                new ItemPool(new Item("eval", 4001),12),
                new ItemPool(new Item("eval", 4003),12),
                new ItemPool(new Item("multiplier", 3001),8),
                new ItemPool(new Item("multiplier", 3002),8)
        };

        Assert.assertArrayEquals(expected, generated);
    }
}
