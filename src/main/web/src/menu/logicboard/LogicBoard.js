import React, {useState} from 'react';
import LogicTile from './LogicTile';

export default function DragBoard() {
    
    const testTiles = [
        {x:  45, y: 30, width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), content: "", type: "logic-circle"
        , url: "./assets/logic/gems/yellow_hex.jpg"},
        {x: 145, y: 30, width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), content: "", type: "logic-circle"
        , url: "./assets/logic/gems/blue_tri.jpg"},
        {x: 245, y: 30, width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), content: "", type: "logic-circle"
        , url: "./assets/logic/gems/green_dodec.jpg"},
        {x: 345, y: 30, width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), content: "", type: "logic-circle"
        , url: "./assets/logic/gems/pink_quad.jpg"},

        {x: 50, y: 150, width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), content: "", type: "logic-rectangle"
        , url: "./assets/chess/b_b.svg"},
        {x: 150, y: 150, width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), content: "", type: "logic-rectangle"
        , url: "./assets/chess/r_b.svg"},
    ];

    const lockOns = [

        {x:  45, y: 30,  width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), type: "logic-circle"},
        {x: 145, y: 30,  width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), type: "logic-circle"},
        {x: 245, y: 30,  width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), type: "logic-circle"},
        {x: 345, y: 30,  width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), type: "logic-circle"},
        {x: 445, y: 30,  width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), type: "logic-circle"},
        {x: 545, y: 30,  width: getVar("logic-circle-diameter"), height: getVar("logic-circle-diameter"), type: "logic-circle"},

        {x: 50, y: 150,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 150, y: 150,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 250, y: 150,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 350, y: 150,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 450, y: 150,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
        {x: 550, y: 150,  width: getVar("logic-rectangle-width"), height: getVar("logic-rectangle-height"), type: "logic-rectangle"},
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