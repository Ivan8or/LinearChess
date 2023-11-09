import React from 'react';

import TileSlot from '/src/components/game/inventory/TileSlot'

import '/src/css/game/inventory/InventoryDisplay.css'

export default function InventoryDisplay() {

    return (
        <ul className='inventory-display'>
            <div className="inventory-display-child inventory-eval-1"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-eval-2"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-eval-3"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-eval-4"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-1"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-2"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-3"> <TileSlot/> </div>
            <div className="inventory-display-child inventory-mult-4"> <TileSlot/> </div>
        </ul>
    );
}
