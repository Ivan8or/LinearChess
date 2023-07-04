const API_ENDPOINT = "/api/v1/sessions"
const METHOD = "GET"

export default function getSession(sessionID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: METHOD,
            headers: {
                "session": JSON.stringify({sessionID: sessionID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}