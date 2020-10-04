package my_project.model;

import KAGO_framework.model.abitur.netz.Client;
import my_project.control.NetworkController;

public class NetworkClient extends Client {

    private NetworkController networkController;

    public NetworkClient(String pServerIP, int pServerPort, NetworkController networkController) {
        super(pServerIP, pServerPort);
        this.networkController = networkController;
    }

    @Override
    public void processMessage(String pMessage) {
        networkController.processServerRequest(pMessage);
    }

}
