package model.mappings;

import java.util.Arrays;

public record Endpoint (String endpoint, String[] methods) {

    @Override
    public boolean equals(Object other) {
        if(this.getClass() != other.getClass())
            return false;

        Endpoint otherEndpoint = (Endpoint) other;

        return  this.endpoint.equals(otherEndpoint.endpoint)
                && Arrays.equals(methods, otherEndpoint.methods);
    }
}

