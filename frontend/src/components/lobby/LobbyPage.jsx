import React, { useState, useEffect, useRef } from 'react';
import { useParams } from "react-router-dom"

import WaitingPage from '../game/WaitingPage';
import GamePage from '../game/GamePage';
import { getLobby } from '/src/api/v1/lobbies/lobbies';

export default function LobbyPage({ sessionID }) {

    const { id } = useParams()
    const [started, setStarted] = useState(false)

    useEffect(() => {
        getLobby(sessionID, id)
            .then(e => e.isLobbyStarted)
            .then(setStarted)
            .catch(console.log)
    }, [setStarted])

    return started
        ? <GamePage key="gamepage" session={sessionID} lobby={id} ></GamePage>
        : <WaitingPage key="waitingpage" session={sessionID} lobby={id} setStarted={setStarted} ></WaitingPage>
}