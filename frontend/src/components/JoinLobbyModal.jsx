import React, { useEffect, useCallback } from "react";
import { useNavigate } from 'react-router-dom';

import Modal from 'components/Modal'

import 'css/JoinLobbyModal.css'

export default function JoinLobbyModal({ isOpen, disable }) {

    const navigate = useNavigate();

    const submit = useCallback((content) => {
        if(!/^[a-z]{7}$/.test(content))
            return;
        
        navigate(`/${content}`);
    },[navigate]);
    
    useEffect(() => {
        const input = document.querySelector("#lobby-code-prompt");
        if(!input) 
            return;
            
        function onKeyup(event) {
            if (event.key === "Enter") {
                submit(input.value)
                console.log('submitting ',input.value)
            }
        }
        input.addEventListener("keyup", onKeyup)
        return () => input.removeEventListener("keyup", onKeyup)
    },[isOpen, submit]);

    return !isOpen ? null : (
        <Modal>
            <button onClick={disable} className="modal-close">X</button>

            <label className="join-modal-title" htmlFor="lobby-code-prompt">
                Enter Lobby Code:
            </label>

            <input type="text" maxLength="7" spellCheck="false" id="lobby-code-prompt" name="lobby-code-prompt"></input>
        </Modal>
    );
}