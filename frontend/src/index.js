import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from "react-router-dom";
import App from './App';

import 'css/theme.css'

const boardRootElement = document.getElementById('root');
const boardRoot = createRoot(boardRootElement);
boardRoot.render(
    //<React.StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    //</React.StrictMode>
);
