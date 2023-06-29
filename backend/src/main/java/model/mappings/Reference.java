package model.mappings;

import java.util.Arrays;

public record Reference(Endpoint[] endpoints) {

    @Override
    public boolean equals(Object other) {
        if(other == null || this.getClass() != other.getClass())
            return false;

        Reference otherReference = (Reference) other;
        return Arrays.equals(endpoints, otherReference.endpoints);
    }
}
