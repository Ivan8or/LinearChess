package api.util.launchers;

import api.Root;
import api.util.LChessAPI;
import api.v1.V1;

public class V1Launcher {

    public static void register() {

        new LChessAPI()
                .withPort(3100)
                .withTimeout(8000)
                .withEndpoint(new Root())
                .withEndpoint(new V1())
                .start();
    }
}
