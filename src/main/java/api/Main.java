package api;

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
        ChessGame game = new ChessGame();
        game.setWhiteAgent(simpleAgent(LSide.WHITE));
        game.setBlackAgent(simpleAgent(LSide.BLACK));
        registerV0(game);
    }

    public static void registerV0(ChessGame game) {

        String pathV0 = "/api/v0";

        LChessAPI apiV0 = new LChessAPI()
                .withPort(3100)
                .withTimeout(8000)
                .withEndpoint(new GetBoardState(game).withCommonPath(pathV0))
                .withEndpoint(new RestartGame(game).withCommonPath(pathV0))
                .withEndpoint(new StepGame(game).withCommonPath(pathV0))
                .withEndpoint(new GetPossibleMoves(game).withCommonPath(pathV0))
                .withEndpoint(new MakeMove(game).withCommonPath(pathV0))
                .start();
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
        return new AlphaBetaAgent(outerEval, 2);
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
