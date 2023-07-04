import React from 'react';
import { useParams } from "react-router-dom"

import WaitingPage from './WaitingPage';
import GamePage from './GamePage';

import isValidLobby from 'api/util/isValidLobby';

import 'css/LobbyPage.css'

export default function LobbyPage() {
    const { id } = useParams()
    return isValidLobby(id) ? <GamePage lobbyId={id}></GamePage> : <WaitingPage lobbyId={id}></WaitingPage>
}