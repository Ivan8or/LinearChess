import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';

const boardRootElement = document.getElementById('root');
const boardRoot = createRoot(boardRootElement);
boardRoot.render(<App/>);
