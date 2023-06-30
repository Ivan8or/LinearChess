package model.shop;

import model.mappings.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class LobbyShopTest {

    @Mock
    RandomGenerator random;

    @Test
    public void borrowItems() {
        lenient().when(random.nextInt(40)).thenReturn(13);
        lenient().when(random.nextInt(39)).thenReturn(7);
        lenient().when(random.nextInt(38)).thenReturn(22);

        ItemPool[] poolTemplates = new ItemPool[] {
                new ItemPool(new Item("eval", 4001),12),
                new ItemPool(new Item("eval", 4003),12),
                new ItemPool(new Item("multiplier", 3001),8),
                new ItemPool(new Item("multiplier", 3002),8)};
        List<Item> itemPool = Arrays.stream(poolTemplates)
                .flatMap(pool -> Arrays.stream(pool.expand()))
                .toList();
        LobbyShop shop = new LobbyShop(itemPool, random);
        Inventory generated = shop.borrowItems(3);

        Inventory expected = new Inventory(
                new SlottedItem(0, new Item("eval", 4003)),
                new SlottedItem(1, new Item("eval", 4001)),
                new SlottedItem(2, new Item("multiplier", 3001)));

        Assert.assertEquals(expected, generated);
        Assert.assertEquals(37, shop.itemPool().size());
    }

    @Test
    public void returnItems() {
        ItemPool[] poolTemplates = new ItemPool[] {};
        LobbyShop shop = new LobbyShop(new ArrayList<>());
        Inventory from = new Inventory(
                new SlottedItem(0, new Item("eval", 4003)),
                new SlottedItem(1, new Item("eval", 4001)),
                new SlottedItem(2, new Item("multiplier", 3001)));

        List<Item> expected = List.of(
                new Item("eval", 4003),
                new Item("eval", 4001),
                new Item("multiplier", 3001));

        shop.returnItems(from);

        Assert.assertEquals(expected, shop.itemPool());
    }
}
