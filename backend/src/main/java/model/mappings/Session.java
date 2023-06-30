package model.mappings;

import util.JsonConverter;

import java.util.UUID;

public record Session(UUID sessionID) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }

    public static Session spawn() {
        return new Session(UUID.randomUUID());
    }
}
