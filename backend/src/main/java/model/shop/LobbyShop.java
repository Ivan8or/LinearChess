package model.shop;

import model.mappings.Inventory;
import model.mappings.Item;
import model.mappings.SlottedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.random.RandomGenerator;

public class LobbyShop {

    final private List<Item> itemPool;
    final private RandomGenerator random;

    public LobbyShop(List<Item> items) {
        itemPool = new ArrayList<>(items);
        random = new SplittableRandom();
    }

    public LobbyShop(List<Item> items, RandomGenerator random) {
        itemPool = new ArrayList<>(items);
        this.random = random;
    }

    public List<Item> itemPool() {
        return itemPool;
    }

    public int startingGold() {
        return 4;
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
            slottedItems[i] = new SlottedItem(i, borrowing[i]);

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
