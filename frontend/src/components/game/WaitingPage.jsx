import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import MainNavBar from '/src/components/common/MainNavBar';
import MainFooter from '/src/components/common/MainFooter';

import { addPlayer, removePlayer, getLobby, toggleReady } from '/src/api/v1/lobbies/lobbies';

import '/src/css/game/WaitingPage.css'


const JOIN_MSGS = ['SESSION_ALREADY_IN_LOBBY', 'SUCCESS']

export default function WaitingPage({ session, lobby, setStarted }) {

    const navigate = useNavigate();
    const [info, setInfo] = useState({ players: 1, ready: false, playersReady: 0})

    const readyButton = () => toggleReady(session, lobby, !info.ready)

    // run on load page
    useEffect(() => {
        addPlayer(session, lobby)
        .then(added => {
            if (!JOIN_MSGS.includes(added.message)) {
                console.log(added.message)
                throw new Error()
            } 
        })
        return () => {}
    }, [session, lobby])

    // poll every X miliseconds
    const pollSpeed = 1000;
    useEffect(() => {
        const interval = setInterval(() => {
            getLobby(session, lobby)
            .then(res => {
                if(res.playersReady == 2) {
                    setStarted(true);
                }
                
                setInfo({ players: res.playerCount, ready: res.isPlayerReady, playersReady: res.playersReady })
            })
            .catch(e => navigate('/'))
        }, pollSpeed);
    
        return () => clearInterval(interval);
    }, [session, lobby]);


    const connected = true // TO REMOVE FOREVER??? MAYBE??

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

                <h3 id="websocket-status"> players ready: {info.playersReady} / 2 </h3>
                <button disabled={!connected} onClick={readyButton} className="big-button" id="ready-button"> {readyButtonText} </button>
                <button onClick={() => navigate('/')} className="big-button"> Leave </button>
            </div>
            <MainFooter />
        </div>
    );
}