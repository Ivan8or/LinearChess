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
        console.log("cur position:", curPosition);
        for(const lockId in locks) {
            const lock = locks[lockId];
            
            if(inBounds(curPosition, lock)) {
                setPositionState({x: lock.x, y: lock.y});
                setSafePositionState({x: lock.x, y: lock.y});
                console.log("LOCKING POSITION ");
                return;
            }
        }
        console.log("RETURNING TO ORIGINAL POSITION");
        setPositionState(safePositionState);
    }

    return (
        <DraggableCore
            handle=".handle"

            onStart={() => {}}
            onDrag={handleDrag}
            onStop={handleDragStop}
            position={positionState}
            //grid={[100, 100]}
            scale={1}>
            <div className="logic-tile handle"
                style={{
                    width: tile.width + '%',
                    height: tile.height + '%',
                }}> {tile.content} </div>
        </DraggableCore>
    );
}

function inBounds(tile, bound) {
    const tileCenter = getCenter(tile);
    const boundCenter = getCenter(bound);

    const returnval = getDistance(tileCenter, boundCenter) < 60;
    console.log('returning',returnval);
    return returnval;
}

function getDistance(p1, p2) {
    console.log('getting distance of', p1.x, p1.y, 'to', p2.x, p2.y);
    console.log('result is',Math.sqrt( Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) ));
    return Math.sqrt( Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) );
}

function getCenter(tile) {
    return {
        x: Math.round(tile.x + tile.width / 2), 
        y: Math.round(tile.y + tile.height / 2)
    };
}