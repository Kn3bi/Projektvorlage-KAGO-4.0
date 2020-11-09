package KAGO_framework.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Diese Klasse dient als vereinfachte Schnittstelle zum Zeichnen. Es handelt sich um eine BlackBox fuer die
 * Graphics2D-Klasse.
 * Vorgegebene Klasse des Frameworks. Modifikation auf eigene Gefahr.
 */
public class DrawTool {

    // Referenzen
    private Graphics2D graphics2D; //java-spezifisches Objekt zum Arbeiten mit 2D-Grafik
    private JComponent parent;

    /**
     * Zeichnet ein Objekt der Klasse BufferedImage
     * @param bI Das zu zeichnende Objekt
     * @param x Die x-Koordinate der oberen linken Ecke
     * @param y Die y-Koordinate der oberen linken Ecke
     */
    public void drawImage(BufferedImage bI, double x, double y){
        if (graphics2D!= null) graphics2D.drawImage(bI, (int)x, (int)y, null);
    }

    /**
     * Zeichnet ein Objekt der Klasse BufferedImage.
     * @param bI das BufferedImage, das gezeichnet wird
     * @param x x-Koordinate der oberen linken Ecke
     * @param y y-Koordinate der oberen linken Ecke
     * @param degrees Grad, die das Bild rotiert sein soll (nicht Bogemaß)
     * @param scale Faktor mit dem das Bild skaliert werden soll (für Wirkung > 0)
     */
    public void drawTransformedImage(BufferedImage bI, double x, double y, double degrees, double scale){
        if (graphics2D!= null){
            AffineTransform transform = new AffineTransform();

            transform.translate(x,y);
            if(scale > 0) {
                transform.scale(scale,scale);
                if(scale < 1){
                    transform.translate(+bI.getWidth() * (1-scale), +bI.getHeight() * (1-scale));
                } else {
                    transform.translate(-bI.getWidth()*(scale-1)*0.25, -bI.getHeight()*(scale-1)*0.25);
                }
            }
            transform.rotate( Math.toRadians(degrees), bI.getWidth()/2, bI.getHeight()/2 );
            graphics2D.drawImage(bI, transform, null);
        }
    }

    /**
     * Modifiziert die Breite aller gezeichneten Linien
     * Der Standardwert ist 1
     * @param size die gewünschte Breite in Pixeln
     */
    public void setLineWidth(int size){
        if (graphics2D!= null){
            graphics2D.setStroke(new BasicStroke(size));
        }
    }

    /**
     * Zeichnet ein Rechteck als Linie ohne Fuellung
     * @param x Die x-Koordinate der oberen linken Ecke
     * @param y Die y-Koordinate der oberen linken Ecke
     * @param width Die Breite
     * @param height Die Hoehe
     */
    public void drawRectangle(double x, double y, double width, double height){
        Rectangle2D.Double r = new Rectangle2D.Double(x,y,width,height);
        if (graphics2D!= null) graphics2D.draw(r);
    }

    /**
     * Zeichnet ein gefuelltes Rechteck
     * @param x Die x-Koordinate der oberen linken Ecke
     * @param y Die y-Koordinate der oberen linken Ecke
     * @param width Die Breite
     * @param height Die Hoehe
     */
    public void drawFilledRectangle(double x, double y, double width, double height){
        Rectangle2D.Double r = new Rectangle2D.Double(x,y,width,height);
        if (graphics2D!= null) graphics2D.fill(r);
    }

    /**
     * Aendert die aktuell verwendete Farbe zum Zeichnen auf eine andere Farbe.
     * Die aenderung gilt solange, bis eine neue Farbe gesetzt wird (Zustand)
     * @param r Der Gruen-Anteil der Farbe (0 bis 255)
     * @param g Der Gelb-Anteil der Farbe (0 bis 255)
     * @param b Der Blau-Anteil der Farbe (0 bis 255)
     * @param alpha Die Transparenz der Farbe, wobei 0 nicht sichtbar und 255 voll deckend ist
     */
    public void setCurrentColor(int r, int g, int b, int alpha){
        if (graphics2D!= null) {
            graphics2D.setColor( new Color(r,g,b,alpha) );
        }
    }

    /**
     * Aendert die aktuell verwendete Farbe zum Zeichnen auf eine andere Farbe.
     * Die Änderung gilt solange, bis eine neue Farbe gesetzt wird.
     * @param color Von außen festgelegtes Farb-Objekt
     */
    public void setCurrentColor(Color color){
        if (graphics2D!= null && color != null) graphics2D.setColor( color );
    }

