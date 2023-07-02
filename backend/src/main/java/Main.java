import api.util.launchers.V1Launcher;
import model.api.Model;
import model.lobby.ChessLobby;
import model.mappings.Session;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Model model = new Model();

            Session p1 = model.getSessions().startSession();
            System.out.println("user: "+p1);

            ChessLobby lobby = model.spawnLobby();
            System.out.println("lobby: "+lobby.getLobbyId());

            System.out.println("press enter to start game");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();

            lobby.addPlayer(p1);
            lobby.addPlayer(model.getSessions().startSession());
            System.out.println("game lobby started!");

            V1Launcher.register(model);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
