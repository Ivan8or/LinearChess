const API_ENDPOINT = "/api/v1/lobbies/boards"

export function getBoard(sessionID, lobbyID) {
    return fetch(import.meta.env.VITE_API_URL + API_ENDPOINT,
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