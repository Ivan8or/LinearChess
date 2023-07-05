import React from 'react';
import { Link } from 'react-router-dom';
import ThemeToggle from 'components/common/ThemeToggle';

import 'css/common/MainFooter.css'

export default function MainFooter() {

    return (
        <ul id="main-footer">
            <div>&copy; 2023 LinearChess.com</div>
            <Link draggable="false" to="/rules">Privacy & Cookies</Link>
            <Link draggable="false" to="/rules">Terms of Use</Link>
            <Link draggable="false" to="/rules">Contact</Link>
        </ul>
    );

    // let children = [
    //     <Link draggable="false" to="/rules">Rules</Link>,
    //     <a draggable="false" href="https://discord.com">Discord</a>,
    //     <a draggable="false" href="https://donate.com">Donate</a>,
    //     <ThemeToggle>Theme</ThemeToggle>
    // ]

    // const links = children.map((child,i) =>
    //     <li className="main-footer-element" key={i}> {child} </li>);

    // return (
    //     <ul id="main-footer">
    //         {links}
    //     </ul>
    // );
}