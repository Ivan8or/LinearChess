import React, { Children } from 'react';

import 'css/common/NavBar.css'

export default function NavBar({ template, spacing, children }) {

    const links = Children.map(children, (child, i) =>
        <li className="nav-element-container" style={{ gridArea: `1 / ${i + 1}` }}>
            {child}
        </li>
    );

    return (
        <ul id="home-navbar" style={{ gridTemplateColumns: template, columnGap: spacing }}>
            {links}
        </ul>
    );
}