import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import MainNavBar from 'components/common/MainNavBar';
import MainFooter from 'components/common/MainFooter';

import { addPlayer, removePlayer, getLobby, toggleReady } from "api/v1/lobbies/lobbies";

import 'css/game/WaitingPage.css'


const JOIN_MSGS = ['SESSION_ALREADY_IN_LOBBY', 'SUCCESS']

export default function WaitingPage({ sessionID, lobbyID }) {

    console.log('loaded waiting page')
    const navigate = useNavigate();
    const [info, setInfo] = useState({ players: 1, ready: false })

    const readyButton = () => toggleReady(sessionID, lobbyID).then(res => setInfo(old => ({ ...old, ready: res.isPlayerReady })))

    useEffect(() => {
        addPlayer(sessionID, lobbyID).then(added => {
            if (!JOIN_MSGS.includes(added.message)) {
                throw new Error()
            }
            return getLobby(sessionID, lobbyID)
        }).then(res => setInfo({ players: res.playerCount, ready: res.isPlayerReady })).catch(e => navigate('/'))

        return () => removePlayer(sessionID, lobbyID)
    }, [sessionID, lobbyID])


    const readyButtonText = info.ready ? <b>Ready!</b> : "Ready?"
    const lobbyUrl = window.location.href

    return (
        <div className="page-root">
            <MainNavBar />
            <div style={{ "textAlign": "center" }}>

                <h2 id="player-count"> {info.players} / 2 Players </h2><br></br>
                <h1 onClick={() => navigator.clipboard.writeText(lobbyID)} id="lobby-id-title">{lobbyID}</h1><br></br>
                <h3 onClick={() => navigator.clipboard.writeText(lobbyUrl)} id="lobby-link"> {lobbyUrl} </h3>

                <div id={"left-banner-ad"}>Ad</div>
                <div id={"right-banner-ad"}>Ad</div>

                <h3 id="websocket-status"> status: connected </h3>
                <button onClick={readyButton} className="big-button"> {readyButtonText} </button>
                <button onClick={() => navigate('/')} className="big-button"> Leave </button>
            </div>
            <MainFooter />
        </div>
    );
}