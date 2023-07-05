import React from 'react';

import InventoryDisplay from 'components/game/inventory/InventoryDisplay'

import 'css/game/SideBoard.css'

export default function ChessBoard() {

    return (
        <div id='sideboard-spacing'>
            <div id="sideboard-outline">
                <button className="sideboard-button big-button">Ready</button>
                <div><center>Opponent Build</center></div>
                <InventoryDisplay />
                <div><center>Your Build</center></div>
                <InventoryDisplay />
                <button className="sideboard-button big-button">Shop</button>
                <div className="sideboard-time">0:45</div>
            </div>
        </div>
    );
}
