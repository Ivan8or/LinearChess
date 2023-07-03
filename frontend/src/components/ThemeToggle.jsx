import React, { useState, useEffect} from "react";

export default function ThemeToggle ({ children }) {

    const [dark, setDark] = useState(() => {
        const val = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
        document.documentElement.classList.add(val ? "dark-theme" : "light-theme")
        return val
    });

    useEffect(() => {
        document.documentElement.classList.toggle("dark-theme")
        document.documentElement.classList.toggle("light-theme")
    }, [dark])
    
    return (
        <button onClick={() => setDark(d => !d)}>
            {children}
        </button>
    )
}