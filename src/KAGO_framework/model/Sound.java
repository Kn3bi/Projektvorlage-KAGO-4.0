package KAGO_framework.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import KAGO_framework.Config;

import java.io.File;

/**
 * Ein Objekt dieser Klasse repräsentiert die Daten für einen speziellen Sound.
 * Diese Klasse muss NICHT benutzt werden. Alle Aufgaben sind über ein Objekt der Klasse
 * SoundController zu erledigen.
 */
public class Sound {

    // Attribute
    private String filename;
    private String name;

    // Referenz
    private MediaPlayer mediaPlayer;

    /**
     * Erzeugt einen neuen Sound mit Angabe, ob er wiederholt werden soll.
     * @param filename Der Dateiname im Verzeichnis assets. Zum Beispiel "/sounds/wuff.mp3" Möglich sind die meisten Sound-Dateien.
     * @param name Der Name unter dem der Sound im Programm verwendet werden soll. Er muss EINDEUTIG sein.
     * @param loop Ob der Sound wiederholt werden soll, wenn er fertig gespielt ist.
     */
    public Sound(String filename, String name, boolean loop) {
        this(filename,name);
        if (loop){
            mediaPlayer.setOnEndOfMedia(new Runnable(){
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        }
    }

    /**
     * Erzeugt einen neuen Sound mit Angabe, ob er wiederholt werden soll.
     * @param filename Der Dateiname im Verzeichnis. Zum Beispiel "assets/sounds/wuff.mp3" Möglich sind die meisten Sound-Dateien.
     * @param name Der Name unter dem der Sound im Programm verwendet werden soll. Er muss EINDEUTIG sein.
     */
    public Sound(String filename, String name) {
        this.filename = filename;
        this.name = name;
        File f = new File(System.getProperty("user.dir")+"/"+filename);
        Media sound = new Media(f.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    /**
     * Spielt den Sound ab.
     */
    public void play() {
        if ( Config.DEBUG) System.out.println("Versuche: "+this.getFilename()+" abzuspielen als benannter Ton: "+this.getName());
        mediaPlayer.play();
    }

    /**
     * Stoppt den Sound.
     */
    public void stop() {
        while(mediaPlayer.getStatus() != MediaPlayer.Status.STOPPED){
            mediaPlayer.stop();
        }
    }

    /**
     * Überprüft, ob der Sound gerade abgespielt wird.
     * @return
     */
    public boolean isPlaying(){
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) return true;
        return false;
    }


    /**
     * Liefert den Dateinamen der Sounddatei zurück.
     * @return der Pfad zur Datei
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Liefert den Namen des Sounds zurück.
     * @return der Name (muss eindeutig sein)
     */
    public String getName() {
        return name;
    }
}