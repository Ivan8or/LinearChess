import React from 'react';

export default function LogicTile( {tile} ) {
    
    return (
        <div className="logic-tile" draggable="true"
        style={{
            width: tile.width + '%',
            height: tile.height + '%',
            left: tile.x + '%',
            top: tile.y + '%'
        }}
        ondrag={onDragTile}
        ></div>
    );
}

function onDragTile() {
    console.log('dragged!!');
}
