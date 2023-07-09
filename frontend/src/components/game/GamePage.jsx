import React, { useState, useEffect } from 'react';

import MainNavBar from 'components/common/MainNavBar';
import MainFooter from 'components/common/MainFooter';
import ChessBoard from 'components/game/board/ChessBoard';
import SideBoard from 'components/game/SideBoard';

import 'css/game/GamePage.css'
import { getBoard } from 'api/v1/lobbies/boards/boards';

export default function GamePage( {session, lobby} ) {
    
    const [fen, updateFen] = useState("8/8/8/8/8/8/8/8");

    useEffect(() => {
        getBoard(session, lobby)
            .then(e => e.fen.fen)
            .then(updateFen)
            .catch(e => console.log(e));
    },[])


    return (
        <div className="page-root" id="game-root">
            <MainNavBar />
            <div id="game-left"><ChessBoard fen={fen} /></div>
            <div id="game-right"><SideBoard /></div>
            <MainFooter hidden={true} />
        </div>
    );
}