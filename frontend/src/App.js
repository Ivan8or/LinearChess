import React from "react";
import { Routes, Route } from "react-router-dom";

import GamePage from 'pages/GamePage'
import HomePage from 'pages/HomePage'

export default function App() {
    return (
        <Routes>
            <Route index element={<HomePage />} />
            <Route path="/:id" element={<GamePage />} />
        </Routes>
    );
}       