import React from 'react';
import BoardTile from './BoardTile';


export default function ChessBoard( {fen} ) {

    console.log('rerendered ChessBoard with fen '+fen);
    const tiles = parseFen(fen);
    console.log('tiles: '+tiles)

    return (
        tiles.map(t => <BoardTile /* key={t[1]} */ tile={t} />)
    );
}

function parseFen(fen) {
    const expandedFen = [...fen]
        .filter((c) => c !== '/')
        .map((c) => parseInt(c) ? ' '.repeat(parseInt(c)) : c)
        .join('');
    
    //const tiles = [...expandedFen].map((e,i) => [e,i]);
    return [...expandedFen];
}