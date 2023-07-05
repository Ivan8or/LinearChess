import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";

import LobbyPage from 'components/lobby/LobbyPage'
import HomePage from 'components/home/HomePage'

import { startSession, getSession } from "api/v1/sessions/sessions";

const SESSION = {
    KEY: "session",
    MESSAGE_VALID: "VALID_SESSION"
}

export default function App() {

    const [sessionID, setSessionID] = useState(null)

    const spawnNew = () => startSession()
        .then((session) => {
            setSessionID(session.sessionID)
            window.localStorage.setItem(SESSION.KEY, session.sessionID)
        })

    useEffect(() => {
        const cachedSessionID = window.localStorage.getItem(SESSION.KEY)
        if (cachedSessionID === null) {
            spawnNew()
            return;
        }
        getSession(cachedSessionID)
            .then((res) => res.message === SESSION.MESSAGE_VALID ? setSessionID(cachedSessionID) : spawnNew())
            
    }, [setSessionID])

    return (
        <Routes>
            <Route index element={<HomePage sessionID={sessionID} />} />
            <Route path="/:id" element={<LobbyPage sessionID={sessionID} />} />
        </Routes>
    );
}       