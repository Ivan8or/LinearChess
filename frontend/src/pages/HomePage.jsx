import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import Modal from 'components/Modal';
import NavBar from 'components/NavBar';
import ThemeToggle from 'components/ThemeToggle';

import 'css/HomePage.css';

export default function HomePage() {

    const [isOpen, setIsOpen] = useState(false);

    return (
        <div>

            <NavBar template={"2% 1fr 1fr 1fr 60% 1fr 2%"} spacing={"1%"} >
                <span></span>
                {/* <img src="./assets/chess/p_w.svg" alt="logo"></img> */}
                <Link draggable="false" to="/alabama">alabama</Link>
                <Link draggable="false" to="/texas">texas</Link>
                <Link draggable="false" to="/mississipi">mississipi</Link>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>

            <h1 className="title">LinearChess.com</h1>

            <Modal isOpen={isOpen}>
                <button onClick={() => setIsOpen(false)} className="modal-close">
                    Close This
                </button>
                My Modal
            </Modal>

            <button className="big-button">
                Create Lobby
            </button>

            <button onClick={() => setIsOpen(true)} className="big-button">
                Join Lobby
            </button>

        </div>
    )
}