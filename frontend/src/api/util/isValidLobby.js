
export function validCodeFormat(code) {
    return /^([b-df-hj-np-tv-z][aeiou]){3}[b-df-hj-np-tv-z]$/.test(code);
}