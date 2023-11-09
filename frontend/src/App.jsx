import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";

import LobbyPage from '/src/components/lobby/LobbyPage'
import HomePage from '/src/components/home/HomePage'

import useSession from "/src/components/hooks/useSession";

import '/src/css/common/App.css'

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