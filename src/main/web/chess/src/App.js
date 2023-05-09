import React, {useState} from 'react';
import ChessBoard from './chessboard/ChessBoard'
import Menu from './menu/Menu';

export default function App() {

    const initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    const [fenState, updateFenState] = useState(initialFen);

    return (
        <>
        <div id="board-root">
            <ChessBoard  fen={fenState} />
        </div>

        <div id="menu-root">
            <Menu  />
        </div>
        
        </>
    );
}

