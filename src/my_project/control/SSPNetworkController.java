package my_project.control;

import KAGO_framework.control.NetworkController;
import my_project.model.Player;

public class SSPNetworkController extends NetworkController {

    public SSPNetworkController(ProgramController programController){
        super(programController);
    }

    /**
     * Sendet dem Server den Spielernamen
     * @param player das Spielerobjekt der Partie
     */
    public void sendPlayerName(Player player){
        send("name"+"$"+player.getName());
    }

    /**
     * Sendet dem Server die Auswahl des Spielers
     * @param choice die getroffene Auswahl
     */
    public void sendPlayerChoice(String choice){
        send("spiele"+"$"+choice);
    }

    /**
     * Verarbeitet alle Informatonen, die der Server an den NetworkClient schickt.
     * @param msg die Nachricht des Servers
     */
    @Override
    public void processServerRequest(String msg){
        // todo Netzwerkschnittstelle implementieren
        if(msg.equals("sende$name")){
            programController.setState(ProgramController.State.WAITINGFORNAME);
        } else if(msg.equals("sende$möglichkeiten")){
            programController.requestSelectionFromPlayer();
        } else {
            String[] tokens = msg.split("\\$");
            if(tokens[0].equals("gegner")){
                if(tokens[1].equals("name")){
                    programController.verarbeiteGegnernamen(tokens[2]);
                }
                if(tokens[1].equals("auswahl")){
                    programController.verarbeiteGegnerauswahl(tokens[2]);
                }
            }
            if(tokens[0].equals("punkte")){
                programController.verarbeiteNeuePunkte(tokens[1]);
            }
            if(tokens[0].equals("status")){
                programController.verarbeiteNeuenStatus(tokens[1]);
            }
        }
    }

}
