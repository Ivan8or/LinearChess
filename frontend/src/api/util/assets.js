export function resourcePathById(item) {
    const name = assetName(item?.id)
    if (item?.type === "eval") {
        const path = `./assets/logic/cards/${name}.svg`
        return path
    }
    else if (item?.type === "multiplier") {
        return `./assets/logic/gems/${name}.jpg`
    }
}

function assetName(id) {
    return {
        1001: "white_pawn_count",
        1002: "white_bishop_count",
        1003: "white_knight_count",
        1004: "white_rook_count",
        1005: "white_queen_count",
        1006: "black_pawn_count",
        1007: "black_bishop_count",
        1008: "black_knight_count",
        1009: "black_rook_count",
        1010: "black_queen_count",

        1011: "white_pawn_across",
        1012: "white_bishop_across",
        1013: "white_knight_across",
        1014: "white_rook_across",
        1015: "white_queen_across",
        1016: "black_pawn_across",
        1017: "black_bishop_across",
        1018: "black_knight_across",
        1019: "black_rook_across",
        1020: "black_queen_across",

        1021: "white_pawn_center",
        1022: "white_bishop_center",
        1023: "white_knight_center",
        1024: "white_rook_center",
        1025: "white_queen_center",
        1026: "black_pawn_center",
        1027: "black_bishop_center",
        1028: "black_knight_center",
        1029: "black_rook_center",
        1030: "black_queen_center",

        1100: "tie",
        1101: "white_win",
        1102: "black_win",

        2000: "green_dodec",
        2001: "green_dodec",
        2002: "green_dodec",
        2003: "green_dodec",

        2100: "yellow_hex",
        2101: "yellow_hex",
        2102: "yellow_hex",
        2103: "yellow_hex",
    }?.[id];
}