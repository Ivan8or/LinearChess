import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import NavBar from 'components/NavBar';
import ThemeToggle from 'components/ThemeToggle';

import { startLobby, addPlayer, removePlayer } from "api/v1/lobbies/lobbies";

import 'css/WaitingPage.css'

export default function WaitingPage({ sessionID, lobbyID }) {

    const lobbyUrl = window.location.href
    const navigate = useNavigate();

    useEffect(() => {
        if (sessionID === null || lobbyID === null) {
            return
        }
        addPlayer(sessionID, lobbyID)
        return () => {
            removePlayer(sessionID, lobbyID)
        }
    }, [sessionID, lobbyID])


    return (
        <div style={{ "textAlign": "center" }}>
            <NavBar template={"2% 1fr 1fr 1fr 4fr 1fr 2%"} spacing={"1%"} >
                <span></span>
                <Link draggable="false" to="/rules">Rules</Link>
                <a draggable="false" href="https://discord.com">Discord</a>
                <a draggable="false" href="https://donate.com">Donate</a>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>

            <h2 id="player-count">1 / 2 Players</h2><br></br>
            <h1 onClick={() => navigator.clipboard.writeText(lobbyID)} id="lobby-id-title">{lobbyID}</h1><br></br>
            <h3 onClick={() => navigator.clipboard.writeText(lobbyUrl)} id="lobby-link"> {lobbyUrl} </h3>

            <div id={"left-banner-ad"}>Ad</div>
            <div id={"right-banner-ad"}>Ad</div>

            <h3 id="websocket-status"> status: connected </h3>
            <button onClick={() => startLobby(sessionID)} className="big-button"> Ready </button>
            <button onClick={() => navigate('/')} className="big-button"> Leave </button>
        </div>
    );
}