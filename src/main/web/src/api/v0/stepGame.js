const API_ENDPOINT = "http://localhost:3100/api/v0/increment";

export default function stepGame() {
    return fetch(API_ENDPOINT, {method: "GET"});
}
