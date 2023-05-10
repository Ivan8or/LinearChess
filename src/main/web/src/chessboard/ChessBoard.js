import React from 'react';
import BoardTile from './BoardTile';


export default function ChessBoard( {fen} ) {

    const tileKinds = parseFen(fen);
    const tileComponents = tileKinds.map(t => <BoardTile /* key={t[1]} */ tile={t} />);

    return (
        <div class='chessboard'>
            {tileComponents}
        </div>
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