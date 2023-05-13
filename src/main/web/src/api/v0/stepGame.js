const API_ENDPOINT = "/api/v0/increment";

export default function stepGame() {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT, {method: "PATCH"});
}