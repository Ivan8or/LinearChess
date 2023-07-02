package model.mappings;

import chess.eval.ChessEval;
import chess.eval.impl.CumulativeEval;
import util.ItemCatalog;
import util.JsonConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public ChessEval translate(int[] evalSlots, int[] multiplierSlots) {

        List<ChessEval> components = new ArrayList<>(evalSlots.length);
        components.add(ItemCatalog.eval(1000).get());

        for(int i = 0; i < evalSlots.length; i++) {
            Optional<SlottedItem> nextEval = getSlot(evalSlots[i]);
            Optional<SlottedItem> nextMultiplier = getSlot(multiplierSlots[i]);
            if(nextEval.isEmpty())
                continue;

            int evalId = nextEval.get().item().id();
            int multiplierId = nextMultiplier.map(slottedItem -> slottedItem.item().id()).orElse(-1);

            Optional<ChessEval> chessEval = ItemCatalog.build(evalId, multiplierId);
            chessEval.ifPresent(components::add);
        }

        return new CumulativeEval(components.toArray(ChessEval[]::new));
    }
}
