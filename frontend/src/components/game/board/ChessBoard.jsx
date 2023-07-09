import React, {useState, useEffect}  from 'react';
import BoardTile from './BoardTile';

import { getBoard } from 'api/v1/lobbies/boards/boards.js'
import extractFen from 'api/util/extractFen.js'

import 'css/game/board/ChessBoard.css'

export default function ChessBoard( {fen} ) {


    const tileKinds = parseFen(extractFen(fen));
    const tileComponents = tileKinds.map((t, i) => <BoardTile key={i} tile={t} />);

    return (
        <div id='chessboard'>
            {tileComponents}
        </div>
    );
}

function parseFen(fen) {
    const expandedFen = [...fen]
        .filter((c) => c !== '/')
        .map((c) => parseInt(c) ? ' '.repeat(parseInt(c)) : c)
        .join('');
    
    return [...expandedFen];
}