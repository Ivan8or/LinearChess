import React, { useState } from 'react';

import InventoryDisplay from '/src/components/game/inventory/InventoryDisplay'
import Modal from '/src/components/common/Modal';
import ShopBoard from './inventory/ShopBoard';

import '/src/css/game/SideBoard.css'

export default function SideBoard( {time, session, lobby, inv, side} ) {

    const [shop, setShop] = useState(false)

    const minutesTime = Math.floor(time / 60000)
    const secondsTime = Math.floor(time / 1000) % 60
    const timeString = `${minutesTime}:${secondsTime < 10 ? 0 : ''}${secondsTime}`

    return (
        <>
            <div id='sideboard-spacing'>
                <div id="sideboard-outline">
                    <div><center>Opponent Previous Phase Build ({side == "BLACK" ? "WHITE" : "BLACK"})</center></div>
                    <InventoryDisplay inv={inv[1]} />
                    <div><center>Your Previous Phase Build ({side})</center></div>
                    <InventoryDisplay inv={inv[0]} />
                    <button onClick={() => setShop(true)} className="sideboard-button big-button">Shop</button>
                    <button className="sideboard-button big-button">Ready</button>
                    <div className="sideboard-time"> {timeString} </div>
                </div>
            </div>

            <Modal isOpen={shop} disable={() => setShop(false)} style={{ width: "70dvw", height: "80dvh", opacity: "60%" }}>
                <ShopBoard session={session} lobby={lobby} />
            </Modal>
        </>
    );
}
