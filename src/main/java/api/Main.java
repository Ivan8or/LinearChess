package api;

import chess.ChessGame;
import chess.agent.impl.AlphaBetaAgent;
import chess.agent.ChessAgent;
import chess.board.LMove;
import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.*;
import chess.eval.impl.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(3100);
        ChessGame game = new ChessGame();
        game.setWhiteAgent(simpleAgent(LSide.WHITE));
        game.setBlackAgent(simpleAgent(LSide.BLACK));

        registerV0(game);
    }

    public static void registerV0(ChessGame game) {

        get("/api/v0/fen", "GET", (request, response) ->
            game.getBoard().getFen()
        );

        get("/api/v0/moves", "GET", (request, response) ->
                Arrays.toString(game.getBoard().legalMoves().toArray())
        );

        get("/api/v0/increment", "GET", (request, response) -> {
            System.out.println(game.getBoard().getSideToMove()+" goes!");
            game.increment();
            return game.getBoard().getFen();
        });

        get("/api/v0/reset", "GET", (request, response) -> {
            game.resetBoard();
            return game.getBoard().getFen();
        });

        get("/api/v0/move/:move", "GET", (request, response) -> {
            String moveString = request.params(":move");

            Optional<LMove> move = game.getBoard().legalMoves().stream()
                    .filter(s -> s.toString().equals(moveString))
                    .findFirst();

            if(!move.isPresent())
                return "bad-move";

            return game.getBoard().getFen();
        });
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
