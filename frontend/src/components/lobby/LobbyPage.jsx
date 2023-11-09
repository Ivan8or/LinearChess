import React, { useState, useEffect, useRef } from 'react';
import { useParams } from "react-router-dom"

import WaitingPage from '../game/WaitingPage';
import GamePage from '../game/GamePage';
import { validCodeFormat } from 'api/util/isValidLobby';
import { getLobby } from 'api/v1/lobbies/lobbies';

export default function LobbyPage({ sessionID }) {

    const { id } = useParams()
    const [started, setStarted] = useState(false)

    console.log('lobby page with', sessionID, id)

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

        if (socket.readyState === 1) {
            joinLobbyPacket(socket, sessionID, id);
        }
        else {
            socket.onopen = (e) => {
                console.log("SOCKET OPENED EVENT")
                joinLobbyPacket(socket, sessionID, id);
            }
        }

        socket.onclose = (e) => {
            console.log("SOCKET CLOSED EVENT")
            setConnected(false)
        }

        socket.addEventListener("message", (e) => {
            console.log("SOCKET MESSAGE EVENT")
            const body = JSON.parse(e.data)
            console.log('message received:', body)
            if (body?.key === "connected") {
                setConnected(body?.value === "TRUE")
            }
        })
    }, [socket])


    return started
        ? <GamePage key="gamepage" session={sessionID} lobby={id} socket={socket} ></GamePage>
        : <WaitingPage key="waitingpage" session={sessionID} lobby={id} connected={connected} ></WaitingPage>
}


function joinLobbyPacket(socket, session, lobby) {
    console.log("SENDING JOIN LOBBY PACKET")
    socket.send(JSON.stringify({
        key: "JOIN_LOBBY",
        session: { sessionID: session },
        lobby: { lobbyID: lobby }
    }));
}