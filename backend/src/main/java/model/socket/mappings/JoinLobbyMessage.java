package model.socket.mappings;

import model.mappings.LobbyID;
import model.mappings.Session;
import util.JsonConverter;

public record JoinLobbyMessage(Session session, LobbyID lobby) {
        @Override
        public String toString() {
            return JsonConverter.toJson(this);
        }
}
