package model.mappings;

import util.JsonConverter;

public record SlottedItem (int slot, Item item){
    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
