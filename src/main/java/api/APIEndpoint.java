package api;

import spark.Service;

public abstract class APIEndpoint {

    protected String commonPath = "";

    public APIEndpoint withCommonPath(String commonPath) {
        this.commonPath = commonPath;
        return this;
    }

    public abstract void start(Service sparkService);
}
