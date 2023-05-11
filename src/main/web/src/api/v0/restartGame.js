const API_ENDPOINT = "http://localhost:3100/api/v0/reset";

export default function restartGame() {
    return fetch(API_ENDPOINT, {method: "PATCH"});
}
