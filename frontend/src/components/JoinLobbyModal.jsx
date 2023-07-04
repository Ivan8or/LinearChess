import React, { useState, useEffect, useCallback, useRef } from "react";
import { useNavigate } from 'react-router-dom';

import Modal from 'components/Modal'

import { validCodeFormat } from 'api/util/isValidLobby';
import { getLobby } from "api/v1/lobbies/lobbies";

import 'css/JoinLobbyModal.css'

export default function JoinLobbyModal({ isOpen, disable }) {

    const navigate = useNavigate();
    const [error, setError] = useState(false);
    const codes = useRef([]);

    const focusPrompt = useCallback(() => {
        setError(false)
        if (!isOpen)
            return;
            
        const prompt = document.getElementById("lobby-code-prompt");
        prompt.focus?.()
        prompt.select?.()
    }, [isOpen]);
    useEffect(focusPrompt, [isOpen, focusPrompt]);

    function submit(event) {
        event.preventDefault();
        const content = event.target.elements.code.value
        if(content === "") {
            return
        }
        if (!validCodeFormat(content) || codes.current.includes(content)) {
            setError(true)
            return
        }
        getLobby(content).then((res) => {
            if (res?.message === "VALID_LOBBY") {
                navigate(`/${content}`)
                return
            }
            setError(true)
            codes.current.push(content)
        })
    };

    useEffect(() => {
        const keyDownHandler = event => {
            if (event.key === 'Escape') {
                event.preventDefault();
                disable()
            }
        };
        document.addEventListener('keydown', keyDownHandler);
        return () => document.removeEventListener('keydown', keyDownHandler)
    }, [disable]);

    if (!isOpen) {
        return null;
    }

    const errorMessage = error ? <div id="invalid-lobby-code">lobby does not exist</div> : null;

    return (
        <Modal>
            <button onClick={disable} className="modal-close">X</button>

            <div className="join-modal-title">
                Enter Lobby Code:
            </div>

            <form id="lobby-code-form" onSubmit={submit}>
                <input type="text" onInput={() => setError(false)} maxLength="7" spellCheck="false" autoComplete="off" id="lobby-code-prompt" name="code"></input>
                <input type="submit" value=">" id="lobby-code-submit" />
            </form>
            {errorMessage}

        </Modal>
    );
}