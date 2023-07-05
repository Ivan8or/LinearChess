import React from "react";
import ReactDOM from 'react-dom';

import 'css/common/Modal.css'

export default function Modal({ children }) {

    return ReactDOM.createPortal(
        <>
            <div id='modal-overlay'></div>
            <div id='modal-content'>
                {children}
            </div>
        </>
        , document.getElementById('portal')
    );
}