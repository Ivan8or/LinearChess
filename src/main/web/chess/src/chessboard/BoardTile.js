import React from 'react';

export default function BoardTile( {tile} ) {

    if(tile === ' ')
        return <div className="chess-tile"></div>

    const color = tile == tile.toUpperCase() ? 'w' : 'b';
    const filePath = `./assets/chess/${tile}_${color}.png`;


    return (
        <div className="chess-tile">
            <img draggable="false" src={filePath} alt={tile}></img>
        </div>
    );
}
