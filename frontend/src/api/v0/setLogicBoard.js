const API_ENDPOINT = "/api/v0/logicboard";
const URL = process.env.REACT_APP_API_URL + API_ENDPOINT;

export default function setLogicBoard(logicboard) {
    if(process.env.NODE_ENV !== 'production' && process.env.REACT_APP_DUMMY_API_CALLS === "true")
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