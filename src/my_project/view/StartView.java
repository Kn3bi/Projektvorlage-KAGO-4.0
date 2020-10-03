package my_project.view;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class StartView extends InteractiveGraphicalObject {

    private int state = 0;
    private int offset = 0;

    private ViewController vC;
    private GIFDisplay background;
    private BufferedImage arrowL, arrowR, retry;

    public StartView(ViewController viewController){
        vC = viewController;
        vC.register(this);
        vC.draw(this);
        vC.getSoundController().loadSound("assets/sounds/ac4_main_theme.mp3","title",true);
        vC.getSoundController().playSound("title");
        arrowL = this.createImage("assets/images/arrow_left.png");
        arrowR = this.createImage("assets/images/arrow_right.png");
        retry = this.createImage("assets/images/try_again.png");
        background = new GIFDisplay("assets/images/background1.gif",0,0);
    }

    public void displayScanning(){
        if(state == 0 || state == 2){
            state = 1;
        }
    }

    public void displayTryAgain(){
        if(state == 1){
            offset = 0;
            state = 2;
        }
    }

    public void displayConnected(){

    }

    @Override
    public void draw(DrawTool drawTool) {
        background.draw(drawTool);
        //drawTool.drawLine(300,0,300,600);
        //drawTool.drawLine(0,500,600,500);
        if(state == 1){
            drawTool.formatText("Purisa", Font.PLAIN,24);
            drawTool.drawText(190,530,"Scanning for Server...");
            drawTool.drawImage(arrowL,140- offset,500);
            drawTool.drawImage(arrowL,90- offset *1.5,500);
            drawTool.drawImage(arrowR,460-arrowR.getWidth()+ offset,500);
            drawTool.drawImage(arrowR,510-arrowR.getWidth()+ offset *1.5,500);
        }
        if(state == 2){
            drawTool.formatText("Purisa", Font.PLAIN,24);
            drawTool.drawText(210,530,"Timeout. Retry?");
            drawTool.drawRotatedImage(retry,300-retry.getWidth()/2,240-retry.getHeight()/2,offset);
        }
    }

    @Override
    public void update(double dt) {
        if(state == 1){
            offset += dt*70;
            if (offset > 45) offset = 0;
        }
        if(state == 2){
            offset += dt*100;
            if (offset > 360) offset = 0;
        }
    }


    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }


}
