import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import Menu from './menu/Menu';

const boardRootElement = document.getElementById('board-root');
const boardRoot = createRoot(boardRootElement);
boardRoot.render(<App/>);


const menuRootElement = document.getElementById('menu-root');
const menuRoot = createRoot(menuRootElement);
menuRoot.render(<Menu/>);
