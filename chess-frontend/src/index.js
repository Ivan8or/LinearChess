import React from 'react';
import ReactDOM from 'react-dom/client';
import { Chessboard } from "react-chessboard";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
<div>
  <Chessboard
    id="BasicBoard"
    position={'rnbqkbnr/ppp2ppp/8/8/5P2/1B1N4/PPPP1BPP/R2QK1NR'}
    showBoardNotation={false}
    arePiecesDraggable={false}
    />
</div>
);
