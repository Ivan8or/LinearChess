const API_ENDPOINT = "/api/v1/lobbies/shops"

export function viewShop(sessionID, lobbyID) {
    return fetch(import.meta.env.VITE_API_URL + API_ENDPOINT + "/view",
        {
            method: "GET",
            headers: {
                "session": JSON.stringify({ "sessionID": sessionID }),
                "lobby": JSON.stringify({ "lobbyID": lobbyID })
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function refreshShop(sessionID, lobbyID) {
    return fetch(import.meta.env.VITE_API_URL + API_ENDPOINT + "/refresh",
        {
            method: "PATCH",
            headers: {
                "session": JSON.stringify({ "sessionID": sessionID }),
                "lobby": JSON.stringify({ "lobbyID": lobbyID })
            }
        })
        .then((response) => response.json())
        .catch(() => null);
}

export function purchase(sessionID, lobbyID, from, to) {
    return fetch(import.meta.env.VITE_API_URL + API_ENDPOINT + "/buy",
        {
            method: "PATCH",
            headers: {
                "session": JSON.stringify({ "sessionID": sessionID }),
                "lobby": JSON.stringify({ "lobbyID": lobbyID })
            },
            body: JSON.stringify({"slotFrom": from, "slotTo": to}),
        })
        .then((response) => response.json())
        .catch(() => null);
}

