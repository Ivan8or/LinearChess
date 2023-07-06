import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import JoinLobbyModal from './JoinLobbyModal';
import MainNavBar from 'components/common/MainNavBar';
import MainFooter from 'components/common/MainFooter';

import { startLobby } from "api/v1/lobbies/lobbies";

import 'css/home/HomePage.css';

export default function HomePage({ sessionID }) {

    const [modal, setModal] = useState(false)
    const navigate = useNavigate();

    return (
        <>
        <div className="page-root">
            <MainNavBar />

            <div>
                <h1 className="title">LinearChess.com</h1>

                <button onClick={() => startLobby(sessionID).then(r => r.lobbyID).then(id => navigate(id))} className="big-button">
                    Create Lobby
                </button>

                <button onClick={() => setModal(true)} className="big-button">
                    Join Lobby
                </button>
            </div>

            <MainFooter />
        </div>
        <JoinLobbyModal isOpen={modal} disable={() => setModal(false)}></JoinLobbyModal>
        </>
    )
}