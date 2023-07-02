import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from "react-router-dom";
import App from './App';

const boardRootElement = document.getElementById('root');
const boardRoot = createRoot(boardRootElement);
boardRoot.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
);
