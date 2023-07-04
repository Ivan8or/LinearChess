
export default function isValidLobby(lobbyId) {
    if(!validCodeFormat(lobbyId))
        return false;

        
    return false;
}

export function validCodeFormat(code) {
    return /^([b-df-hj-np-tv-z][aeiou]){3}[b-df-hj-np-tv-z]$/.test(code);
}