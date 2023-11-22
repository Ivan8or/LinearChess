import React, { useState, useEffect } from 'react';

import MainNavBar from '/src/components/common/MainNavBar';
import MainFooter from '/src/components/common/MainFooter';
import ChessBoard from '/src/components/game/board/ChessBoard';
import SideBoard from '/src/components/game/SideBoard';

import '/src/css/game/GamePage.css'
import { getBoard } from '/src/api/v1/lobbies/boards/boards';

export default function GamePage( {session, lobby} ) {
    
    const [fen, updateFen] = useState("8/8/8/8/8/8/8/8");
    const [time, setTime] = useState(0);

    const pollSpeed = 1000;
    useEffect(() => {
        const interval = setInterval(() => {
            getBoard(session, lobby)
            .then(e => {
                updateFen(e.fen.fen)
                setTime(e["time-left"])
            })
            .catch(e => console.log(e));
        }, pollSpeed);

        return () => clearInterval(interval);
    }, []);


    return (
        <div className="page-root" id="game-root">
            <MainNavBar />
            <div id="game-left"><ChessBoard fen={fen} /></div>
            <div id="game-right"><SideBoard time={time}/></div>
            <MainFooter hidden={true} />
        </div>
    );
}