import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import JoinLobbyModal from 'components/JoinLobbyModal';
import NavBar from 'components/NavBar';
import ThemeToggle from 'components/ThemeToggle';

import 'css/HomePage.css';

export default function HomePage() {

    const [modal, setModal] = useState(false)

    return (
        <div>
            <NavBar template={"2% 1fr 1fr 1fr 4fr 1fr 2%"} spacing={"1%"} >
                <span></span>
                <Link draggable="false" to="/rules">Rules</Link>
                <Link draggable="false" to="/discord">Discord</Link>
                <Link draggable="false" to="/donate">Donate</Link>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>

            <h1 className="title">LinearChess.com</h1>

            <button className="big-button">
                Create Lobby
            </button>

            <button onClick={() => setModal(true)} className="big-button">
                Join Lobby
            </button>

            <JoinLobbyModal isOpen={modal} disable={() => setModal(false)}></JoinLobbyModal>
        </div>
    )
}