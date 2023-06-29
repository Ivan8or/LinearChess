package model.shop;

public class ShopSession {

    private transient LobbyShop shop;

    private int gold;
    private Object[] wares;

    public Object[] getWares() {
        return wares;
    }

    public int getGold() {
        return gold;
    }

    public boolean canAffordRefresh() {
        return gold >= 1;
    }

    public boolean refresh() {
        return true;
    }

    public boolean warePresent(int slot) {
        return wares[slot] != null;
    }

    public boolean canAffordBuy(int slot) {
        return gold >= 3;
    }

    public boolean buy(int slot) {
        return true;
    }

    public boolean sell(int slot) {
        return true;
    }


}
