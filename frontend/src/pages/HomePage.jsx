import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import Modal from 'components/Modal';

import 'css/variables.css';
import 'css/HomePage.css';
import NavBar from 'components/NavBar';
import ThemeToggle from 'components/ThemeToggle';

export default function HomePage() {

    const [isOpen, setIsOpen] = useState(false);

    return (
        <div>

            <NavBar template={"2% 1fr 1fr 1fr 60% 1fr 2%"} spacing={"1%"} >
                <span></span>
                {/* <img src="./assets/chess/p_w.svg" alt="logo"></img> */}
                <Link to="/alabama">alabama</Link>
                <Link to="/texas">texas</Link>
                <Link to="/mississipi">mississipi</Link>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>

            <h1 className="title">LinearChess.com</h1>

            <Modal isOpen={isOpen} close={() => setIsOpen(false)}>
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