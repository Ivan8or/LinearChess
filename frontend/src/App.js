// import React, {useState} from 'react';
// import ChessBoard from './chessboard/ChessBoard'
// import Menu from './menu/Menu';

// import getBoardFen from './api/v0/getBoardFen.js'
// import extractFen from './api/v0/util/extractFen.js'

// export default function App() {

//     const [fenState, setFenState] = useState("8/8/8/8/8/8/8/8");
    
//     getBoardFen()
//         .then(extractFen)
//         .then(setFenState)
//         .catch(e => console.log(e));

//     const updateFen = (fen) => {
//         setFenState(fen);
//     }

//     return (
//         <>
//         <div id="board-root">
//             <ChessBoard fen={fenState} />
//         </div>

//         <div id="menu-root">
//             <Menu setFenState={ updateFen }  />
//         </div>
//         </>
//     );
// }

import React, { useState, useEffect } from "react";
import { Link, BrowserRouter as Router, Route } from "react-router-dom";

const HomePage = () => {
  return <>Home Page</>;
};

const App = () => {
  return (
    <>
      <Router>
        <Route exact path="/" component={HomePage} />
      </Router>
    </>
  );
};

export default App;