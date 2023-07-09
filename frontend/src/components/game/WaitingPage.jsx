import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import MainNavBar from 'components/common/MainNavBar';
import MainFooter from 'components/common/MainFooter';

import { addPlayer, removePlayer, getLobby, toggleReady } from "api/v1/lobbies/lobbies";

import 'css/game/WaitingPage.css'


const JOIN_MSGS = ['SESSION_ALREADY_IN_LOBBY', 'SUCCESS']

export default function WaitingPage({ session, lobby, connected, started }) {

    console.log('waiting page with',session,lobby)

    const navigate = useNavigate();
    const [info, setInfo] = useState({ players: 1, ready: false })

    const readyButton = () => toggleReady(session, lobby).then(res => setInfo(old => ({ ...old, ready: res.isPlayerReady })))

    useEffect(() => {
        addPlayer(session, lobby).then(added => {
            if (!JOIN_MSGS.includes(added.message)) {
                console.log(added.message)
                throw new Error()
            }
            return getLobby(session, lobby)
        }).then(res => setInfo({ players: res.playerCount, ready: res.isPlayerReady })).catch(e => navigate('/'))
        return () => {
            //console.log('checking... current is',started.current)
            //if(!started.current) removePlayer(session, lobby)
            // TODO FIX THIS !!!
        }
    }, [session, lobby])


    const readyButtonText = info.ready ? <b>Ready!</b> : "Ready?"
    const connectStatus = connected ? "connected" : "connecting..."
    const lobbyUrl = window.location.href

    return (
        <div className="page-root">
            <MainNavBar />
            <div style={{ "textAlign": "center" }}>

                <h2 id="player-count"> {info.players} / 2 Players </h2><br></br>
                <h1 onClick={() => navigator.clipboard.writeText(lobby)} id="lobby-id-title">{lobby}</h1><br></br>
                <h3 onClick={() => navigator.clipboard.writeText(lobbyUrl)} id="lobby-link"> {lobbyUrl} </h3>

                <div id={"left-banner-ad"}>Ad</div>
                <div id={"right-banner-ad"}>Ad</div>

                <h3 id="websocket-status"> status: {connectStatus} </h3>
                <button disabled={!connected} onClick={readyButton} className="big-button" id="ready-button"> {readyButtonText} </button>
                <button onClick={() => navigate('/')} className="big-button"> Leave </button>
            </div>
            <MainFooter />
        </div>
    );
}