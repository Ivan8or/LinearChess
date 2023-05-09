import React, {useState} from 'react';
import ChessBoard from './ChessBoard'

export default function App() {

    const initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    const expandedFen = [...initialFen]
        .filter((c) => c !== '/')
        .map((c) => parseInt(c) ? ' '.repeat(parseInt(c)) : c)
        .join('');
    
    const tiles = [...expandedFen].map((e,i) => [e,i]);

    const [tileState, useTileState] = useState(tiles);
    
    return (
        <ChessBoard tiles={tileState}/>
    );
}
