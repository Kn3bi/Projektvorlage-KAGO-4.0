package my_project.control;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

public class NetworkController {

    private String serverIP;
    private boolean isWorking;
    private int maximumCycles;
    private int currentCycle;
    
    public NetworkController(){
        serverIP = null;
        isWorking = false;
        maximumCycles = 100;
    }

    /**
     * Initiiert einen Netzwerkscan auf dem entsprechenden Port. Falls ein Server entdeckt wird,
     * wird die IP im String serverIP gesetzt.
     * @param port Der zu prüfende Port
     */
    public void startNetworkScan(int port){
        if(!isWorking) {
            isWorking = true;
            currentCycle = 0;
            new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    while (serverIP == null && currentCycle < maximumCycles) {
                        currentCycle++;
                        serverIP = scanForServerIP(port);
                    }
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
        String iIPv4 = getNetworkPartOfIP();
        for (int i = 1; i < 254; i++) {
            try {
                //System.out.println("Checking IP: "+iIPv4 + i);
                Socket mySocket = new Socket();
                SocketAddress address = new InetSocketAddress(iIPv4 + i, port);

                mySocket.connect(address, 5);
                // Hier nach ist Connection acquired!
                return iIPv4+i;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("No success.");
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

}
