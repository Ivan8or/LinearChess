import React, { useState } from 'react';

import InventoryDisplay from '/src/components/game/inventory/InventoryDisplay'
import Modal from '/src/components/common/Modal';
import ShopBoard from './inventory/ShopBoard';

import '/src/css/game/SideBoard.css'

export default function ChessBoard() {

    const [shop, setShop] = useState(false)

    return (
        <>
            <div id='sideboard-spacing'>
                <div id="sideboard-outline">
                    <div><center>Opponent Build</center></div>
                    <InventoryDisplay />
                    <div><center>Your Build</center></div>
                    <InventoryDisplay />
                    <button onClick={() => setShop(true)} className="sideboard-button big-button">Shop</button>
                    <button className="sideboard-button big-button">Ready</button>
                    <div className="sideboard-time">0:45</div>
                </div>
            </div>

            <Modal isOpen={shop} disable={() => setShop(false)} style={{ width: "70dvw", height: "80dvh", opacity: "60%" }}>
                <ShopBoard />
            </Modal>
        </>
    );
}
