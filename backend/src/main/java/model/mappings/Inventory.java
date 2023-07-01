package model.mappings;

import chess.eval.ChessEval;
import util.JsonConverter;

import java.util.Arrays;
import java.util.Optional;

public record Inventory(SlottedItem... items) {

    @Override
    public boolean equals(Object other) {

        if(other == null || this.getClass() != other.getClass())
            return false;

        Inventory otherInventory = (Inventory) other;
        return Arrays.equals(items, otherInventory.items);
    }

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }

    public Optional<SlottedItem> getSlot(int slot) {
        for(SlottedItem item : items)
            if(item.slot() == slot)
                return Optional.of(item);

        return Optional.empty();
    }

    public Inventory removeItem(int slot) {
        SlottedItem[] updatedItems = Arrays.stream(items)
                .filter(i -> i.slot() != slot)
                .toArray(SlottedItem[]::new);

        return new Inventory(updatedItems);
    }

    public Inventory addItem(SlottedItem item) {
        SlottedItem[] updatedItems = Arrays.copyOf(items, items.length + 1);
        updatedItems[updatedItems.length - 1] = item;
        return new Inventory(updatedItems);
    }

    public Inventory moveItem(int slotFrom, int slotTo) {
        SlottedItem[] updatedItems = Arrays.copyOf(items, items.length);
        for(int i = 0; i < updatedItems.length; i++) {
            if (updatedItems[i].slot() == slotFrom) {
                updatedItems[i] = new SlottedItem(slotTo,updatedItems[i].item());
                return new Inventory(updatedItems);
            }
        }
        throw new IllegalStateException("can't move nonexistent slot");
    }

    public boolean occupied(int slot) {
        return getSlot(slot).isEmpty();
    }

    public ChessEval translate() {
        return null;
    }
}
