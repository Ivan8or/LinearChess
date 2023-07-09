import api.util.launchers.V1Launcher;
import model.socket.SocketServer;
import model.api.Model;

public class Main {
    public static void main(String[] args) {
        try {
            Model model = new Model();
            V1Launcher.register(model, 3100);
            SocketServer allSockets = new SocketServer(model, 3200);
            allSockets.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
