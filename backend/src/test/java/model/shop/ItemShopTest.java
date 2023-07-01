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
import java.util.UUID;
import java.util.random.RandomGenerator;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class ItemShopTest {

    @Mock
    RandomGenerator random;

    @Test
    public void trackShopSession() {
        ItemPool[] poolTemplates = new ItemPool[] {
                new ItemPool(new Item("eval", 4001),12)};
        List<Item> itemPool = Arrays.stream(poolTemplates)
                .flatMap(pool -> Arrays.stream(pool.expand()))
                .toList();

        ItemShop shop = new ItemShop(itemPool, random);
        Session player1 = new Session(UUID.randomUUID());
        Session player2 = new Session(UUID.randomUUID());

        ShopView view1a = shop.playerView(player1);
        ShopView view1b = shop.playerView(player1);
        ShopView view2 = shop.playerView(player2);
        Assert.assertEquals(view1a, view1b);
        Assert.assertNotEquals(view1a, view2);

        shop.refreshShops();
        Assert.assertEquals(view1a, view1b);
    }

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
        ItemShop shop = new ItemShop(itemPool, random);
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
        ItemShop shop = new ItemShop(new ArrayList<>());
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
