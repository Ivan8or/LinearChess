import React from 'react';
import LoadBoardButton from './buttons/LoadBoardButton'
import StepGameButton from './buttons//StepGameButton'
import RestartGameButton from './buttons/RestartGameButton'

export default function Menu( {setFenState}) {
    return (
        <>
            <LoadBoardButton setFenState={ setFenState }/>
            <StepGameButton setFenState={ setFenState }/>
            <RestartGameButton setFenState={ setFenState }/>
        </> 
    );
}
