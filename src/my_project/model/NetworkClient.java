package my_project.model;

import KAGO_framework.model.abitur.netz.Client;
import my_project.control.NetworkController;

public class NetworkClient extends Client {

    private boolean debug = true;

    private NetworkController networkController;

    public NetworkClient(String pServerIP, int pServerPort, NetworkController networkController) {
        super(pServerIP, pServerPort);
        this.networkController = networkController;
    }

    @Override
    public void processMessage(String pMessage) {
        networkController.processServerRequest(pMessage);
        if(debug)System.out.println("Received: "+pMessage);
    }

    @Override
    public void send(String pMessage){
        super.send(pMessage);
        if(debug)System.out.println("Sending: "+pMessage);
    }

}
