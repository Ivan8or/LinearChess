package model.mappings;

import java.util.Arrays;

public class Reference {

    private Endpoint[] endpoints;

    public Reference() {

    }

    public Reference(Endpoint[] endpoints) {
        this.endpoints = endpoints;
    }

    public Endpoint[] getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Endpoint[] endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public boolean equals(Object other) {
        if(this.getClass() != other.getClass())
            return false;

        Reference otherReference = (Reference) other;
        return Arrays.equals(endpoints, otherReference.endpoints);
    }
}
