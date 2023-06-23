import React, {useState} from 'react';
import Draggable from 'react-draggable';


export default function LogicTile({ tile, locks }) {

    const startPosition = { x: tile.x, y: tile.y }
    const [positionState, setPositionState] = useState(startPosition);


    function onStartTile(e, data) {
        setPositionState(null);
        console.log("RESET POSITION STATE");
    }

    function onReleaseTile(e, data) {

        const curPosition = {x: tile.x, y: tile.y, width: tile.width, height: tile.height }

        for(const lock in locks) {
            if(inBounds(curPosition, lock)) {
                setPositionState({x: lock.x, y: lock.y});
                return;
            }
        }
        //setPositionState(startPosition);
    }

    return (
        <Draggable
            axis="both"
            handle=".handle"
            defaultPosition={startPosition}
            //position={positionState}

            onStart={onStartTile}
            onStop={onReleaseTile}
            onDrag={() => {console.log("position state is "+positionState)}}
            //grid={[100, 100]}
            scale={1}>
            <div className="logic-tile handle"
                style={{
                    width: tile.width + '%',
                    height: tile.height + '%'
                }}> {tile.content} </div>
        </Draggable>
    );
}

function inBounds(tile, bound) {
    const tileCenter = getCenter(tile);

    if(bound.x < tileCenter.x || bound.x + bound.width > tileCenter.x)
        return false;
    
    if(bound.y < tileCenter.y || bound.y + bound.height > tileCenter.y)
        return false;

    return true;
}

function getCenter(tile) {
    return {
        x: Math.round(tile.x + tile.width / 2), 
        y: Math.round(tile.y + tile.height / 2)
    };
}