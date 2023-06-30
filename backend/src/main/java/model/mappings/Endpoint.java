package model.mappings;

import util.JsonConverter;

import java.util.Arrays;

public record Endpoint (String endpoint, String... methods) {

    @Override
    public boolean equals(Object other) {
        if(other == null || this.getClass() != other.getClass())
            return false;

        Endpoint otherEndpoint = (Endpoint) other;

        return  this.endpoint.equals(otherEndpoint.endpoint)
                && Arrays.equals(methods, otherEndpoint.methods);
    }

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}

