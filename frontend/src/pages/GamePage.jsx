import React from 'react';
import { useParams } from "react-router-dom"

import 'css/variables.css'
import 'css/GamePage.css'

export default function GamePage() {

    const { id } = useParams()
    return (
        <div>
            {id}
        </div>
    );
}