package api.util.launchers;

import api.Root;
import api.util.LChessAPI;
import api.v1.V1;
import api.v1.lobbies.V1Lobbies;
import api.v1.lobbies.boards.V1LobbiesBoards;
import api.v1.lobbies.inventories.V1LobbiesInventories;
import api.v1.lobbies.shops.V1LobbiesShops;
import api.v1.lobbies.shops.buy.V1LobbiesShopsBuy;
import api.v1.lobbies.shops.refresh.V1LobbiesShopsRefresh;
import api.v1.lobbies.shops.sell.V1LobbiesShopsSell;
import api.v1.lobbies.shops.view.V1LobbiesShopsView;
import api.v1.sessions.V1Sessions;
import api.v1.sessions.updates.V1SessionsStatus;
import model.api.Model;

public class V1Launcher {

    public static void register(Model model, int port) {

        new LChessAPI()
                .withPort(port)
                .withTimeout(8000)
                .withEndpoint(new Root())
                .withEndpoint(new V1())
                .withEndpoint(new V1Sessions(model))
                .withEndpoint(new V1SessionsStatus(model))
                .withEndpoint(new V1Lobbies(model))
                .withEndpoint(new V1LobbiesBoards(model))
                .withEndpoint(new V1LobbiesInventories(model))
                .withEndpoint(new V1LobbiesShops())
                .withEndpoint(new V1LobbiesShopsView(model))
                .withEndpoint(new V1LobbiesShopsBuy(model))
                .withEndpoint(new V1LobbiesShopsSell(model))
                .withEndpoint(new V1LobbiesShopsRefresh(model))
                .start();
    }
}
