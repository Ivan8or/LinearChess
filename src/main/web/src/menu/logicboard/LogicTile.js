import React from 'react';
import Draggable from 'react-draggable';

export default function LogicTile( {tile} ) {
    
    return (

        <Draggable
        axis="both"
        handle=".handle"
        defaultPosition={{x: 0, y: 0}}
        position={null}
        grid={[1,1]}
        scale={1}>
            <div className="logic-tile handle"
            style={{
                width: tile.width + '%',
                height: tile.height + '%',
                left: tile.x + '%',
                top: tile.y + '%'
            }}>AAA</div>
        </Draggable>
      
        
    );
}

function onDragTile() {
    console.log('dragged!!');
}
