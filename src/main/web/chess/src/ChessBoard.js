import React from 'react';
import BoardTile from './BoardTile';


export default function ChessBoard( {tiles} ) {

    return (
        tiles.map(t => <BoardTile key={t[1]} tile={t[0]} />)
    );
}
