import React from 'react';
import { useParams } from "react-router-dom"

import WaitingPage from './WaitingPage';
import GamePage from './GamePage';

import isValidLobby from 'api/util/isValidLobby';

export default function LobbyPage({ sessionID }) {
    const { id } = useParams()
    return isValidLobby(id)
        ? <GamePage sessionID={sessionID} lobbyID={id}></GamePage>
        : <WaitingPage sessionID={sessionID} lobbyID={id}></WaitingPage>
}