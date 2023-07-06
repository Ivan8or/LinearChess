import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";

import LobbyPage from 'components/lobby/LobbyPage'
import HomePage from 'components/home/HomePage'

import useSession from "components/hooks/useSession";

import 'css/common/App.css'

const SESSION = {
    KEY: "session",
    MESSAGE_VALID: "VALID_SESSION"
}

export default function App() {

    const [sessionID, _] = useSession(null)
    console.log('session is',sessionID)

    return (
        <Routes>
            <Route index element={<HomePage sessionID={sessionID} />} />
            <Route path="/:id" element={<LobbyPage sessionID={sessionID} />} />
        </Routes>
    );
}       