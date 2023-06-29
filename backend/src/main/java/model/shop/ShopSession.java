package model.shop;

import model.mappings.Inventory;
import model.mappings.SlottedItem;

import java.util.Optional;

public class ShopSession {

    private transient LobbyShop shop;

    private int gold;
    private Inventory wares;

    public ShopSession(LobbyShop shop) {
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

    public boolean refresh() {
        if(!canAffordRefresh())
            return false;

        gold -= 1;
        shop.returnItems(wares);
        wares = shop.borrowItems();
        return true;
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
