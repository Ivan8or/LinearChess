import React, {useState} from 'react';
import DraggableCore from 'react-draggable';


export default function LogicTile({ tile, locks, callback }) {

    const activeLock = lockByUID(locks, tile.lock);

    const [positionState, setPositionState] = useState({ x: activeLock.x, y: activeLock.y });
    const [safePositionState, setSafePositionState] = useState({ x: activeLock.x, y: activeLock.y });

    function handleDrag(e, data) {
        setPositionState({ x: positionState.x + data.deltaX, y: positionState.y + data.deltaY });
    }

    function handleDragStop(e, data) {
        const curPosition = {x: positionState.x, y: positionState.y, shape: tile.shape}

        for(const lockId in locks) {
            const lock = locks[lockId];
            
            if(lock.shape === tile.shape && inBounds(curPosition, lock)) {
                setPositionState({x: lock.x, y: lock.y});
                setSafePositionState({x: lock.x, y: lock.y});
                callback(tile, lock.uid);
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
            <div className={"logic-tile handle "+tile.shape}> 
                <img draggable="false" src={tile.url} alt={tile.url}></img>
            </div>
        </DraggableCore>
    );
}

function lockByUID(locks, uid) {
    return locks.find(element => element.uid === uid);
}

function inBounds(tile, bound) {
    const tileCenter = getCenter(tile);
    return tileCenter.x > bound.x 
        && tileCenter.x < (bound.x + getVar(bound.shape, "logic-width")) 
        && tileCenter.y > bound.y 
        && tileCenter.y < (bound.y + getVar(bound.shape, "logic-height"));
}

function getCenter(tile) {
    return {
        x: Math.round(tile.x + getVar(tile.shape, "logic-width") / 2),
        y: Math.round(tile.y + getVar(tile.shape, "logic-height") / 2)
    };
}

function getVar(type, value) {
    return parseInt(getComputedStyle(document.querySelector("."+type)).getPropertyValue('--'+value));
}