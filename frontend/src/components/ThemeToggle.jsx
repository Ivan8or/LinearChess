import React, { useState, useEffect } from "react";

const THEME = {
    NAME: 'theme',
    DARK: 'dark',
    LIGHT: 'light'
}

export default function ThemeToggle ({ children }) {

    const [theme, setTheme] = useState(() => {
        const themeCookie = window.localStorage.getItem(THEME.NAME);

        if(themeCookie === null) {
            const preferDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
            return preferDark ? THEME.DARK : THEME.LIGHT
        }
        return themeCookie
    });
    
    useEffect(() => {
        document.documentElement.classList.add(theme === THEME.DARK ? "dark-theme" : "light-theme")
        document.documentElement.classList.remove(theme === THEME.DARK ? "light-theme" : "dark-theme")
        window.localStorage.setItem(THEME.NAME, theme);
    }, [theme])
    
    return (
        <button className="theme-toggle" onClick={() => setTheme(t => t === THEME.DARK ? THEME.LIGHT : THEME.DARK)}>
            {children}
        </button>
    )
}