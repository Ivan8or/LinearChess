import React, {useState} from 'react';
import ChessBoard from './chessboard/ChessBoard'
import Menu from './menu/Menu';

import getBoardFen from './api/v0/getBoardFen.js'
import extractFen from './api/v0/util/extractFen.js'

export default function App() {

    const [fenState, setFenState] = useState("8/8/8/8/8/8/8/8");
    
    getBoardFen()
        .then((response) => response.text())
        .then(extractFen)
        .then(setFenState);

    const updateFen = (fen) => {
        setFenState(fen);
    }

    return (
        <>
        <div id="board-root">
            <ChessBoard fen={fenState} />
        </div>

        <div id="menu-root">
            <Menu setFenState={ updateFen }  />
        </div>
        
        </>
    );
}

