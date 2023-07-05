import React from 'react';
import { Link } from 'react-router-dom';

import NavBar from 'components/common/NavBar';
import ThemeToggle from 'components/common/ThemeToggle';
import ChessBoard from 'components/game/ChessBoard';
import SideBoard from 'components/game/SideBoard';

import 'css/game/GamePage.css'

export default function GamePage() {

    return (
        <div>
            <NavBar template={"2% 1fr 1fr 1fr 4fr 1fr 2%"} spacing={"1%"} >
                <span></span>
                <Link draggable="false" to="/rules">Rules</Link>
                <a draggable="false" href="https://discord.com">Discord</a>
                <a draggable="false" href="https://donate.com">Donate</a>
                <span></span>
                <ThemeToggle>Theme</ThemeToggle>
            </NavBar>
            <div id="game-root">
                <div id="game-left">
                    <ChessBoard/>
                </div>

                <div id="game-right">
                    <SideBoard/>
                </div>
            </div>
        </div>
    );
}