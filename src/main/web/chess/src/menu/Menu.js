import React from 'react';



function updateFenWithAPI(apiCall, updateFen) {
    fetch(apiCall, {method: "GET"})
        .then((response) => response.text())
        .then((fatFen) => fatFen.split(" ")[0])
        .then((response) => updateFen(response));
}

function loadBoard(updater) {
    return () => updateFenWithAPI("http://localhost:3100/api/v0/fen", updater);
}

function stepGame(updater) {
    return () => updateFenWithAPI("http://localhost:3100/api/v0/increment", updater);
}

function restartGame(updater) {
    return () => updateFenWithAPI("http://localhost:3100/api/v0/reset", updater);
}

export default function Menu( {updater}) {
    return (
        <>
            <button onClick={loadBoard(updater)} className="menu-button">Load Board</button>
            <button onClick={stepGame(updater)} className="menu-button">Step Game</button>
            <button onClick={restartGame(updater)} className="menu-button">Restart Game</button>
        </> 
    );
}
