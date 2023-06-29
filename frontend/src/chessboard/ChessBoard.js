import React from 'react';
import BoardTile from './BoardTile';

export default function ChessBoard( {fen} ) {

    const tileKinds = parseFen(fen);
    const tileComponents = tileKinds.map((t, i) => <BoardTile key={i} tile={t} />);

    return (
        <div className='chessboard'>
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