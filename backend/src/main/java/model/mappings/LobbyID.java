package model.mappings;

import util.JsonConverter;

public record LobbyID(String lobbyID) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
