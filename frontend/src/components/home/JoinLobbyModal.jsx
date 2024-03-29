import React, { useState, useEffect, useCallback, useRef } from "react";
import { useNavigate } from 'react-router-dom';

import Modal from '/src/components/common/Modal'

import { validCodeFormat } from '/src/api/util/isValidLobby';
import { getLobby } from "/src/api/v1/lobbies/lobbies";

import '/src/css/home/JoinLobbyModal.css'

export default function JoinLobbyModal({ isOpen, disable }) {

    const navigate = useNavigate();
    const [error, setError] = useState(false);
    const codes = useRef([]);

    const focusPrompt = useCallback(() => {
        setError(false)
        if (!isOpen) return;
    
        const prompt = document.getElementById("lobby-code-prompt");
        prompt.focus?.()
        prompt.select?.()
    }, [isOpen]);
    
    useEffect(focusPrompt, [isOpen, focusPrompt]);

    function submit(event) {
        event.preventDefault();
        const lobbyCode = event.target.elements.code.value
        if(lobbyCode === "") {
            return
        }
        if (!validCodeFormat(lobbyCode) || codes.current.includes(lobbyCode)) {
            setError(true)
            return
        }
        getLobby(null, lobbyCode).then((res) => {
            if (res?.message === "VALID_LOBBY") {
                navigate(`/${lobbyCode}`)
                return
            }
            setError(true)
            codes.current.push(lobbyCode)
        })
    };


    const errorMessage = error ? <div id="invalid-lobby-code">lobby does not exist</div> : null;

    return (
        <Modal isOpen={isOpen} disable={disable}>

            <button onClick={disable} className="join-modal-close">X</button>
            <div className="join-modal-title">Enter Lobby Code:</div>

            <form id="lobby-code-form" onSubmit={submit}>
                <input type="text" onInput={() => setError(false)} maxLength="7" spellCheck="false" autoComplete="off" id="lobby-code-prompt" name="code"></input>
                <input type="submit" value=">" id="lobby-code-submit" />
            </form>
            {errorMessage}

        </Modal>
    );
}