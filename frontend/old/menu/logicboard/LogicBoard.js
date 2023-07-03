import React from 'react';
import LogicTile from './LogicTile';
import setLogicBoard from '../../api/v0/setLogicBoard.js'

export default function LogicBoard() {

    const testTiles = [
        {shape: "logic-circle", type: "SINGLE", uid: 2, lock: 13,
            url: "./assets/logic/gems/green_dodec.jpg",
            context: {}
        },
        {shape: "logic-circle", type: "SINGLE_INVERTED", uid: 3, lock: 14,
            url: "./assets/logic/gems/pink_quad.jpg",
            context: {}
        },

        {shape: "logic-rectangle", type: "NUM_PIECES", uid: 4, lock: 7,
            url: "./assets/chess/p_w.svg",
            context: {
                "discriminate-types": ["PAWN", "KNIGHT", "ROOK", "BISHOP", "QUEEN"],
                "discriminate-sides": ["WHITE"]
            },
        },
        {shape: "logic-rectangle", type: "NUM_PIECES", uid: 5, lock: 8,
            url: "./assets/chess/p_b.svg",
            context: {
                "discriminate-types": ["PAWN", "KNIGHT", "ROOK", "BISHOP", "QUEEN"],
                "discriminate-sides": ["BLACK"]
            },
        },
    ];

    const lockOns = [
        {x:  50, y: 30, shape: "logic-rectangle", category: "active-evals", slot: 1, uid: 1},
        {x:  200, y: 30, shape: "logic-rectangle", category: "active-evals", slot: 2, uid: 2},
        {x:  350, y: 30, shape: "logic-rectangle", category: "active-evals", slot: 3, uid: 3},
        {x:  45, y: 170, shape: "logic-circle", category: "active-multipliers", slot: 1, uid: 4},
        {x:  195, y: 170, shape: "logic-circle", category: "active-multipliers", slot: 2, uid: 5},
        {x:  345, y: 170, shape: "logic-circle", category: "active-multipliers", slot: 3, uid: 6},

        {x:  20, y: 320, shape: "logic-rectangle", category: "inventory", slot: 4, uid: 7},
        {x:  120, y: 320, shape: "logic-rectangle", category: "inventory", slot: 5, uid: 8},
        {x:  220, y: 320, shape: "logic-rectangle", category: "inventory", slot: 6, uid: 9},
        {x:  320, y: 320, shape: "logic-rectangle", category: "inventory", slot: 7, uid: 10},
        {x:  420, y: 320, shape: "logic-rectangle", category: "inventory", slot: 8, uid: 11},
        {x:  520, y: 320, shape: "logic-rectangle", category: "inventory", slot: 9, uid: 12},
        {x:  15,  y: 460, shape: "logic-circle", category: "inventory", slot: 4, uid: 13},
        {x:  115, y: 460, shape: "logic-circle", category: "inventory", slot: 5, uid: 14},
        {x:  215, y: 460, shape: "logic-circle", category: "inventory", slot: 6, uid: 15},
        {x:  315, y: 460, shape: "logic-circle", category: "inventory", slot: 7, uid: 16},
        {x:  415, y: 460, shape: "logic-circle", category: "inventory", slot: 8, uid: 17},
        {x:  515, y: 460, shape: "logic-circle", category: "inventory", slot: 9, uid: 18},
    ];

    const usedTiles = new Map();
    testTiles.forEach((tile) => usedTiles.set(tile.uid, tile.lock));

    function updateTile(tile, lockId) {
        usedTiles.set(tile.uid, lockId);
        setLogicBoard(buildBoardJson(usedTiles))
            .then(res => console.log(res));
    }

    function buildBoardJson(tilesMap) {
        let toReturn = {
            object: "logicboard",
            version: "1.0.0",
            "active-evals": [],
            "active-multipliers": [],
            inventory: []
        }

        Array.from( tilesMap ).forEach(([tileUID, lockUID]) => 
            { 
                const nextLock = byUID(lockOns, lockUID);
                const nextTile = byUID(testTiles, tileUID);
                if(nextTile.shape === "logic-rectangle")
                    toReturn["active-evals"].push( { "slot": nextLock.slot, "type": nextTile.type, "context": nextTile.context } );
                else
                    toReturn["active-multipliers"].push( { "slot": nextLock.slot, "type": nextTile.type, "context": nextTile.context } );
            }
        ); 

        return toReturn;
    }



    const lockComponents = lockOns.map((l, i) => <div key={i} className={"logic-lock "+l.shape} style={{
        top: l.y + 'px',
        left: l.x + 'px',
    }}/>);

    const tileComponents = testTiles.map((t, i) => <LogicTile key={i} tile={t} locks={lockOns} callback={updateTile} />);

    return (
        <div className='logic-board'>
            {tileComponents}
            {lockComponents}
        </div>
    );
}

function byUID(objectsList, uid) {
    return objectsList.find(element => element.uid === uid);
}