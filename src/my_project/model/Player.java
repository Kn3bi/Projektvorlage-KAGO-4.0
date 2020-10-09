package my_project.model;

import java.awt.image.BufferedImage;

public class Player {

    private String name;
    private int punkte;

    public Player(String name){
        this.name = name;
        punkte = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }
}
