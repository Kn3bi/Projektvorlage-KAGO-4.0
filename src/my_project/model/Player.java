package my_project.model;

import java.awt.image.BufferedImage;

public class Player {

    private String name;
    private BufferedImage icon;
    private int punkte;

    public Player(String name, BufferedImage icon){
        this.name = name;
        this.icon = icon;
        punkte = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }
}
