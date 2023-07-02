package model.lobby;


import model.api.Model;
import model.mappings.LobbyID;
import model.mappings.Session;
import model.session.SessionTracker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ChessLobbyTest {

    @Mock
    Model model;

    @Mock
    SessionTracker tracker;

    @Test
    public void startGame() {
        LobbyID lobbyId = new LobbyID("alabama");
        ChessLobby lobby = new ChessLobby(lobbyId, tracker, model);

        Session player1 = new Session(UUID.randomUUID());
        Session player2 = new Session(UUID.randomUUID());

        Assert.assertFalse(lobby.hasStarted());

        lobby.addPlayer(player1);
        Assert.assertFalse(lobby.hasStarted());

        lobby.addPlayer(player2);
        Assert.assertFalse(lobby.hasStarted());

        lobby.start();
        Assert.assertTrue(lobby.hasStarted());
    }

    @Test
    public void playerCount() {
        LobbyID lobbyId = new LobbyID("alabama");
        ChessLobby lobby = new ChessLobby(lobbyId, tracker, model);

        Session player1 = new Session(UUID.randomUUID());
        Session player2 = new Session(UUID.randomUUID());
        Session player3 = new Session(UUID.randomUUID());

        Assert.assertEquals(0, lobby.numPlayers());

        boolean result = lobby.addPlayer(player1);
        Assert.assertEquals(1, lobby.numPlayers());
        Assert.assertTrue(result);

        result = lobby.addPlayer(player1);
        Assert.assertEquals(1, lobby.numPlayers());
        Assert.assertFalse(result);

        lobby.removePlayer(player1);
        Assert.assertEquals(0, lobby.numPlayers());

        lobby.addPlayer(player1);
        result = lobby.addPlayer(player2);
        Assert.assertEquals(2, lobby.numPlayers());
        Assert.assertTrue(result);

        result = lobby.addPlayer(player3);
        Assert.assertEquals(2, lobby.numPlayers());
        Assert.assertFalse(result);
    }

    @Test
    public void hasPlayer() {
        LobbyID lobbyId = new LobbyID("alabama");
        ChessLobby lobby = new ChessLobby(lobbyId, tracker, model);

        UUID sessionId = UUID.randomUUID();
        Session player1a = new Session(sessionId);
        Session player1b = new Session(sessionId);

        lobby.addPlayer(player1a);
        Assert.assertTrue(lobby.hasPlayer(player1a));
        Assert.assertTrue(lobby.hasPlayer(player1b));
    }

    @Test
    public void lobbyId() {
        LobbyID lobbyId = new LobbyID("alabama");
        ChessLobby lobby = new ChessLobby(lobbyId, tracker, model);

        Assert.assertEquals(lobbyId, lobby.getLobbyId());
    }
}
