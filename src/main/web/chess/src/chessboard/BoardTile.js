import React from 'react';

export default function BoardTile( {tile} ) {

    return (
        <img className="chess-tile" src={tile+".jpg"} alt={tile}></img>
    );
}
