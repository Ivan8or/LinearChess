import React, { useState, useEffect} from "react";

export default function ThemeToggle ({ children }) {

    const [dark, setDark] = useState(false);

    useEffect(() => {
        let initialTheme = JSON.parse(window.localStorage.getItem('dark'))
        if(initialTheme == null) {
            initialTheme = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
            document.documentElement.classList.add(initialTheme ? "dark-theme" : "light-theme")
        }
        setDark(initialTheme);
    }, []);
    
    useEffect(() => {
        const tog = document.documentElement.classList.toggle("dark-theme")
        document.documentElement.classList.toggle("light-theme", !tog)
        window.localStorage.setItem('dark', JSON.stringify(dark));
    }, [dark])
    
    return (
        <button className="theme-toggle" onClick={() => setDark(d => !d)}>
            {children}
        </button>
    )
}