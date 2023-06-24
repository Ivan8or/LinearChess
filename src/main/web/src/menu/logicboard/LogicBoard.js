import React, {useState} from 'react';
import LogicTile from './LogicTile';

export default function DragBoard() {
    
    const testTiles = [
        {x: 10, y: 110, width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), content: ""}
    ];

    const lockOns = [
        {x: 450, y: 10,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 200, y: 60,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 10, y: 110,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"}
    ];

    const lockComponents = lockOns.map((l, i) => <div key={i} className={"logic-lock "+l.type} style={{
        top: l.y + 'px',
        left: l.x + 'px',
    }}/>);
    const tileComponents = testTiles.map((t, i) => <LogicTile key={i} tile={t} locks={lockOns} />);

    return (
        <div className='logic-board'>
            {tileComponents}
            {lockComponents}
        </div>
    );
}

function getVar(value) {
    return parseInt(getComputedStyle(document.body).getPropertyValue('--'+value));
}