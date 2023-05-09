import React from 'react';
import BoardTile from './BoardTile';


export default function ChessBoard( {fen} ) {

    const tiles = [...fen]
        .filter((c) => c !== '/')
        .map((c) => parseInt(c) ? ' '.repeat(parseInt(c)) : c)
        .join('');

    return (
        [...tiles].map(t => <BoardTile key={t} tile={t} />)
    );
}
