import React from "react";
import { Routes, Route } from "react-router-dom";

import GamePage from 'pages/game/GamePage'
import HomePage from 'pages/home/HomePage'

export default function App() {
    return (
        <Routes>
            <Route index element={<HomePage />} />
            <Route path="/:id" element={<GamePage />} />
        </Routes>
    );
}       