package model.shop;

import model.mappings.Inventory;
import model.mappings.SlottedItem;

import java.util.Optional;

public class ShopView {

    private transient ItemShop shop;

    private int gold;
    private Inventory wares;

    public ShopView(ItemShop shop) {
        this.shop = shop;
        this.gold = shop.startingGold();
        this.wares = shop.borrowItems();
    }

    public Inventory getWares() {
        return wares;
    }

    public int getGold() {
        return gold;
    }

    public boolean canAffordRefresh() {
        return gold >= 1;
    }

    public boolean restockWares() {
        if(!canAffordRefresh())
            return false;

        gold -= 1;
        forceRestockWares();
        return true;
    }

    public void refresh() {
        gold = 4;
        forceRestockWares();
    }

    public void forceRestockWares() {
        shop.returnItems(wares);
        wares = shop.borrowItems();
    }

    public boolean warePresent(int slot) {
        return wares.getSlot(slot).isPresent();
    }

    public boolean canAffordBuy(int slot) {
        return gold >= 3;
    }

    public Optional<SlottedItem> buy(int slot) {
        if(!canAffordBuy(slot))
            return Optional.empty();

        gold -= 3;
        Optional<SlottedItem> toReturn = wares.getSlot(slot);
        wares = wares.removeItem(slot);
        return toReturn;
    }

    public void sell(SlottedItem item) {
        shop.returnItem(item);
        gold += 1;
    }
}