    /**
     * Zeichnet einen Kreis ohne Fuellung als Linie
     * @param x Die x-Koordinate des Mittelpunkts
     * @param y Die y-Koordinate des Mittelpunkts
     * @param radius Der Kreisradius
     */
    public void drawCircle(double x, double y, double radius){
        Ellipse2D.Double e = new Ellipse2D.Double(x-radius,y-radius,radius*2,radius*2);
        if (graphics2D!= null) graphics2D.draw(e);
    }

    /**
     * Zeichnet einen gefuellten Kreis
     * @param x Die x-Koordinate des Mittelpunkts
     * @param y Die y-Koordinate des Mittelpunkts
     * @param radius Der Kreisradius
     */
    public void drawFilledCircle(double x, double y, double radius){
        Ellipse2D.Double e = new Ellipse2D.Double(x-radius,y-radius,radius*2,radius*2);
        if (graphics2D!= null) graphics2D.fill(e);
    }

    /**
     * Zeichnet ein nicht gefuelltes Dreieck
     * @param x1 Die x-Koordinate der ersten Ecke
     * @param y1 Die y-Koordinate der ersten Ecke
     * @param x2 Die x-Koordinate der zweiten Ecke
     * @param y2 Die y-Koordinate der zweiten Ecke
     * @param x3 Die x-Koordinate der dritten Ecke
     * @param y3 Die y-Koordinate der dritten Ecke
     */
    public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3 ){
        Polygon p = new Polygon();
        p.addPoint((int)x1,(int)y1);
        p.addPoint((int)x2,(int)y2);
        p.addPoint((int)x3,(int)y3);
        if (graphics2D!= null) graphics2D.draw(p);
    }

    /**
     * Zeichnet ein gefuelltes Dreieck
     * @param x1 Die x-Koordinate der ersten Ecke
     * @param y1 Die y-Koordinate der ersten Ecke
     * @param x2 Die x-Koordinate der zweiten Ecke
     * @param y2 Die y-Koordinate der zweiten Ecke
     * @param x3 Die x-Koordinate der dritten Ecke
     * @param y3 Die y-Koordinate der dritten Ecke
     */
    public void drawFilledTriangle(double x1, double y1, double x2, double y2, double x3, double y3 ){
        Polygon p = new Polygon();
        p.addPoint((int)x1,(int)y1);
        p.addPoint((int)x2,(int)y2);
        p.addPoint((int)x3,(int)y3);
        if (graphics2D!= null) graphics2D.fill(p);
    }

    /**
     * Zeichnet eine duenne Linie zwischen den beiden Punkten
     * @param x1 Die x-Koordinate des ersten Punkts
     * @param y1 Die y-Koordinate des ersten Punkts
     * @param x2 Die x-Koordinate des zweiten Punkts
     * @param y2 Die y-Koordinate des zweiten Punkts
     */
    public void drawLine(double x1, double y1, double x2, double y2){
        Line2D.Double line = new Line2D.Double(x1,y1,x2,y2);
        if (graphics2D!= null) graphics2D.draw(line);
    }

    /**
     * Zeichnet einen Text an die gewuenschte Stelle
     * @param x Die x-Position des Textbeginns
     * @param y Die y-Position des Textbeginns
     * @param text Der anzuzeigende Text
     */
    public void drawText(double x, double y, String text){
        if (graphics2D!=null) graphics2D.drawString(text,(int)x,(int)y);
    }

    /**
     * Passt die Schriftart und Größe der gezeichneten Texte an.
     * @param fontName Der Name der Schriftart, z.B. "Arial"
     * @param style Der Style für die Schriftart, z.B. Font.BOLD
     * @param size Die Größe der Schrift
     */
    public void formatText(String fontName, int style, int size){
        if (graphics2D != null) graphics2D.setFont(new Font(fontName, style, size));
    }
	
    /**
     * Spezifiziert das zu verwendende Grafikobjekt von Java und
     * das Objekt in dem gezeichnet wird.
     * Bitte nicht verwenden.
     * @param g2d Das java-interne Grafikobjekt des Programmfensters
     */
    public void setGraphics2D(Graphics2D g2d, JComponent parent){
        graphics2D = g2d;
        this.parent = parent;
    }

    public Graphics2D getGraphics2D(){
        return graphics2D;
    }

    public JComponent getParent(){
        return parent;
    }

}
