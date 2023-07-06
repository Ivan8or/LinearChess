import React, { useEffect } from "react";
import ReactDOM from 'react-dom';

import 'css/common/Modal.css'


function turnOffModal(disable) {
    const keyDownHandler = event => {
        if (event.key === 'Escape') {
            event.preventDefault();
            disable()
        }
    };
    document.addEventListener('keydown', keyDownHandler);
    return () => document.removeEventListener('keydown', keyDownHandler)
}

export default function Modal({ isOpen, disable, style, children }) {

    useEffect(() => turnOffModal(disable), [disable]);
    if (!isOpen) return null;

    const contentStyle = {
        width: style?.width || "60dvw",
        height: style?.height || "60dvh"
    }
    const overlayStyle = {
        backgroundColor: `rgba(0, 0, 0, ${style?.opacity || "60%"})`
    }

    return ReactDOM.createPortal(
        <>
            <div id='modal-overlay' style={overlayStyle}></div>
            <div id='modal-content' style={contentStyle}>
                {children}
            </div>
        </>
        ,document.getElementById('portal'));
}