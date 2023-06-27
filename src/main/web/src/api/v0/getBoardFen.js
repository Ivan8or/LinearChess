const API_ENDPOINT = "/api/v0/fen";

export default function getBoardFen() {
    if(process.env.NODE_ENV !== 'production' && process.env.REACT_APP_DUMMY_API_CALLS === "true")
        return new Promise((res, err) => res("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));

    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT, {method: "GET"}).then((response) => response.text());
}