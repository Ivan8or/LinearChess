package model.shop;

import model.mappings.Inventory;
import model.mappings.Item;
import model.mappings.SlottedItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.Optional;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class ShopViewTest {

    static final String RESOURCE_PATH = "model/shop/shopView/";

    @Mock
    ItemShop shop;

    @Test
    public void sellItem() {
        lenient().when(shop.startingGold()).thenReturn(4);
        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(new SlottedItem(501, new Item("eval", 3001))));
        ShopView from = new ShopView(shop);
        SlottedItem toSell = new SlottedItem(202, new Item("multiplier", 4004));

        from.sell(toSell);

        Assert.assertEquals(5, from.getGold());
    }

    @Test
    public void buyItem() {
        lenient().when(shop.startingGold()).thenReturn(4);
        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(
                        new SlottedItem(501, new Item("eval", 3001)),
                        new SlottedItem(602, new Item("multiplier", 4001)),
                        new SlottedItem(603, new Item("multiplier", 4004))));
        ShopView from = new ShopView(shop);

        Assert.assertTrue(from.canAffordBuy(602));

        Optional<SlottedItem> purchased = from.buy(602);

        Assert.assertTrue(purchased.isPresent());
        Assert.assertEquals(new SlottedItem(602, new Item("multiplier", 4001)), purchased.get());
        Assert.assertEquals(1, from.getGold());
        Assert.assertEquals(
                new Inventory(
                        new SlottedItem(501, new Item("eval", 3001)),
                        new SlottedItem(603, new Item("multiplier", 4004))),
                from.getWares());
        Assert.assertFalse(from.canAffordBuy(501));

        purchased = from.buy(602);

        Assert.assertFalse(purchased.isPresent());
        Assert.assertEquals(1, from.getGold());
        Assert.assertEquals(
                new Inventory(
                        new SlottedItem(501, new Item("eval", 3001)),
                        new SlottedItem(603, new Item("multiplier", 4004))),
                from.getWares());
    }

    @Test
    public void waresPresent() {
        lenient().when(shop.startingGold()).thenReturn(4);
        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(
                        new SlottedItem(501, new Item("eval", 3001)),
                        new SlottedItem(602, new Item("multiplier", 4001)),
                        new SlottedItem(603, new Item("multiplier", 4004))));

        ShopView from = new ShopView(shop);

        Assert.assertTrue(from.warePresent(501));
        Assert.assertTrue(from.warePresent(501));
        Assert.assertTrue(from.warePresent(602));
        Assert.assertTrue(from.warePresent(603));

        Assert.assertFalse(from.warePresent(604));
        Assert.assertFalse(from.warePresent(504));
        Assert.assertFalse(from.warePresent(-1));
    }

    @Test
    public void refreshShop() {
        lenient().when(shop.startingGold()).thenReturn(4);
        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(new SlottedItem(501, new Item("eval", 3001))));

        ShopView from = new ShopView(shop);
        from.buy(501);
        Assert.assertEquals(1, from.getGold());

        from.refreshShopStatus();

        Assert.assertEquals(4, from.getGold());
        Assert.assertArrayEquals(
                new SlottedItem[] { new SlottedItem(501, new Item("eval", 3001)) },
                from.getWares().items());
    }

    @Test
    public void restockItems() {
        lenient().when(shop.startingGold()).thenReturn(4);
        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(new SlottedItem(501, new Item("eval", 3001))));

        ShopView from = new ShopView(shop);

        Assert.assertArrayEquals(
                new SlottedItem[] { new SlottedItem(501, new Item("eval", 3001)) },
                from.getWares().items());


        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(new SlottedItem(601, new Item("multiplier", 4001))));

        boolean refreshStatus = from.restockWares();

        Assert.assertTrue(refreshStatus);
        Assert.assertArrayEquals(
                new SlottedItem[] { new SlottedItem(601, new Item("multiplier", 4001)) },
                from.getWares().items());
        Assert.assertEquals(3, from.getGold());

        from.restockWares();
        from.restockWares();
        from.restockWares();

        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(new SlottedItem(502, new Item("eval", 3009))));

        refreshStatus = from.restockWares();

        Assert.assertFalse(refreshStatus);
        Assert.assertArrayEquals(
                new SlottedItem[] { new SlottedItem(601, new Item("multiplier", 4001)) },
                from.getWares().items());
        Assert.assertEquals(0, from.getGold());
    }

    @Test
    public void writeSimple() {
        lenient().when(shop.startingGold()).thenReturn(4);
        lenient().when(shop.borrowItems()).thenReturn(
                new Inventory(
                        new SlottedItem(501, new Item("eval", 3001)),
                        new SlottedItem(601, new Item("multiplier", 4001)))
        );
        ShopView from = new ShopView(shop);
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
