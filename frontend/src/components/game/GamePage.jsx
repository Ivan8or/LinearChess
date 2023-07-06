import React from 'react';

import MainNavBar from 'components/common/MainNavBar';
import MainFooter from 'components/common/MainFooter';
import ChessBoard from 'components/game/board/ChessBoard';
import SideBoard from 'components/game/SideBoard';

import 'css/game/GamePage.css'

export default function GamePage() {

    return (
        <div className="page-root" id="game-root">
            <MainNavBar />
            <div id="game-left"><ChessBoard /></div>
            <div id="game-right"><SideBoard /></div>
            <MainFooter hidden={true} />
        </div>
    );
}