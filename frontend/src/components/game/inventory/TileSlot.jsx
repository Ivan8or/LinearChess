import React from 'react';

import { itemResourcePath } from '/src/api/util/assets';

import '/src/css/game/inventory/TileSlot.css'

export default function TileSlot( {item} ) {

    const timeImage = <img width="101%" height="101%" src={itemResourcePath(item)}></img>;
    return (
        <div className='tile-slot'>
            {item?.id != undefined ? timeImage : ""}
        </div>
    );
}
