import React, {useState} from 'react';
import ChessBoard from './chessboard/ChessBoard'

export default function App() {

    
    const initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    const [fenState, updateFenState] = useState(initialFen);

    return (
        <ChessBoard fen={fenState}/>
    );
}

