package model.mappings;

import java.util.Arrays;

public class Endpoint {

    private final String endpoint;
    private final String[] methods;

    public Endpoint(String endpoint, String[] methods) {
        this.endpoint = endpoint;
        this.methods = methods;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String[] getMethods() {
        return methods;
    }

    @Override
    public boolean equals(Object other) {
        if(this.getClass() != other.getClass())
            return false;

        Endpoint otherEndpoint = (Endpoint) other;

        return  this.endpoint.equals(otherEndpoint.endpoint)
                && Arrays.equals(methods, otherEndpoint.methods);
    }
}
