package model.mappings;

import util.JsonConverter;

import java.util.Arrays;

public record Reference(Endpoint[] endpoints) {

    @Override
    public boolean equals(Object other) {
        if(other == null || this.getClass() != other.getClass())
            return false;

        Reference otherReference = (Reference) other;
        return Arrays.equals(endpoints, otherReference.endpoints);
    }

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
