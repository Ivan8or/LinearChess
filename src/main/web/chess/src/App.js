import React, {useState} from 'react';
import ChessBoard from './chessboard/ChessBoard'
import Menu from './menu/Menu';

export default function App() {

    const [fenState, setFenState] = useState("");

    const updateFen = (fen) => {
        setFenState(fen);
    }

    return (
        <>
        <div id="board-root">
            <ChessBoard fen={fenState} />
        </div>

        <div id="menu-root">
            <Menu updater={ updateFen }  />
        </div>
        
        </>
    );
}

