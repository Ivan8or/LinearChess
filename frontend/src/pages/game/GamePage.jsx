import React from 'react';
import { useParams } from "react-router-dom"

import ChessBoard from 'chessboard/ChessBoard'
import Menu from 'menu/Menu';


import 'css/variables.css'
import './GamePage.css'

export default function GamePage() {

    const { id } = useParams()
    return (
        <>
            <div>
                {"id is:" + id}
            </div>
            <div id="board-root">
                <ChessBoard />
            </div>

            <div id="menu-root">
                <Menu />
            </div>
        </>
    );
}