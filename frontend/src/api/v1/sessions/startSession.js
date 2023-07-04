const API_ENDPOINT = "/api/v1/sessions"
const METHOD = "POST"

export default function startSession() {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: METHOD
        })
        .then((response) => response.json())
        .catch(() => null);
}