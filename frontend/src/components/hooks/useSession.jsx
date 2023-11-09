import { useEffect } from 'react';

import useLocalStorage from './useLocalStorage';
import { startSession, getSession } from "api/v1/sessions/sessions";

const SESSION = {
    KEY: "session",
    MESSAGE_VALID: "VALID_SESSION"
}

async function newSession(set) {
    const res = await startSession()
    set(res?.sessionID)
}

async function refreshedSession(session, onInvalid) {
    const res = await getSession(session)
    if(res?.message !== SESSION.MESSAGE_VALID) {
        onInvalid?.()
    }
}



export default function useSession() {
    const [cached, setCached] = useLocalStorage(SESSION.KEY, null)

    useEffect(() => {
        refreshedSession(cached, () => newSession(setCached))
    }, [cached, setCached])

    return [cached, () => refreshedSession(cached)]
}
