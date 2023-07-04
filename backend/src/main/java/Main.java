import api.util.launchers.V1Launcher;
import model.api.Model;

public class Main {
    public static void main(String[] args) {
        try {
            Model model = new Model();
            V1Launcher.register(model);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
