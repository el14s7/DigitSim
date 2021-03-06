/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;
import general.Vector2i;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pathFinder.Node;

/**
 * Alle Funktionen die zeichnen / animieren gehören in diese Klasse
 * @author Elias
 */
public class Draw {
    
    /**
    * Funktion zum Linien zeichnen
    *
    * @author Tim
    */
    public static void gcDrawLine(GraphicsContext gc, double x1, double y1, double x2, double y2, double size, Color color)
    { 
        if(gc.getStroke() != color)
        {
            gc.setStroke(color);
        }
        gc.setLineWidth(size);
        gc.strokeLine( x1, y1, x2, y2);
    }
    
    /**
    * Line zeichnen, die aus mehreren koordinaten als 2 besteht
    * 
    * @author Tim
    * 
    * @param gc GraphicsContent
    * @param dx Array mit X - Koordinaten, jedoch nur die differenz zum LinienUrsprung
    * @param dy Array mit Y - Koordinaten, jedoch nur die differenz zum LinienUrsprung
    * @param lineWidth Liniendicke
    * @param color Linienfarbe
    */
    public static void gcLineFromArray(GraphicsContext gc, double dx[], double dy[], double lineWidth, Color color)
    { 
       int points = 0;
       
       if(checkArraySameLength(dx, dy) == true) {
           points = dx.length;
       }
       
       if(points > 1)
       {
            for(int i = 1; i < points; i++)
            {
                gcDrawLine(gc, dx[i], dy[i], dx[i - 1], dy[i - 1], lineWidth, color);   
            }
        }else{
           ErrorHandler.printError(Draw.class, "Linie Wurde nicht gezeichnet weil: array Länge stimmt nicht");
       }
    }
    
    /**
    * Funktion zum Kreise zeichnen
    *
    * @author Tim
    */
    public static void gcDrawCircle(GraphicsContext gc, double x, double y, double r, Color color)
    { 
        if(gc.getFill() != color)
        {
            gc.setFill(color);
        }
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
    }
    
    /**
    * Funktion zum Linien zeichnen, im Array jedoch wird nur die Differenz zum Linienursprung angegeben
    * 
    * @author Elias
    * 
    * @param gc GraphicsContent
    * @param dx Array mit X - Koordinaten, jedoch nur die differenz zum LinienUrsprung
    * @param dy Array mit Y - Koordinaten, jedoch nur die differenz zum LinienUrsprung
    * @param lineWidth Liniendicke
    * @param color Linienfarbe
    * @param baseX X-Startkoordinate der Linie
    * @param baseY Y-Startkoordinate der Linie
    * 
    */
    public static void gcLineFromArrayBaseCoords(GraphicsContext gc, double dx[], double dy[], double lineWidth, Color color, double baseX, double baseY)
    {        
       for(int i = 0; i < dx.length; i++) {
           dx[i] = dx[i] + baseX;
           dy[i] = dy[i] + baseY;
       }
       gcLineFromArray(gc,dx,dy,lineWidth,color);
    }
    
    /**
     * Vergleicht die Länge zweier Arrays
     * @param a1 array nr 1
     * @param a2 array nr 2
     * @return wenn arrays gleichlang: return true      sonst return false
     */
    public static boolean checkArraySameLength(double a1[],double a2[]){
        if(a1.length != a2.length) {
           ErrorHandler.printError(Draw.class, "Arrays haben nicht die gleiche Länge");
           return false;
        }      
        return true;
    }
    
    /**
    * Funktionen zum erstellen von Zeichnungen (ziehmlich selbsterklärend daher auch nicht Auskommentiert)
    *
    * @author Dominik
    */
    public static Circle drawCircle(double dX, double dY, double dRadius, Color dColor, double alpha, boolean fill, double lineWidth){ //Kreis zeichen
        Circle circle = new Circle( dX, dY, dRadius);
        circle.setStroke(dColor);
        circle.setStrokeWidth(lineWidth);
        if(fill) {
            circle.setFill(dColor.deriveColor(1, 1, 1, alpha));            
        }
        else {
            circle.setFill(Color.TRANSPARENT);
        }

        return circle;
    }
    
