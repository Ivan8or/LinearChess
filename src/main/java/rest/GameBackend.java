package rest;

import chess.ChessGame;
import chess.agent.AlphaBetaAgent;
import chess.agent.ChessAgent;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.*;
import spark.Spark;

import java.util.Random;

public class GameBackend {

    public static void main(String[] args) {
        Spark.port(3100);
        ChessGame game = new ChessGame();
        game.setWhiteAgent(simpleAgent(LSide.WHITE));
        game.setBlackAgent(simpleAgent(LSide.BLACK));

        Spark.get("/api/v1/fen", "GET",
                (request, response) -> game.getBoard().getFen());

        Spark.get("/api/v1/board", "GET",
                (request, response) -> game.getBoard().toString());

        Spark.get("/api/v1/increment", "GET",
                (request, response) -> "success: " + game.increment());
    }


    public static ChessAgent simpleAgent(LSide side) {

        ChessEval numMyPieces = pieceWeights(side);
        ChessEval numOpPieces = pieceWeights(side.flip());

        DistanceAcrossEval myProgress = new DistanceAcrossEval(
                LPieceType.PAWN, side);

        DistanceAcrossEval opProgress = new DistanceAcrossEval(
                LPieceType.PAWN, side);

        RandomEval rand = new RandomEval(new Random());

        GameEndsEval gameEnds = new GameEndsEval(side);
        GameDrawsEval gameDraws = new GameDrawsEval();

        CumulativeEval outerEval = new CumulativeEval(
                new WeightedEval(gameEnds, 1000),
                new WeightedEval(gameDraws, -100),
                new WeightedEval(numMyPieces, 1),
                new WeightedEval(numOpPieces, -1),
                new WeightedEval(opProgress, -0.5),
                new WeightedEval(myProgress, 0.5),
                new WeightedEval(rand, 0.001)
        );
        return new AlphaBetaAgent(outerEval, 3);
    }

    public static ChessEval pieceWeights(LSide side) {
        NumPiecesEval numPawns = new NumPiecesEval(
                LPieceType.PAWN, side);
        NumPiecesEval numRooks = new NumPiecesEval(
                LPieceType.ROOK, side);
        NumPiecesEval numKnights = new NumPiecesEval(
                LPieceType.KNIGHT, side);
        NumPiecesEval numBishops = new NumPiecesEval(
                LPieceType.BISHOP, side);
        NumPiecesEval numQueens = new NumPiecesEval(
                LPieceType.QUEEN, side);

        return new CumulativeEval(
                new WeightedEval(numPawns, 1),
                new WeightedEval(numRooks, 5),
                new WeightedEval(numKnights, 3),
                new WeightedEval(numBishops, 3),
                new WeightedEval(numQueens, 9)
        );
    }
}
