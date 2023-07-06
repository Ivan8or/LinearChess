import { useState, useEffect } from 'react';

function getValue(key, initial) {
    const saved = JSON.parse(localStorage.getItem(key))
    if(saved) return saved
    return initial instanceof Function ? initial() : initial
}

export default function useLocalStorage(key, initial) {
    const [value, setValue] = useState(() => getValue(key, initial))
    useEffect(() => window.localStorage.setItem(key, JSON.stringify(value)), [key, value])
    return [value, setValue]
}
