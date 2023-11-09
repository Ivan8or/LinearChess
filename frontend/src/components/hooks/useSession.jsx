import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useLocalStorage from './useLocalStorage';
import { startSession, getSession } from "/src/api/v1/sessions/sessions";

const SESSION = {
    KEY: "session",
    MESSAGE_VALID: "VALID_SESSION"
}

function newSession(set) {
    startSession().then((session) => set(session.sessionID))
}

function refreshedSession(session, onInvalid) {
    return getSession(session)
        .then((res) => res.message !== SESSION.MESSAGE_VALID ? onInvalid() : session)
}

export default function useSession() {
    const navigate = useNavigate()
    const [cached, setCached] = useLocalStorage(SESSION.KEY, null)

    useEffect(() => {
        refreshedSession(cached, () => newSession(setCached))
    }, [cached, setCached])

    return [cached, () => refreshedSession(cached, () => navigate('/'))]
}
