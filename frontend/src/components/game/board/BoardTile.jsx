import React from 'react';


import '/src/css/game/board/ChessBoard.css'

export default function BoardTile( {tile} ) {

    if(tile === ' ')
        return <div className="chess-tile"></div>;

    const ROOT_PATH = './assets/chess';
    const color = tile === tile.toUpperCase() ? 'w' : 'b';
    const lowercaseTile = tile.toLowerCase();
    const filePath = `${ROOT_PATH}/${lowercaseTile}_${color}.svg`;


    return (
        <div className="chess-tile">
            <img draggable="false" src={filePath} alt={tile}></img>
        </div>
    );
}
