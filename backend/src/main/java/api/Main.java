package api;

import api.v0.APIv0;
import api.v0.impl.*;
import chess.ChessGame;
import chess.agent.impl.AlphaBetaAgent;
import chess.agent.ChessAgent;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;
import chess.eval.impl.*;

import java.util.Random;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        APIv0.registerV0();
    }
}
