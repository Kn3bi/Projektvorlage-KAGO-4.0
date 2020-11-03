package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class House extends GraphicalObject {

    public House(){
        //Ein leerer Konstruktor erscheint!
    }

    @Override
    public void draw(DrawTool drawTool) {
        //Hier wird ein Rechteck gezeichnet.
        drawTool.drawRectangle(200,200,30,50);
        //Hier wird ein Dreieck gezeichnet. Hierzu müssen drei Punkte angegeben werden. Jeder Punkt wiederum besteht aus zwei Zahlen.
        drawTool.drawTriangle(200,200,230,200,215,175);
    }
}
