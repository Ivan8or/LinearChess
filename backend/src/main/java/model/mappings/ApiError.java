package model.mappings;

import util.JsonConverter;

public record ApiError(int status, String message) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
