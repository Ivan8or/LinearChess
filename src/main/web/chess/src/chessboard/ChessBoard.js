import React from 'react';
import BoardTile from './BoardTile';


export default function ChessBoard( {fen} ) {

    const tiles = parseFen(fen);

    return (
        tiles.map(t => <BoardTile key={t[1]} tile={t[0]} />)
    );
}

function parseFen(fen) {
    const expandedFen = [...fen]
        .filter((c) => c !== '/')
        .map((c) => parseInt(c) ? ' '.repeat(parseInt(c)) : c)
        .join('');
    
    const tiles = [...expandedFen].map((e,i) => [e,i]);
    return tiles;
}