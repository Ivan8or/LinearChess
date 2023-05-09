import React, {useState} from 'react';
import ChessBoard from './ChessBoard'

export default function App() {

    const [fen, useFen] = useState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
    
    return (
        <ChessBoard fen={fen}/>
    );
}
