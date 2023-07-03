const API_ENDPOINT = "/api/v1/sessions"
const METHOD = "DELETE"

export default function endSession(session) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: METHOD,
            headers: {
                "session": JSON.stringify(session)
            }
        })
        .then((response) => response.json());
}