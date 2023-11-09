import React, { useState, useEffect } from 'react';

import MainNavBar from '/src/components/common/MainNavBar';
import MainFooter from '/src/components/common/MainFooter';
import ChessBoard from '/src/components/game/board/ChessBoard';
import SideBoard from '/src/components/game/SideBoard';

import '/src/css/game/GamePage.css'
import { getBoard } from '/src/api/v1/lobbies/boards/boards';

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