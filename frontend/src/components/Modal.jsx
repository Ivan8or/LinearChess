import React from "react";
import ReactDOM from 'react-dom';

import 'css/Modal.css'

export default function Modal({ isOpen, close, children }) {
    if (!isOpen)
        return null;

    return ReactDOM.createPortal(
        <>
            <div className='overlay'></div>

            <div className='modal'>
                <button onClick={close}>
                    close me
                </button>
                {children}
            </div>
        </>
        , document.getElementById('portal')
    );
}