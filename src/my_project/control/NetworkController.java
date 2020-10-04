package my_project.control;

import my_project.model.NetworkClient;
import my_project.model.Player;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

public class NetworkController {

    private NetworkClient networkClient;

    private String serverIP;
    private int port;
    private boolean isWorking;
    private int maximumCycles;
    private int currentCycle;

    public NetworkController(){
        serverIP = null;
        isWorking = false;
        maximumCycles = 20;
    }

    /**
     * Initiiert einen Netzwerkscan auf dem entsprechenden Port. Falls ein Server entdeckt wird,
     * wird die IP im String serverIP gesetzt. Solange gescannt wird, ist diese null, wenn es
     * kein Ergebnis gibt, ist sie gleich timeout
     * @param port Der zu prüfende Port
     */
    public void startNetworkScan(int port){
        this.port = port;
        if(!isWorking) {
            this.serverIP = null;
            isWorking = true;
            currentCycle = 0;
            new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    while (serverIP == null && currentCycle < maximumCycles) {
                        currentCycle++;
                        serverIP = scanForServerIP(port);
                    }
                    serverIP = "timeout";
                    isWorking = false;
                    return null;
                }
            }.execute();
        } else{
            System.out.println("INFO (Network Controller): Der letzte Netzwerk-Scan ist noch nicht abgeschlossen.");
        }
    }

    /**
     * Scannt das lokale Netzwerk nach einem Gerät mit offenem Port und liefert die
     * IP zurück, falls irgend eine Verbindung gelingt.
     * @param port Der zu prüfende Port
     * @return die IP Adresse des gefundenen Servers
     */
    private String scanForServerIP(int port) {
        boolean localhostChecked = false;
        String iIPv4 = getNetworkPartOfIP();
        for (int i = 1; i < 254; i++) {
            try {

                Socket mySocket = new Socket();
                if(i == 1 && !localhostChecked){
                    localhostChecked = true;
                    // Prüfe ob Server auf localhost läuft (Priorität)
                    System.out.println("Checking IP: "+"127.0.0."+ i);
                    i--;
                    SocketAddress address = new InetSocketAddress("127.0.0.1", port);
                    mySocket.connect(address, 5);
                    // Hier nach ist Connection acquired!
                    return "127.0.0.1";
                }
                System.out.println("Checking IP: "+iIPv4 + i);
                SocketAddress address = new InetSocketAddress(iIPv4 + i, port);
                mySocket.connect(address, 5);
                // Hier nach ist Connection acquired!
                return iIPv4+i;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                //System.out.println("No success.");
            }
        }
        return null;
    }

    /**
     * Gibt für die Subnetzmaske 255.255.255.0 den Netzwerkteil der lokalen IP-Adresse zurück.
     * @return der Netzwerkteil, falls bestimmbar
     */
    private String getNetworkPartOfIP(){
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            //System.out.println("Your current IP address : " + ip.getHostAddress().replace(ip.getHostName()+"/",""));
            String[] adressTokens = (ip.getHostAddress().replace(ip.getHostName()+"/","")).split("\\.");
            return adressTokens[0]+"."+adressTokens[1]+"."+adressTokens[2]+".";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Erstellt einen neuen Client, der sich mit dem Server verbindet.
     */
    public void startConnection(){
        if(serverIP != null && !serverIP.equals("timeout")){
            networkClient = new NetworkClient(serverIP,port,this);
        }
    }

    /**
     * Sendet dem Server den Spielernamen
     * @param player das Spielerobjekt der Partie
     */
    public void sendPlayerName(Player player){
        //networkClient.send(msg);
    }

    /**
     * Verarbeitet alle Informatonen, die der Server an den NetworkClient schickt.
     * @param msg die Nachricht des Servers
     */
    public void processServerRequest(String msg){
        // todo Netzwerkschnittstelle implementieren
    }

    public String getServerIP() {
        return serverIP;
    }
}
