import React, { useState, useEffect } from 'react';

import '/src/css/game/inventory/ShopBoard.css'

import { viewShop, refreshShop, purchase } from '/src/api/v1/lobbies/shops/shops';
import { resourcePathById } from '/src/api/util/assets';

export default function ShopBoard({ session, lobby }) {

    const [shop, setShop] = useState({})
    const [nextSlot, setNextSlot] = useState(0)

    const pollSpeed = 1000;
    useEffect(() => {
        syncShopWindow(session, lobby, setShop)
        const interval = setInterval(() => syncShopWindow(session, lobby, setShop), pollSpeed);
        return () => clearInterval(interval);
    }, [session, lobby]);

    return (
        <div id="shop-root">
            <div style={{ "color": "red" }} id="shop-wares">WARES
                <div style={{ "color": "black" }}>
                    <button onClick={() => purchaseFromShopWindow(session, lobby, setShop, 0, nextSlot, setNextSlot)} >
                        Purchase
                    </button>
                    <img width="60" height="60" draggable="false" src={resourcePathById(getItem(shop, 0))} alt={resourcePathById(getItem(shop, 0))}></img>
                </div>
                <div style={{ "color": "black" }}>
                    <button onClick={() => purchaseFromShopWindow(session, lobby, setShop, 1, nextSlot, setNextSlot)} >
                        Purchase
                    </button>
                    <img width="60" height="60" draggable="false" src={resourcePathById(getItem(shop, 1))} alt={resourcePathById(getItem(shop, 1))}></img>
                </div>
                <div style={{ "color": "black" }}>
                    <button onClick={() => purchaseFromShopWindow(session, lobby, setShop, 2, nextSlot, setNextSlot)} >
                        Purchase
                    </button>
                    <img width="60" height="60" draggable="false" src={resourcePathById(getItem(shop, 2))} alt={resourcePathById(getItem(shop, 2))}></img>
                </div>
            </div>
            <div style={{ "color": "red" }} id="shop-stats">STATS
                <div style={{ "color": "black" }}>
                    Gold: {JSON.stringify(shop?.gold)}
                </div>

                <button onClick={() => restockShopWindow(session, lobby, setShop)} >
                    Refresh Shop
                </button>

            </div>
            <div style={{ "color": "red" }} id="shop-inv">INV</div>
        </div>
    );
}


function getItem(shop, index) {
    return shop?.wares?.items?.[index]?.item;
}

function syncShopWindow(session, lobby, setShop) {
    viewShop(session, lobby)
        .then(res => {
            setShop(res.shop)
        })
        .catch(e => navigate('/'))
}

async function restockShopWindow(session, lobby, setShop) {
    await refreshShop(session, lobby)
    await syncShopWindow(session, lobby, setShop)
}

async function purchaseFromShopWindow(session, lobby, setShop, indexFrom, invslot, setNextSlot) {
    setNextSlot(invslot + 1)
    await purchase(session, lobby, indexFrom + 500, invslot + 100)
    await syncShopWindow(session, lobby, setShop)
}
