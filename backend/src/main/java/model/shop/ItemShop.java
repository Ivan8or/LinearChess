package model.shop;

import model.mappings.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.random.RandomGenerator;

public class ItemShop {

    final private List<Item> itemPool;
    final private RandomGenerator random;

    final private Map<Session, ShopView> shopViews;

    public ItemShop(List<Item> items) {
        itemPool = new ArrayList<>(items);
        random = new SplittableRandom();
        shopViews = new ConcurrentHashMap<>();
    }

    public ItemShop(ItemPool[] pools) {
        itemPool = new ArrayList<>(Arrays.stream(pools)
                .flatMap(pool -> Arrays.stream(pool.expand()))
                .toList());
        random = new SplittableRandom();
        shopViews = new ConcurrentHashMap<>();
    }

    public ItemShop(List<Item> items, RandomGenerator random) {
        itemPool = new ArrayList<>(items);
        this.random = random;
        shopViews = new ConcurrentHashMap<>();
    }

    public ItemShop(ItemPool[] pools, RandomGenerator random) {
        itemPool = new ArrayList<>(Arrays.stream(pools)
                .flatMap(pool -> Arrays.stream(pool.expand()))
                .toList());
        this.random = random;
        shopViews = new ConcurrentHashMap<>();
    }

    public List<Item> itemPool() {
        return itemPool;
    }

    public int startingGold() {
        return 4;
    }

    public ShopView playerView(Session player) {
        if(shopViews.containsKey(player))
            return shopViews.get(player);

        ShopView newSession = new ShopView(this);
        shopViews.put(player, newSession);
        return newSession;
    }

    public void refreshShops() {
        for(ShopView shop : shopViews.values())
            shop.refreshShopStatus();
    }

    public Inventory borrowItems() {
        return borrowItems(3);
    }

    public Inventory borrowItems(int amount) {
        Item[] borrowing = new Item[amount];
        synchronized (itemPool) {
            for(int i = 0; i < amount; i++) {
                int randomIndex = random.nextInt(itemPool.size());
                borrowing[i] = itemPool.remove(randomIndex);
            }
        }

        SlottedItem[] slottedItems = new SlottedItem[amount];
        for(int i = 0; i < amount; i++)
            slottedItems[i] = new SlottedItem(500 + i, borrowing[i]);

        return new Inventory(slottedItems);
    }

    public void returnItems(Inventory items) {
        SlottedItem[] slotteds = items.items();
        synchronized (itemPool) {
            for (SlottedItem slotted : slotteds) {
                Item toBeReturned = slotted.item();
                itemPool.add(toBeReturned);
            }
        }
    }

    public void returnItem(SlottedItem item) {
        synchronized (itemPool) {
            itemPool.add(item.item());
        }
    }

}
