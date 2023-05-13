const API_ENDPOINT = "/api/v0/fen";

export default function getBoardFen() {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT, {method: "GET"});
}