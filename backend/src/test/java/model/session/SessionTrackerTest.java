package model.session;

import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.LobbyID;
import model.mappings.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionTrackerTest {

    @Mock
    Model api;

    @Test
    public void sessionsLobbies() {
        SessionTracker tracker = new SessionTracker();
        when(api.getSessions()).thenReturn(tracker);

        ChessLobby lobby = new ChessLobby(new LobbyID("alabama"), api);

        Session session = tracker.startSession();
        Assert.assertEquals(Optional.empty(), tracker.sessionLobby(session));

        tracker.joinLobby(session, lobby);
        Assert.assertEquals(Optional.of(lobby), tracker.sessionLobby(session));

        tracker.leaveLobby(session);
        Assert.assertEquals(Optional.empty(), tracker.sessionLobby(session));

        tracker.endSession(session);
        Assert.assertNull(tracker.sessionLobby(session));
    }

    @Test
    public void sessionsLifecycle() {
        SessionTracker tracker = new SessionTracker();
        Session ended = tracker.startSession();
        Session valid = tracker.startSession();

        Assert.assertNotEquals(ended, valid);

        tracker.endSession(ended);

        Assert.assertFalse(tracker.validSession(ended));
        Assert.assertTrue(tracker.validSession(valid));

        Session validCopy = new Session(valid.sessionID());

        Assert.assertTrue(tracker.validSession(validCopy));

        Session unregistered = Session.spawn();
        Assert.assertFalse(tracker.validSession(unregistered));
    }
}
