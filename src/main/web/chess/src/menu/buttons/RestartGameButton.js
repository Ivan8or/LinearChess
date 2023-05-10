import React, {useState} from 'react';


const API_ENDPOINT = "http://localhost:3100/api/v0/reset";


function handler(setFenState, setLoadingState) {
    return () => {
        setLoadingState("true");

        fetch(API_ENDPOINT, {method: "GET"})
            .then((response) => response.text())
            .then((fatFen) => fatFen.split(" ")[0])
            .then((response) => setFenState(response))
            .then(() => setLoadingState("false"));
    }
}

export default function RestartGameButton( {setFenState} ) {

    const [loadingState, setLoadingState] = useState("false");

    return (
        <button onClick={handler(setFenState, setLoadingState)} className="menu-button" loading={loadingState} >Restart Game</button>
    );
}