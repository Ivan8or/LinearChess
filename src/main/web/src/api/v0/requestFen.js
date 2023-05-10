const API_ENDPOINT = "http://localhost:3100/api/v0/fen";

export default function requestFen() {
    return fetch(API_ENDPOINT, {method: "GET"});
}
