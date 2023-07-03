import React from "react";
import ReactDOM from 'react-dom';

import 'css/Modal.css'

export default function Modal({ children }) {

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