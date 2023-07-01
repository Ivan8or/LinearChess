package model.mappings;

import util.JsonConverter;

public record BoardFEN(String fen) {

    @Override
    public String toString() {
        return JsonConverter.toJson(this);
    }
}
