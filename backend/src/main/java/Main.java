import api.util.launchers.V1Launcher;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.Session;

public class Main {
    public static void main(String[] args) {
        try {
            Model model = new Model();
            ChessLobby lobby = model.spawnLobby();
            Session p1 = model.getSessions().startSession();
            Session p2 = model.getSessions().startSession();
            System.out.println("lobby: " + lobby.getLobbyId());
            System.out.println("session 1: " + p1);
            System.out.println("session 2: " + p2);
            lobby.addPlayer(p1);
            lobby.addPlayer(p2);
            lobby.start();

            V1Launcher.register(model);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
