package model.mappings;

import util.JsonConverter;

public record ApiError(String message) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
