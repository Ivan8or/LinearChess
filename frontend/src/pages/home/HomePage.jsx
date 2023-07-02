import React from "react";

import './HomePage.css'

export default function HomePage() {

    return (
        <>
            <h1 className="title">LinearChess.com</h1>
            <button id="create-lobby" className="big-button">Create Lobby</button>
            <button id="join-lobby" className="big-button">Join Lobby</button>
        </>
    )
}