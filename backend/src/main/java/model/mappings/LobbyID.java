package model.mappings;

import util.JsonConverter;

public record LobbyID(String lobbyId) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
