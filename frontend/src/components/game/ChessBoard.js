import React, {useState, useEffect}  from 'react';
import BoardTile from './BoardTile';

import getBoardFen from 'api/v0/getBoardFen.js'
import extractFen from 'api/util/extractFen.js'


import 'css/game/ChessBoard.css'

export default function ChessBoard() {

    const [fen, updateFen] = useState("8/8/8/8/8/8/8/8");
    
    useEffect(() => {
        getBoardFen()
            .then(extractFen)
            .then(updateFen)
            .then(() => console.log("got fen"))
            .catch(e => console.log(e));
    },[])

    const tileKinds = parseFen(fen);
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