const API_ENDPOINT = "/api/v1/lobbies"

export function startLobby(sessionID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "POST",
            headers: {
                "session": JSON.stringify({"sessionID": sessionID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function getLobby(sessionID, lobbyID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "GET",
            headers: {
                "session": JSON.stringify({"sessionID": sessionID}),
                "lobby": JSON.stringify({"lobbyID": lobbyID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function addPlayer(sessionID, lobbyID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "PUT",
            headers: {
                "session": JSON.stringify({"sessionID": sessionID}),
                "lobby": JSON.stringify({"lobbyID": lobbyID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function removePlayer(sessionID, lobbyID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "DELETE",
            headers: {
                "session": JSON.stringify({"sessionID": sessionID}),
                "lobby": JSON.stringify({"lobbyID": lobbyID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function toggleReady(sessionID, lobbyID) {
    return fetch(process.env.REACT_APP_API_URL + API_ENDPOINT,
        {
            method: "PATCH",
            headers: {
                "session": JSON.stringify({"sessionID": sessionID}),
                "lobby": JSON.stringify({"lobbyID": lobbyID})
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}