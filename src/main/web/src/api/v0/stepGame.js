const API_ENDPOINT = "/api/v0/increment";

export default function stepGame() {
    return new Promise((res, err) => res("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT, {method: "PATCH"}).then((response) => response.text());;
}