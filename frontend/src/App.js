import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";

import LobbyPage from 'pages/LobbyPage'
import HomePage from 'pages/HomePage'

import startSession from "api/v1/sessions/startSession";
import getSession from "api/v1/sessions/getSession";


const SESSION = {
    KEY: "session",
    MESSAGE_VALID: "VALID_SESSION"
}

export default function App() {

    const [sessionID, setSessionID] = useState(null)

    const spawnNew = () => startSession()
        .then((session) => setSessionID(session.sessionID))

    useEffect(() => {
        if (sessionID) {
            window.localStorage.setItem(SESSION.KEY, sessionID)
        }
    }, [sessionID])

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
            <Route index element={<HomePage />} />
            <Route path="/:id" element={<LobbyPage />} />
        </Routes>
    );
}       