import React from 'react';
import { Link } from 'react-router-dom';
import ThemeToggle from 'components/common/ThemeToggle';

import 'css/common/MainNavBar.css'

export default function MainNavBar() {

    let children = [
        <span className="navbar-ignore"></span>,
        <Link draggable="false" to="/rules">Rules</Link>,
        <a draggable="false" href="https://discord.com">Discord</a>,
        <a draggable="false" href="https://donate.com">Donate</a>,
        <span className="navbar-ignore"></span>,
        <ThemeToggle>Theme</ThemeToggle>
    ]

    const links = children.map((child,i) =>
        <li className="main-navbar-element" key={i} style={{ gridArea: `1 / ${i + 1}` }}>{child}</li>);

    return (
        <ul id="main-navbar">
            {links}
        </ul>
    );
}