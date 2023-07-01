package model.lobby;


import model.mappings.Endpoint;
import model.mappings.Session;
import model.session.SessionTracker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import util.JsonConverter;
import util.ResourceAsString;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ChessLobbyTest {

    static final String RESOURCE_PATH = "model/lobby/chessLobby/";

    @Mock
    SessionTracker tracker;

    @Test
    public void write() {
        String lobbyId = "alabama";
        ChessLobby from = new ChessLobby(lobbyId, tracker);
        String generated = JsonConverter.toJson(from);

        String expected = ResourceAsString.at(RESOURCE_PATH+"simple.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void lobbyCount() {
        String lobbyId = "alabama";
        ChessLobby lobby = new ChessLobby(lobbyId, tracker);

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

        result = lobby.addPlayer(player2);
        Assert.assertEquals(2, lobby.numPlayers());
        Assert.assertTrue(result);

        result = lobby.addPlayer(player3);
        Assert.assertEquals(2, lobby.numPlayers());
        Assert.assertFalse(result);
    }

    @Test
    public void hasPlayer() {
        String lobbyId = "alabama";
        ChessLobby lobby = new ChessLobby(lobbyId, tracker);

        UUID sessionId = UUID.randomUUID();
        Session player1a = new Session(sessionId);
        Session player1b = new Session(sessionId);

        lobby.addPlayer(player1a);
        Assert.assertTrue(lobby.hasPlayer(player1a));
        Assert.assertTrue(lobby.hasPlayer(player1b));
    }

    @Test
    public void lobbyId() {
        String lobbyId = "alabama";
        ChessLobby lobby = new ChessLobby(lobbyId, tracker);

        Assert.assertEquals(lobbyId, lobby.getLobbyId());
    }
}
