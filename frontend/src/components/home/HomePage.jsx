import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import JoinLobbyModal from './JoinLobbyModal';
import NavBar from 'components/common/NavBar';
import ThemeToggle from 'components/common/ThemeToggle';

import { startLobby } from "api/v1/lobbies/lobbies";

import 'css/home/HomePage.css';

export default function HomePage( {sessionID} ) {

    const [modal, setModal] = useState(false)
    const navigate = useNavigate();

    return (
        <div>
            <NavBar template={"2% 1fr 1fr 1fr 4fr 1fr 2%"} spacing={"1%"} >
                <span></span>
                <Link draggable="false" to="/rules">Rules</Link>
                <a draggable="false" href="https://discord.com">Discord</a>
                <a draggable="false" href="https://donate.com">Donate</a>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>

            <h1 className="title">LinearChess.com</h1>

            <button onClick={() => startLobby(sessionID).then(r => r.lobbyID).then(id => navigate(id))} className="big-button">
                Create Lobby
            </button>

            <button onClick={() => setModal(true)} className="big-button">
                Join Lobby
            </button>

            <JoinLobbyModal isOpen={modal} disable={() => setModal(false)}></JoinLobbyModal>
        </div>
    )
}