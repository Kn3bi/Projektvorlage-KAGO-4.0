package my_project.view;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import KAGO_framework.view.ProgramView;
import KAGO_framework.view.simple_gui.GIFPainter;
import my_project.control.ProgramController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PlayerSelectView extends ProgramView {

    private int selectedIconIndex = 0;
    private boolean autoChangeName = true;
    private String name = "Mr. Bust";

    private BufferedImage[] playerIcons;
    private GraphicalObject change, start, waiting;
    private GIFPainter background;

    public PlayerSelectView(ViewController viewController, ProgramController programController){
        super(viewController,programController);
        playerIcons = new BufferedImage[8];
        playerIcons[0] = this.createImage("assets/images/playerIcons/icon_bust.png");
        playerIcons[1] = this.createImage("assets/images/playerIcons/icon_cat.png");
        playerIcons[2] = this.createImage("assets/images/playerIcons/icon_child.png");
        playerIcons[3] = this.createImage("assets/images/playerIcons/icon_devil.png");
        playerIcons[4] = this.createImage("assets/images/playerIcons/icon_einstein.png");
        playerIcons[5] = this.createImage("assets/images/playerIcons/icon_eyeman.png");
        playerIcons[6] = this.createImage("assets/images/playerIcons/icon_hippo.png");
        playerIcons[7] = this.createImage("assets/images/playerIcons/icon_panda.png");
        change = new GraphicalObject("assets/images/change.png");
        change.setX(300-change.getWidth()/2);
        change.setY(400);
        start = new GraphicalObject("assets/images/start.png");
        start.setX(300-start.getWidth()/2);
        start.setY(510);
        waiting = new GraphicalObject("assets/images/waiting.png");
        waiting.setX(300-start.getWidth()/2);
        waiting.setY(510);
        background = new GIFPainter("assets/images/background2.gif",0,0);
        viewController.getSoundController().loadSound("assets/sounds/speech/choosenow.mp3","choose",false);
        viewController.getSoundController().loadSound("assets/sounds/speech/rename.mp3","rename",false);
        viewController.getSoundController().playSound("choose");
        viewController.getSoundController().loadSound("assets/sounds/sword-unsheathe.wav","unsheathe",false);
    }

    @Override
    public void keyReleased(int key) {
        super.keyReleased(key);
        if(key == KeyEvent.VK_N){
            programController.setState(ProgramController.State.WAITINGFORNAME);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        for(int i = 0; i < 4; i++){
            if(x > 40 && x < 40+100 && y > 50+i*120 && y < 50+i*120+100){
                if(selectedIconIndex != i){
                    if(autoChangeName) autoChangeName(i);
                    selectedIconIndex = i;
                    viewController.getSoundController().playSound("unsheathe");
                }
            }
        }
        for(int i = 4; i < 8; i++){
            if(x > 450 && x < 450+100 && y > 50+(i-4)*120 && y < 50+(i-4)*120+100) {
                if(selectedIconIndex != i){
                    if(autoChangeName) autoChangeName(i);
                    selectedIconIndex = i;
                    viewController.getSoundController().playSound("unsheathe");
                }
            }
        }
        if(change.collidesWith(x,y)){
            String m = null;
            viewController.getSoundController().playSound("unsheathe");
            while(m == null || m.length() > 12){
                viewController.getSoundController().playSound("rename");
                m = JOptionPane.showInputDialog("Your new name shall be:");
            }
            autoChangeName = false;
            name = m;
        }
        if(start.collidesWith(x,y)){
            if(programController.getState() == ProgramController.State.WAITINGFORNAME)
                programController.setState(ProgramController.State.PLAYING);
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        background.draw(drawTool);
        drawTool.formatText("Purisa", Font.BOLD,24);
        drawTool.drawText(185,120,"Choose your Avatar!");
        drawTool.setCurrentColor(255,255,255,160);
        drawTool.drawFilledRectangle(change.getX(),250,change.getWidth(),130);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawText(300 - 10*6,300,"Your name:");
        drawTool.drawText(300 - name.length()*6,360,name);
        change.draw(drawTool);
        if(programController.getState() == ProgramController.State.WAITINGFORNAME){
            start.draw(drawTool);
        } else {
            waiting.draw(drawTool);
        }
        for(int i = 0; i < 4; i++){
            if(selectedIconIndex == i){
                drawTool.setCurrentColor(255,255,0,200);
                drawTool.drawFilledRectangle(40,45+i*120,115,115);
            }
            drawTool.setCurrentColor(255,255,255,180);
            drawTool.drawFilledRectangle(45,50+i*120,105,105);
            drawTool.drawImage(playerIcons[i],50,55+i*120);
        }
        for(int i = 4; i < 8; i++){
            if(selectedIconIndex == i){
                drawTool.setCurrentColor(255,255,0,200);
                drawTool.drawFilledRectangle(445,45+(i-4)*120,115,115);
            }
            drawTool.setCurrentColor(255,255,255,180);
            drawTool.drawFilledRectangle(450,50+(i-4)*120,105,105);
            drawTool.drawImage(playerIcons[i],455,55+(i-4)*120);
        }
    }

    private void autoChangeName(int i){
        switch(i){
            case 0: name = "Mr. Bust"; break;
            case 1: name = "Crazed cat"; break;
            case 2: name = "The BOI"; break;
            case 3: name = "Sir Evil"; break;
            case 4: name = "Einstein4ever"; break;
            case 5: name = "EyeMan"; break;
            case 6: name = "HippoNotHappy"; break;
            case 7: name = "BadPanda"; break;
        }
    }

    @Override
    public void update(double dt) {

    }

    public BufferedImage[] getPlayerIcons(){
        return playerIcons;
    }

    public int getSelectedIconIndex() {
        return selectedIconIndex;
    }

    public String getName() {
        return name;
    }

}
