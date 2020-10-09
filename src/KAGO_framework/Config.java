package KAGO_framework;

/**
 * In dieser Klasse werden globale, statische Einstellungen verwaltet.
 * Diese beziehen sich nur auf die Funktionsweise des Frameworks.
 * Für individuelle Einstellungen am eigenen Projekt sollte die Config-Datei im Paket "my_project"
 * verwendet werden.
 */
public class Config {

    // Frameworkversion
    public final static String VERSION = "KNB-AOS-GraphicalObject-Java-Framework (light) - 3.4b - 02.10.2020";
    public final static String JAVA_SUPPORTED = "Java 11 + JavaFX";

    // Szenariomodus
    public final static boolean IS_SCENARIO_MODE = false;

    // Schaltet die Infomeldungen des Frameworks an oder aus
    public final static boolean INFO_MESSAGES = true;
    public final static boolean DEBUG = false;

}
