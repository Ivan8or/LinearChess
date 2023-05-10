import React, {useState} from 'react';
import restartGame from '../../api/v0/restartGame.js'
import extractFen from '../../api/v0/util/extractFen.js'

function handler(setFenState, setLoadingState) {
    return () => {
        setLoadingState("true");

        restartGame()
            .then((response) => response.text())
            .then(extractFen)
            .then(setFenState)
            .finally(() => setLoadingState("false"));
    }
}

export default function RestartGameButton( {setFenState} ) {

    const [loadingState, setLoadingState] = useState("false");

    return (
        <button onClick={handler(setFenState, setLoadingState)} className="menu-button" loading={loadingState} >Restart Game</button>
    );
}