import React, {useState} from 'react';
import stepGame from 'api/v0/stepGame.js'
import extractFen from 'api/util/extractFen.js'

function handler(setFenState, setLoadingState) {
    return () => {
        setLoadingState("true");

        stepGame()
            .then(extractFen)
            .then(setFenState)
            .finally(() => setLoadingState("false"));
    }
}

export default function StepGameButton( {setFenState} ) {
    
    const [loadingState, setLoadingState] = useState("false");

    return (
        <button onClick={handler(setFenState, setLoadingState)} className="menu-button" loading={loadingState} >Step Game</button>
    );
}