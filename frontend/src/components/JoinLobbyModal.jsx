import React, { useState, useEffect, useCallback } from "react";
import { useNavigate } from 'react-router-dom';

import Modal from 'components/Modal'

import { validCodeFormat } from 'api/util/isValidLobby';

import 'css/JoinLobbyModal.css'

export default function JoinLobbyModal({ isOpen, disable }) {

    const navigate = useNavigate();
    const [error, setError] = useState(false);

    const focusPrompt = useCallback(() => {
        if (!isOpen)
            return;
        const prompt = document.getElementById("lobby-code-prompt");
        if (prompt !== null) {
            prompt.focus();
            prompt.select();
        }
    }, [isOpen]);

    useEffect(focusPrompt, [isOpen, focusPrompt]);

    const submit = useCallback((event) => {
        event.preventDefault();
        const content = event.target.elements.code.value
        if (!validCodeFormat(content)) {
            setError(true);
            return;
        }

        navigate(`/${content}`);
    }, [navigate, focusPrompt]);

    if (!isOpen) {
        return null;
    }

    const errorMessage = error ? <div id="invalid-lobby-code">lobby does not exist</div> : null;

    return (
        <Modal>
            <button onClick={disable} className="modal-close">X</button>

            <label className="join-modal-title" htmlFor="lobby-code-prompt">
                Enter Lobby Code:
            </label>

            <form id="lobby-code-form" onSubmit={submit}>
                <input type="text" onInput={() => setError(false)} maxLength="7" spellCheck="false" autoComplete="off" id="lobby-code-prompt" name="code"></input>
                <input type="submit" value=">" id="lobby-code-submit" />
            </form>
            {errorMessage}
            
        </Modal>
    );
}