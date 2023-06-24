import React, {useState} from 'react';
import LogicTile from './LogicTile';

export default function DragBoard() {
    
    const testTiles = [
        //{x: 10, y: 10, width: 40, height: 20, content: "AAA"},
        {x: 10, y: 60, width: 40, height: 20, content: "BBB"}
    ];

    const lockOns = [
        {x: 50, y: 10, width: 40, height: 20, type: "rectangle"},
        {x: 50, y: 150, width: 40, height: 20, type: "rectangle"}
    ];
    
    const [tilesState, setTilesState] = useState(testTiles);

    const tileComponents = tilesState.map((t, i) => <LogicTile key={i} tile={t} locks={lockOns} />);

    return (
        <div className='logic-board'>
            {tileComponents}
        </div>
    );
}