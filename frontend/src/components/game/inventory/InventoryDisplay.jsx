import React from 'react';

import TileSlot from '/src/components/game/inventory/TileSlot'

import { itemResourcePath } from '/src/api/util/assets';

import '/src/css/game/inventory/InventoryDisplay.css'

export default function InventoryDisplay( {inv} ) {
    const items = inv?.items
    const imgs = items?.map((elem) => {
        return <img width="100" height="100" src={itemResourcePath(elem?.item)}></img>
    })
    return (
        <ul className='inventory-display'>
            <div className="inventory-display-child inventory-eval-1"> <TileSlot item={getItemBySlotID(items, 100)}/> </div>
            <div className="inventory-display-child inventory-eval-2"> <TileSlot item={getItemBySlotID(items, 101)}/> </div>
            <div className="inventory-display-child inventory-eval-3"> <TileSlot item={getItemBySlotID(items, 102)}/> </div>
            <div className="inventory-display-child inventory-eval-4"> <TileSlot item={getItemBySlotID(items, 103)}/> </div>
            <div className="inventory-display-child inventory-mult-1"> <TileSlot item={getItemBySlotID(items, 200)}/> </div>
            <div className="inventory-display-child inventory-mult-2"> <TileSlot item={getItemBySlotID(items, 201)}/> </div>
            <div className="inventory-display-child inventory-mult-3"> <TileSlot item={getItemBySlotID(items, 202)}/> </div>
            <div className="inventory-display-child inventory-mult-4"> <TileSlot item={getItemBySlotID(items, 203)}/> </div>
        </ul>
    );
}

function getItemBySlotID(items, id) {
    return items?.filter((elem) => elem.slot == id)?.[0]?.item
}