package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.view.GIFDisplay;
import my_project.view.StartView;

import java.awt.event.MouseEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private boolean stateInvoked;

    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private NetworkController networkController;
    private StartView startView;
    private State state;

    // Enum
    enum State {
        SCANNING, TRYAGAIN, CONNECTED, PLAYING, FINISHED
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
        networkController.startNetworkScan(1234);
        setState(State.SCANNING);
        startView = new StartView(viewController);
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
                    startView.displayConnected();
                    setState(State.CONNECTED);
                }
            } else {
                startView.displayScanning();
            }
        }

    }

    public void setState(State state){
        this.state = state;
    }

    public void mouseClicked(MouseEvent e){}

}
