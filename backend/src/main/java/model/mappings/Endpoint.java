package model.mappings;

import java.util.Arrays;

public class Endpoint {

    private String endpoint;
    private String[] methods;

    public Endpoint() {

    }
    public Endpoint(String endpoint, String[] methods) {
        this.endpoint = endpoint;
        this.methods = methods;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
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
