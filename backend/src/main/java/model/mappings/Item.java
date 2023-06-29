package model.mappings;

import util.JsonConverter;

public record Item (String type, int id) {
    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
