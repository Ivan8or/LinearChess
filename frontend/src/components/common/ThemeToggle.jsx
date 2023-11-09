import React, { useState, useEffect } from "react";

import '/src/css/common/ThemeToggle.css'

const THEME = {
    KEY: 'theme',
    DARK: 'dark',
    LIGHT: 'light'
}

export default function ThemeToggle ({ children }) {
    const [theme, setTheme] = useState(() => {
        const themeCookie = window.localStorage.getItem(THEME.KEY);

        if(themeCookie === null) {
            const preferDark = window.matchMedia?.('(prefers-color-scheme: dark)').matches
            return preferDark ? THEME.DARK : THEME.LIGHT
        }
        return themeCookie
    });
    
    useEffect(() => {
        document.documentElement.classList.add(theme === THEME.DARK ? "dark-theme" : "light-theme")
        document.documentElement.classList.remove(theme === THEME.DARK ? "light-theme" : "dark-theme")
        window.localStorage.setItem(THEME.KEY, theme);
    }, [theme])
    
    return (
        <button id="theme-toggle" onClick={() => setTheme(t => t === THEME.DARK ? THEME.LIGHT : THEME.DARK)}>
            {children}
        </button>
    )
}