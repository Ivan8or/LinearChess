package model.mappings;

import org.junit.Assert;
import org.junit.Test;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.Optional;

public class InventoryTest {

    static final String RESOURCE_PATH = "model/mappings/inventory/";

    @Test
    public void readEmpty() {
        String from = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        Inventory generated = JsonConverter.fromJson(from, Inventory.class);

        Inventory expected = new Inventory();
        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeEmpty() {
        Inventory from = new Inventory();
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"empty.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readSimple() {
        String from = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        Inventory generated = JsonConverter.fromJson(from, Inventory.class);

        Inventory expected = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(201, new Item("multiplier", 4001)));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeSimple() {
        Inventory from = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(201, new Item("multiplier", 4001)));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void readLong() {
        String from = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        Inventory generated = JsonConverter.fromJson(from, Inventory.class);

        Inventory expected = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(201, new Item("multiplier", 40010)),
                new SlottedItem(202, new Item("multiplier", 40011)),
                new SlottedItem(203, new Item("multiplier", 40012)),
                new SlottedItem(204, new Item("multiplier", 40013)));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void writeLong() {
        Inventory from = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(201, new Item("multiplier", 40010)),
                new SlottedItem(202, new Item("multiplier", 40011)),
                new SlottedItem(203, new Item("multiplier", 40012)),
                new SlottedItem(204, new Item("multiplier", 40013)));
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"long.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void accessSlot() {
        Inventory from = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(201, new Item("multiplier", 40010)),
                new SlottedItem(202, new Item("multiplier", 40011)),
                new SlottedItem(203, new Item("multiplier", 40012)),
                new SlottedItem(204, new Item("multiplier", 40013)));
        Optional<SlottedItem> generated = from.getSlot(201);

        Optional<SlottedItem> expected = Optional.of(new SlottedItem(201, new Item("multiplier", 40010)));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void misAccessSlot() {
        Inventory from = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(201, new Item("multiplier", 40010)),
                new SlottedItem(202, new Item("multiplier", 40011)),
                new SlottedItem(203, new Item("multiplier", 40012)),
                new SlottedItem(204, new Item("multiplier", 40013)));
        Optional<SlottedItem> generated = from.getSlot(201);

        Optional<SlottedItem> unexpected = Optional.of(new SlottedItem(202, new Item("multiplier", 40011)));

        Assert.assertNotEquals(unexpected, generated);
    }

    @Test
    public void addItem() {
        Inventory from = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(204, new Item("multiplier", 40013)));
        Inventory generated = from.addItem(new SlottedItem(203, new Item("multiplier", 40012)));

        Inventory expected = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(204, new Item("multiplier", 40013)),
                new SlottedItem(203, new Item("multiplier", 40012)));

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void removeItem() {
        Inventory from = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(204, new Item("multiplier", 40013)),
                new SlottedItem(203, new Item("multiplier", 40012)));
        Inventory generated = from.removeItem(204);

        Inventory expected = new Inventory(
                new SlottedItem(101, new Item("eval", 3001)),
                new SlottedItem(203, new Item("multiplier", 40012)));

        Assert.assertEquals(expected, generated);
    }
}
