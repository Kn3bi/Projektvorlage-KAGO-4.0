package my_project.view;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class StartView extends GameView {

    private int state = 0;
    private int offset = 0;

    private GIFDisplay background;
    private BufferedImage arrowL, arrowR, retry;

    public StartView(ViewController viewController, ProgramController programController){
        super(viewController,programController);
        viewController.getSoundController().loadSound("assets/sounds/toglory.mp3","title",true);
        viewController.getSoundController().loadSound("assets/sounds/speech/welcome.mp3","welcome",false);
        viewController.getSoundController().playSound("title");
        viewController.getSoundController().playSound("welcome");
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

    @Override
    public void draw(DrawTool drawTool) {
        if(state == 1){
            background.draw(drawTool);
            drawTool.formatText("Purisa", Font.PLAIN,24);
            drawTool.drawText(190,530,"Scanning for Server...");
            //drawTool.drawImage(arrowL,200- offset,510);
            //drawTool.drawImage(arrowL,150- offset *1.5,510);
            //drawTool.drawImage(arrowR,400-arrowR.getWidth()+ offset,510);
            //drawTool.drawImage(arrowR,450-arrowR.getWidth()+ offset *1.5,510);
        }
        if(state == 2){
            drawTool.formatText("Purisa", Font.PLAIN,24);
            drawTool.drawText(210,530,"Timeout. Retry?");
            drawTool.drawTransformedImage(retry,300-retry.getWidth()/2,240-retry.getHeight()/2,offset,1);
        }
    }

    @Override
    public void update(double dt) {
        viewController.getSoundController().setVolume("title",0.25);
        if(state == 1){
            offset += dt*120;
            if (offset > 220) offset = 0;
        }
        if(state == 2){
            offset += dt*100;
            if (offset > 360) offset = 0;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(state == 2){
            double x = e.getX();
            double y = e.getY();
            double x_Rechteck = 300-retry.getWidth()/2;
            double y_Rechteck = 240-retry.getHeight()/2;
            if( x > x_Rechteck && x < x_Rechteck + retry.getWidth() &&
                    y > y_Rechteck && y < y_Rechteck + retry.getHeight()){
                state = 0;
                programController.setState(ProgramController.State.SCANNING);
            }
        }
    }

    @Override
    public void keyReleased(int key) {
        super.keyReleased(key);
        if(key == KeyEvent.VK_N){
            programController.setState(ProgramController.State.PLAYERSELECT);
        }
    }
}
