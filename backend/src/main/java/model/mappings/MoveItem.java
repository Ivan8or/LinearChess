package model.mappings;

import util.JsonConverter;

public record MoveItem(int slotFrom, int slotTo) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
