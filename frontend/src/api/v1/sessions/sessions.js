const API_ENDPOINT = "/api/v1/sessions"

export function endSession(sessionID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "DELETE",
            headers: {
                "session": JSON.stringify({"sessionID": sessionID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function getSession(sessionID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "GET",
            headers: {
                "session": JSON.stringify({sessionID: sessionID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}


export function startSession() {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "POST"
        })
        .then((response) => response.json())
        .catch(() => null);
}