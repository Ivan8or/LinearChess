const API_ENDPOINT = "/api/v0/reset";

export default function restartGame() {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT, {method: "PATCH"});
}