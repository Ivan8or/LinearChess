const API_ENDPOINT = "/api/v0/logicboard";
const URL = import.meta.env.VITE_API_URL + API_ENDPOINT;

export default function setLogicBoard(logicboard) {
    if(import.meta.env.NODE_ENV !== 'production' && import.meta.env.VITE_DUMMY_API_CALLS === "true")
        return new Promise((res, err) => res( {success: "true"} ));

    return fetch(URL, 
            {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify( logicboard )
            }
        ).then((response) => response.text());
}