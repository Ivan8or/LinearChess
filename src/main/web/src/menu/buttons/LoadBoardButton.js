import React, {useState} from 'react';
import getBoardFen from '../../api/v0/getBoardFen.js'
import extractFen from '../../api/v0/util/extractFen.js'

function handler(setFenState, setLoadingState) {
    return () => {
        setLoadingState("true");

        getBoardFen()
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