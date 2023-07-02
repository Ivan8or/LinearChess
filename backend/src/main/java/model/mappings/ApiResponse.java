package model.mappings;

import util.JsonConverter;

public record ApiResponse(int status, String message) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
