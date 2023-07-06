import React from 'react';
import { Link } from 'react-router-dom';
import ThemeToggle from 'components/common/ThemeToggle';

import 'css/common/MainFooter.css'

export default function MainFooter( {hidden} ) {

    if(hidden === true)
        return <ul id="main-footer"></ul>

    return (
        <ul id="main-footer">
            <div>&copy; 2023 LinearChess.com</div>
            <Link draggable="false" to="/rules">Privacy & Cookies</Link>
            <Link draggable="false" to="/rules">Terms of Use</Link>
            <Link draggable="false" to="/rules">Contact</Link>
        </ul>
    );
}