const API_ENDPOINT = "/api/v0/fen";

export default function getBoardFen() {
    if(import.meta.env.NODE_ENV !== 'production' && import.meta.env.VITE_DUMMY_API_CALLS === "true")
        return new Promise((res, err) => res("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));

    return fetch(import.meta.env.VITE_API_URL + API_ENDPOINT, {method: "GET"}).then((response) => response.text());
}