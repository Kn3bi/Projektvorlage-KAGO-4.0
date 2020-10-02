package KAGO_framework.model;

import KAGO_framework.control.Interactable;

import java.awt.event.MouseEvent;

public abstract class InteractiveGraphicalObject extends GraphicalObject implements Interactable {

    @Override
    public abstract void keyPressed(int key);

    @Override
    public abstract void keyReleased(int key);

    @Override
    public abstract void mouseReleased(MouseEvent e);

    @Override
    public abstract void mouseClicked(MouseEvent e);

    @Override
    public abstract void mouseDragged(MouseEvent e);

    @Override
    public abstract void mouseMoved(MouseEvent e);

    @Override
    public abstract void mousePressed(MouseEvent e);

}
