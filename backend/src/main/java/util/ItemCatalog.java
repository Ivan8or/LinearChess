package util;

import chess.board.LPieceType;
import chess.board.LSide;
import chess.eval.ChessEval;
import chess.eval.impl.*;

import java.util.Optional;
import java.util.Random;

public class ItemCatalog {

    public static Optional<ChessEval> build(int evalId, int multiplierId, LSide ownerSide) {
        Optional<ChessEval> base = eval(evalId, ownerSide);
        if(base.isEmpty())
            return Optional.empty();

        ChessEval result = new WeightedEval(base.get(), multiplier(multiplierId));
        return Optional.of(result);
    }

    public static Optional<ChessEval> eval(int id, LSide ownerSide) {
        switch(id) {
            case 1000: return Optional.of(RANDOM());

            case 1001: return Optional.of(WHITE_PAWNS_ALIVE(ownerSide));
            case 1002: return Optional.of(WHITE_BISHOPS_ALIVE(ownerSide));
            case 1003: return Optional.of(WHITE_KNIGHTS_ALIVE(ownerSide));
            case 1004: return Optional.of(WHITE_ROOKS_ALIVE(ownerSide));
            case 1005: return Optional.of(WHITE_QUEENS_ALIVE(ownerSide));
            case 1006: return Optional.of(BLACK_PAWNS_ALIVE(ownerSide));
            case 1007: return Optional.of(BLACK_BISHOPS_ALIVE(ownerSide));
            case 1008: return Optional.of(BLACK_KNIGHTS_ALIVE(ownerSide));
            case 1009: return Optional.of(BLACK_ROOKS_ALIVE(ownerSide));
            case 1010: return Optional.of(BLACK_QUEENS_ALIVE(ownerSide));
            
            case 1011: return Optional.of(WHITE_PAWNS_ACROSS(ownerSide));
            case 1012: return Optional.of(WHITE_BISHOPS_ACROSS(ownerSide));
            case 1013: return Optional.of(WHITE_KNIGHTS_ACROSS(ownerSide));
            case 1014: return Optional.of(WHITE_ROOKS_ACROSS(ownerSide));
            case 1015: return Optional.of(WHITE_QUEENS_ACROSS(ownerSide));
            case 1016: return Optional.of(BLACK_PAWNS_ACROSS(ownerSide));
            case 1017: return Optional.of(BLACK_BISHOPS_ACROSS(ownerSide));
            case 1018: return Optional.of(BLACK_KNIGHTS_ACROSS(ownerSide));
            case 1019: return Optional.of(BLACK_ROOKS_ACROSS(ownerSide));
            case 1020: return Optional.of(BLACK_QUEENS_ACROSS(ownerSide));
            
            case 1021: return Optional.of(WHITE_PAWNS_MIDDLE(ownerSide));
            case 1022: return Optional.of(WHITE_BISHOPS_MIDDLE(ownerSide));
            case 1023: return Optional.of(WHITE_KNIGHTS_MIDDLE(ownerSide));
            case 1024: return Optional.of(WHITE_ROOKS_MIDDLE(ownerSide));
            case 1025: return Optional.of(WHITE_QUEENS_MIDDLE(ownerSide));
            case 1026: return Optional.of(BLACK_PAWNS_MIDDLE(ownerSide));
            case 1027: return Optional.of(BLACK_BISHOPS_MIDDLE(ownerSide));
            case 1028: return Optional.of(BLACK_KNIGHTS_MIDDLE(ownerSide));
            case 1029: return Optional.of(BLACK_ROOKS_MIDDLE(ownerSide));
            case 1030: return Optional.of(BLACK_QUEENS_MIDDLE(ownerSide));

            case 1100: return Optional.of(DRAW_GAME());
            case 1101: return Optional.of(WHITE_WINS_GAME(ownerSide));
            case 1102: return Optional.of(BLACK_WINS_GAME(ownerSide));

            default: return Optional.empty();
        }
    }

    public static double multiplier(int id) {
        switch(id) {
            case 2000: return 1.5;
            case 2001: return 2;
            case 2002: return 2.5;
            case 2003: return 3;

            case 2100: return -1.5;
            case 2101: return -2;
            case 2102: return -2.5;
            case 2103: return -3;

            default: return 1;
        }
    }
    
    private static ChessEval RANDOM() {
        return new RandomEval(new Random());
    }
    
    private static ChessEval WHITE_PAWNS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.PAWN, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_BISHOPS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.BISHOP, LSide.WHITE);

        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_KNIGHTS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.KNIGHT, LSide.WHITE);

        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_ROOKS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.ROOK, LSide.WHITE);

        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_QUEENS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.QUEEN, LSide.WHITE);

        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_PAWNS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.PAWN, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_BISHOPS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.BISHOP, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_KNIGHTS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.KNIGHT, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_ROOKS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.ROOK, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_QUEENS_ALIVE(LSide ownerSide) {
        ChessEval toReturn = new NumPiecesEval(LPieceType.QUEEN, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }

    private static ChessEval WHITE_PAWNS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.PAWN, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_BISHOPS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.BISHOP, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_KNIGHTS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.KNIGHT, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_ROOKS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.ROOK, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_QUEENS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.QUEEN, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_PAWNS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.PAWN, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_BISHOPS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.BISHOP, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_KNIGHTS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.KNIGHT, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_ROOKS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.ROOK, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_QUEENS_ACROSS(LSide ownerSide) {
        ChessEval toReturn = new DistanceAcrossEval(LPieceType.QUEEN, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }

    private static ChessEval WHITE_PAWNS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.PAWN, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_BISHOPS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.BISHOP, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_KNIGHTS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.KNIGHT, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_ROOKS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.ROOK, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_QUEENS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.QUEEN, LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_PAWNS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.PAWN, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_BISHOPS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.BISHOP, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_KNIGHTS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.KNIGHT, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_ROOKS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.ROOK, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval BLACK_QUEENS_MIDDLE(LSide ownerSide) {
        ChessEval toReturn = new DistanceToMidEval(LPieceType.QUEEN, LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }

    private static ChessEval DRAW_GAME() {
        ChessEval toReturn = new GameDrawsEval();
        return toReturn;
    }
    private static ChessEval BLACK_WINS_GAME(LSide ownerSide) {
        ChessEval toReturn = new GameEndsEval(LSide.BLACK);
        
        if(ownerSide == LSide.BLACK)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
    private static ChessEval WHITE_WINS_GAME(LSide ownerSide) {
        ChessEval toReturn = new GameEndsEval(LSide.WHITE);
        
        if(ownerSide == LSide.WHITE)
            return toReturn;
        return new WeightedEval(toReturn, -1);
    }
}
