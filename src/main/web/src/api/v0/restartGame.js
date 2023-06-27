const API_ENDPOINT = "/api/v0/reset";

export default function restartGame() {
    if(process.env.NODE_ENV !== 'production' && process.env.REACT_APP_DUMMY_API_CALLS === "true")
        return new Promise((res, err) => res("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));

    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT, {method: "PATCH"}).then((response) => response.text());;
}