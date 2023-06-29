package model.mappings;

import java.util.Arrays;

public record Inventory(SlottedItem[] slots) {

    @Override
    public boolean equals(Object other) {
        if(this.getClass() != other.getClass())
            return false;

        Inventory otherInventory = (Inventory) other;
        return Arrays.equals(slots, otherInventory.slots);
    }
}
