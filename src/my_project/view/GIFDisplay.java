package my_project.view;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;

import javax.swing.*;

public class GIFDisplay extends GraphicalObject {

    private List<ImageIcon> images;
    private int amount;

    public GIFDisplay(String imagePath, double x, double y){
        images = new List<>();
        addImage(imagePath);
        images.toFirst();
        this.x = x;
        this.y = y;
    }

    public void addImage(String imagePath){
        images.append(new ImageIcon(imagePath));
        amount++;
    }

    public void setImageByIndex(int i){
        if(!images.isEmpty() && i <= amount){
            int current = 1;
            images.toFirst();
            while(current < i){
                images.next();
            }
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(images.hasAccess()) images.getContent().paintIcon(drawTool.getParent(), drawTool.getGraphics2D(), (int)x, (int)y);
    }

    @Override
    public void update(double dt) {

    }
}
