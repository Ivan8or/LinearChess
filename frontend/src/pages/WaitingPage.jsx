import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

import NavBar from 'components/NavBar';
import ThemeToggle from 'components/ThemeToggle';

import 'css/WaitingPage.css'

export default function WaitingPage( { lobbyId } ) {

    const lobbyUrl = window.location.href
    const navigate = useNavigate();

    return (
        <div style={{"textAlign": "center"}}>
            <NavBar template={"2% 1fr 1fr 1fr 4fr 1fr 2%"} spacing={"1%"} >
                <span></span>
                <Link draggable="false" to="/rules">Rules</Link>
                <Link draggable="false" to="/discord">Discord</Link>
                <Link draggable="false" to="/donate">Donate</Link>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>

            <h2 id="player-count">1 / 2 Players</h2><br></br>
            <h1 onClick={() => navigator.clipboard.writeText(lobbyId)} id="lobby-id-title">{lobbyId}</h1><br></br>
            <h3 onClick={() => navigator.clipboard.writeText(lobbyUrl)} id="lobby-link"> {lobbyUrl} </h3>

            <div id={"left-banner-ad"}>Ad</div>
            <div id={"right-banner-ad"}>Ad</div>

            <h3 id="websocket-status"> status: connected </h3>
            <button className="big-button"> Ready </button>
            <button onClick={() => navigate('/')} className="big-button"> Leave </button>
        </div>
    );
}