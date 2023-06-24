import React, {useState} from 'react';
import DraggableCore from 'react-draggable';


export default function LogicTile({ tile, locks }) {

    const [positionState, setPositionState] = useState({ x: tile.x, y: tile.y });
    const [safePositionState, setSafePositionState] = useState({ x: tile.x, y: tile.y });

    function handleDrag(e, data) {
        setPositionState({ x: positionState.x + data.deltaX, y: positionState.y + data.deltaY });
    }

    function handleDragStop(e, data) {
        const curPosition = {x: positionState.x, y: positionState.y, width: tile.width, height: tile.height }

        for(const lockId in locks) {
            const lock = locks[lockId];
            
            if(lock.type == tile.type && inBounds(curPosition, lock)) {
                setPositionState({x: lock.x, y: lock.y});
                setSafePositionState({x: lock.x, y: lock.y});
                return;
            }
        }
        setPositionState(safePositionState);
    }

    return (
        <DraggableCore
            handle=".handle"

            onStart={() => {}}
            onDrag={handleDrag}
            onStop={handleDragStop}
            position={positionState}

            scale={1}>
            <div className={"logic-tile handle "+tile.type}> {tile.content} </div>
        </DraggableCore>
    );
}

function inBounds(tile, bound) {
    const tileCenter = getCenter(tile);
    return tileCenter.x > bound.x 
        && tileCenter.x < (bound.x + bound.width) 
        && tileCenter.y > bound.y 
        && tileCenter.y < (bound.y + bound.height);
}

function getCenter(tile) {
    return {
        x: Math.round(tile.x + tile.width / 2),
        y: Math.round(tile.y + tile.height / 2)
    };
}