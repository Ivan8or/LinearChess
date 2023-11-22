import React from 'react';

import TileSlot from '/src/components/game/inventory/TileSlot'

import { resourcePathById } from '/src/api/util/assets';

import '/src/css/game/inventory/InventoryDisplay.css'

export default function InventoryDisplay( {inv} ) {
    const items = inv?.items
    const imgs = items?.map((elem) => {
        return <img width="100" height="100" src={resourcePathById(elem?.item)} alt={resourcePathById(elem?.item)}></img>
    })
    return (
        <ul className='inventory-display'>
            {imgs}
            {/* <div className="inventory-display-child inventory-eval-1"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-eval-2"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-eval-3"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-eval-4"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-1"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-2"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-3"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-4"> <TileSlot/> </div> */}
        </ul>
    );
}
