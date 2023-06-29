package model.shop;

import model.mappings.Inventory;
import model.mappings.SlottedItem;

public class ShopSession {

    private transient LobbyShop shop;

    private int gold;
    private Inventory wares;

    public ShopSession(LobbyShop shop) {
        this.shop = shop;
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
        return true;
    }

    public boolean warePresent(int slot) {
        return wares.getSlot(slot).isPresent();
    }

    public boolean canAffordBuy(int slot) {
        return gold >= 3;
    }

    public SlottedItem buy(int slot) {
        SlottedItem purchasedItem = wares.getSlot(slot).get();

        return wares.getSlot(slot).get();
    }

    public void sell(SlottedItem item) {

    }
}
