import React, {useState} from 'react';
import LogicTile from './LogicTile';

export default function DragBoard () {
    
    const testTiles = [{x: 10, y: 10, width: 40, height: 20}, {x: 100, y: 100, width: 40, height: 20}];
    const [tilesState, setTilesState] = useState(testTiles);

    const tileComponents = tilesState.map((t, i) => <LogicTile key={i} tile={t} />);

    return (
        <div className='logic-board'>
            {tileComponents}
        </div>
    );
}