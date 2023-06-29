package api.v0.impl;

import api.APIEndpoint;
import chess.ChessGame;
import chess.agent.ChessAgent;
import chess.agent.impl.AlphaBetaAgent;
import chess.eval.ChessEval;
import chess.eval.builder.JsonParser;
import chess.eval.builder.LogicBoard;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

public class SetPlayerEval extends APIEndpoint {

    final private ChessGame game;

    public SetPlayerEval(ChessGame game) {
        super("/logicboard", HttpMethod.put);
        this.game = game;
    }

    @Override
    public Object handle(Request request, Response response) {
        synchronized(game) {
            String logicboardJson = request.body();
            ChessEval newEval = LogicBoard.parseBoard(JsonParser.parseJsonObject(logicboardJson));
            ChessAgent newAgent = new AlphaBetaAgent(newEval, 2);
            game.setAgent(newAgent, game.getBoard().getSideToMove());
            return "{\"success\":\"true\"}";
        }
    }

}
