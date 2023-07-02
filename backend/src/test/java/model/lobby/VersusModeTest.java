package model.lobby;

import chess.board.LSide;
import model.mappings.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(MockitoJUnitRunner.class)
public class VersusModeTest {

    @Mock
    ChessLobby lobby;

    @Test
    public void incrementRound() {
        Session player1 = new Session(UUID.randomUUID());
        Session player2 = new Session(UUID.randomUUID());
        VersusMode game = new VersusMode(player1, player2, lobby);

        Assert.assertEquals(0, game.getRound());

        game.progressRound(400, 400);
        Assert.assertEquals(1, game.getRound());

        try {
            TimeUnit.MILLISECONDS.sleep(300);
            Assert.assertEquals(1, game.getRound());
            Assert.assertEquals(GamePhase.SHOP, game.getPhase());

            TimeUnit.MILLISECONDS.sleep(300);
            Assert.assertEquals(1, game.getRound());
            Assert.assertEquals(GamePhase.PLAY, game.getPhase());

            TimeUnit.MILLISECONDS.sleep(300);
            Assert.assertEquals(2, game.getRound());
            Assert.assertEquals(GamePhase.SHOP, game.getPhase());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void isGameOver() {
        Session player1 = new Session(UUID.randomUUID());
        Session player2 = new Session(UUID.randomUUID());
        VersusMode game = new VersusMode(player1, player2, lobby);

        Assert.assertFalse(game.isOver());
    }

    @Test
    public void playerSides() {
        Session player1 = new Session(UUID.randomUUID());
        Session player2 = new Session(UUID.randomUUID());

        VersusMode game = new VersusMode(player1, player2, lobby);

        Assert.assertEquals(LSide.WHITE, game.getSide(player1));
        Assert.assertEquals(LSide.BLACK, game.getSide(player2));
    }
}
