import React from 'react';

import InventoryDisplay from 'components/game/inventory/InventoryDisplay'

import 'css/game/SideBoard.css'

export default function ChessBoard() {

    return (
        <div id='sideboard'>
            <button className="sideboard-button big-button">Shop</button>
            <button className="sideboard-button big-button">Start</button>
            <div><center>Opponent Build</center></div>
            <InventoryDisplay/>
            <div><center>Your Build</center></div>
            <InventoryDisplay/>
        </div>
    );
}
