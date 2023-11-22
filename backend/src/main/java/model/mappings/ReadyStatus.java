package model.mappings;

import util.JsonConverter;

public record ReadyStatus(boolean ready) {
    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
