package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.Player;
import my_project.view.PlayView;
import my_project.view.PlayerSelectView;
import my_project.view.StartView;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private int targetPort;

    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private NetworkController networkController;
    private StartView startView;
    private PlayerSelectView playerSelectView;
    private PlayView playView;
    private State state;
    private Player player;

    // Enum
    public enum State {
        SCANNING, TRYAGAIN, PLAYERSELECT, PLAYING, FINISHED
    }

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param ViewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController ViewController){
        this.viewController = ViewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen. Achtung: funktioniert nicht im Szenario-Modus
     */
    public void startProgram() {
        networkController = new NetworkController();
        while(targetPort < 1000 || targetPort > 65000) {
            try {
                String m = JOptionPane.showInputDialog("Port für Spielserver (1000 bis 65000) eingeben:");
                targetPort = Integer.parseInt(m);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(viewController.getDrawFrame(), "Wer keine Zahl eingeben kann, darf nicht spielen.", "Braincheck", JOptionPane.ERROR_MESSAGE);
            }
        }
        startView = new StartView(viewController,this);
        setState(State.SCANNING);
    }

    /**
     * Sorgt dafür, dass zunächst gewartet wird, damit der SoundController die
     * Initialisierung abschließen kann. Die Wartezeit ist fest und damit nicht ganz sauber
     * implementiert, aber dafür funktioniert das Programm auch bei falscher Java-Version
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        if(state == State.SCANNING){
            if (networkController.getServerIP() != null){
                if (networkController.getServerIP().equals("timeout")){
                    startView.displayTryAgain();
                    setState(State.TRYAGAIN);
                } else {
                    setState(State.PLAYERSELECT);
                }
            } else {
                startView.displayScanning();
            }
        }
    }

    /**
     * Ändert den Spielzusand
     * @param state ein im enum State definierter Zustand
     */
    public void setState(State state){
        this.state = state;
        if (state == State.SCANNING) networkController.startNetworkScan(targetPort);
        if (state == State.PLAYERSELECT){
            startView.disposeView();
            //todo Hier Serververbingung einbauen!
            playerSelectView = new PlayerSelectView(viewController,this);
        }
        if (state == State.PLAYING){
            playerSelectView.disposeView();
            player = new Player(playerSelectView.getName(),playerSelectView.getPlayerIcons()[playerSelectView.getSelectedIconIndex()]);
            networkController.sendPlayerName(player);
            playView = new PlayView(viewController,this);
        }
    }

    public void mouseClicked(MouseEvent e){}

}
