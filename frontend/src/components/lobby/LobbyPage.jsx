import React from 'react';
import { useParams } from "react-router-dom"

import WaitingPage from '../game/WaitingPage';
import GamePage from '../game/GamePage';

import isValidLobby from 'api/util/isValidLobby';

export default function LobbyPage({ sessionID }) {
    const { id } = useParams()
    return id === "game"
        ? <GamePage sessionID={sessionID} lobbyID={id}></GamePage>
        : <WaitingPage sessionID={sessionID} lobbyID={id}></WaitingPage>
}