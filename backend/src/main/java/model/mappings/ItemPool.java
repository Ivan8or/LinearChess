package model.mappings;

import util.JsonConverter;

public record ItemPool(Item item, int amount) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }

    public Item[] expand() {
        Item[] toReturn = new Item[amount];
        for(int i = 0; i < amount; i++)
            toReturn[i] = item;

        return toReturn;
    }
}
