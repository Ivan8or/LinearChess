import React from "react";
import ReactDOM from 'react-dom';

import 'css/Modal.css'

export default function Modal({ isOpen, children }) {
    if (!isOpen)
        return null;

    return ReactDOM.createPortal(
        <>
            <div className='overlay'></div>
            <div className='modal'>
                {children}
            </div>
        </>
        , document.getElementById('portal')
    );
}