import React, {useState} from 'react';

export default function BoardTile( {tile} ) {
    
    const [tileState, setTileState] = useState(tile);

    return (
        <div onClick={() => setTileState(tileState)} className="chess-tile"> {tileState} </div>
    );
}