    public static Circle drawCircle(Vector2i coords, double dRadius, Color dColor, double alpha, boolean fill, double lineWidth){ //Kreis zeichen
        Circle circle = new Circle( coords.getX(), coords.getY(), dRadius);
        circle.setStroke(dColor);
        circle.setStrokeWidth(lineWidth);
        if(fill) {
            circle.setFill(dColor.deriveColor(1, 1, 1, alpha));            
        }
        else {
            circle.setFill(Color.TRANSPARENT);
        }

        return circle;
    }
    
    public static Rectangle drawRectangle(double dX, double dY, double dWidth, double dHeight, double dArcHeight, double dArcWidth, Color dColor, double alpha, double strokeWidth){//Rechteck zeichen
        Rectangle rec = new Rectangle(dX, dY, dWidth, dHeight);
        rec.setStroke(dColor);
        rec.setStrokeWidth(strokeWidth);
        rec.setFill(Color.BLACK.deriveColor(1, 1, 1, alpha));
        rec.setArcHeight(dArcHeight);
        rec.setArcWidth(dArcWidth);
        return rec;
    }
    
    public static Label drawLabel(double dX, double dY, String dText, Color dColor, boolean dUnderline, int dFontSize){//Label zeichen
        Label label = new Label(dText);
        label.setTranslateX(dX);
        label.setTranslateY(dY);
        label.setFont(new Font(dFontSize));
        label.setTextFill(dColor);
        label.setUnderline(dUnderline);
        return label;
    }
    
    public static Line drawLine(double p1X, double p1Y, double p2X, double p2Y, Color dColor,double dWidth){
        Line l = new Line(p1X, p1Y, p2X, p2Y);
        l.setStrokeWidth(dWidth);
        l.setStroke(dColor);
        return l;
    } 
    
    public static Line drawLine(Vector2i start, Vector2i end, Color dColor,double dWidth){
        Line l = new Line(start.getX(), start.getY(), end.getX(), end.getY());
        l.setStrokeWidth(dWidth);
        l.setStroke(dColor);
        return l;
    } 
    
    public static Text drawText(double pX, double pY, String content, Color color, int size){ //Text zeichen
        Text text = new Text(pX, pY, content);
        text.setFont(new Font(size));
        text.setStroke(color);
        text.setFill(color);
        text.applyCss(); //Sorgt einfach dafür dass alles ordnungsgemäß übernommen wird, ändert am aussehen natürlich nichts, da CSS gleich bleibt.
        return text;
    }
    
    /**
     * Baut eine Gruppe bestehend aus Linien. Die X und Y Koordinaten der Linien stecken in den beiden Arrays. Die Linien werden außerdem an das Karomuster angepasst.
     * @author Elias
     * @return 
     */
    public static Group lineArrayXYToGroup(double dx[], double dy[], Color lineColor, double lineWidth) {
        if(Draw.checkArraySameLength(dx, dy)) {
            Group g = new Group();
            for (int i =0; i < (dx.length-1); i ++) {
                g.getChildren().add(Draw.drawLine(GenFunctions.getXYAdaptGrid(dx[i]), GenFunctions.getXYAdaptGrid(dy[i]),GenFunctions.getXYAdaptGrid(dx[i+1]) , GenFunctions.getXYAdaptGrid(dy[i+1]), lineColor, lineWidth));
            }
            return g;
        }
        return null;
    }
    
    // @author tim (box check)
    //Schaut ob man auf den Input eines Elements geklickt hat (Testfunktion!)
    public static boolean isInArea(double sourceX, double sourceY, double otherX, double otherY, double radius)
    {
        boolean result = (Math.abs(sourceX - otherX) < radius) && (Math.abs(sourceY - otherY) < radius); //Math.abs gibt immer den Absoluten wert zurück
        return result;
    }
    
    public static Group drawPath(List<Node> path) {
        Group g = new Group();
        for(Node i : path) {
            int x = i.tile.getX()*21;
            int y = i.tile.getY()*21;
            Circle circle = new Circle( x+10.5, y+10.5, 3);
            circle.setStroke(Color.RED);
            circle.setStrokeWidth(2);
            circle.setFill(Color.TRANSPARENT);
            g.getChildren().add(circle);
        }
        return g;
    }
}