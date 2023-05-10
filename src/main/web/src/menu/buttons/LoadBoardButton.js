import React, {useState} from 'react';
import requestFen from '../../api/v0/requestFen.js'
import extractFen from '../../api/v0/util/extractFen.js'

function handler(setFenState, setLoadingState) {
    return () => {
        setLoadingState("true");

        requestFen()
            .then((response) => response.text())
            .then(extractFen)
            .then(setFenState)
            .finally(() => setLoadingState("false"));
    }
}

export default function LoadBoardButton( {setFenState} ) {
    
    const [loadingState, setLoadingState] = useState("false");

    return (
        <button onClick={handler(setFenState, setLoadingState)} className="menu-button" loading={loadingState} >Load Board</button>
    );
}