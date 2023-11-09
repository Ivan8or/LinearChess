import React, { useState, useEffect, useRef } from 'react';
import { useParams } from "react-router-dom"

import WaitingPage from '../game/WaitingPage';
import GamePage from '../game/GamePage';
import { validCodeFormat } from '/src/api/util/isValidLobby';
import { getLobby } from '/src/api/v1/lobbies/lobbies';

export default function LobbyPage({ sessionID }) {

    const { id } = useParams()
    const [started, setStarted] = useState(false)
    
    console.log('lobby page with',sessionID,id)

    useEffect(() => {
        getLobby(sessionID, id)
            .then(e => e.isLobbyStarted)
            .then(setStarted)
            .catch(console.log)
    }, [setStarted])

    const [socket, setSocket] = useState(() => {
        console.log("SOCKET CREATED")
        return new WebSocket("ws://localhost:3200")
    })
    const [connected, setConnected] = useState(false);


    useEffect(() => {
        if (socket === null || socket === undefined) return

        socket.onopen = (e) => {
            console.log("SOCKET OPENED EVENT")
            console.log('ready state',socket.readyState)
            socket.send(JSON.stringify({
                key: "JOIN_LOBBY",
                session: { sessionID: sessionID },
                lobby: { lobbyID: id }
            }));
        }

        socket.onclose = (e) => {
            console.log("SOCKET CLOSED EVENT")
            setConnected(false)
        }

        socket.onmessage = (e) => {
            console.log("SOCKET MESSAGE EVENT")
            const body = JSON.parse(e.data)
            console.log('message received:', body)
            if (body?.key === "connected") {
                setConnected(body?.value === "TRUE")
            }
        }
    }, [socket])


    return started
        ? <GamePage key="gamepage" session={sessionID} lobby={id} ></GamePage>
        : <WaitingPage key="waitingpage" session={sessionID} lobby={id} connected={connected} ></WaitingPage>
}