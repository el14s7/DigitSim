/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import digitsim.DigitSimController;
import digitsim.Draw;
import element.Element;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import pathFinder.Vector2i;

/**
 *
 * @author Tim
 */
public class Connection { //Speichert die Verbindungen

    private DigitSimController dsController;

    public Connection(DigitSimController pDSController) {
        dsController = pDSController;
    }

    // -Bearbeitet von Tim 21.11.16
    public class ConData // Daten einer Verbindung
    {
        public int indexFirstElement;       // index des ersten elements im array
        public boolean typeFirst;           // ein oder ausgang
        public int indexFirst;              // index des jeweiligen ein oder ausgangs am ersten element
        public int indexSecondElement;      // index des zweiten elements im array
        public boolean typeSecond;          // ein oder ausgang
        public int indexSecond;             // index des jeweiligen ein oder ausgangs am zweiten element
        public ConnectionLine connectionLine; // Elias KLasse zum Zeichnen von conenctions
    }

    public static ArrayList<ConData> connections = new ArrayList<ConData>(); //Speichert alle Verbindungne

    /**
     * addConnection(..) Fügtt eine Verbindung zwischen ein und ausgängen von
     * verachiedenen elementen hinzu
     *
     * @author Tim
     *
     * @param indexFirstElement erstes element (index im main array in der
     * Controlelr klasse elements)
     * @param indexSecondElement zweites element (index im main array in der
     * Controlelr klasse elements) param type: 0 für ausgang, 1 für eingang
     * param index: index des ein oder ausgangs am element
     */
    public void addConnection(int indexFirstElement, boolean typeFirst, int indexFirst, int indexSecondElement, boolean typeSecond, int indexSecond) {
        ConData data = new ConData();
        if(indexFirstElement == indexSecondElement){ //Überorüfen ob es sich um die gleichen Elemente handelt
            return;
        }
        for(ConData d : connections){ //Überprüfen ob es die verbindung schon gibt
            if(d.indexFirstElement == indexFirstElement && d.indexSecondElement == indexSecondElement && d.indexFirst == indexFirst && d.indexSecond == indexSecond && d.typeFirst == typeFirst && d.typeSecond == typeSecond){
                return;
            }
        }
        if(typeFirst == typeSecond){ //Überprüfen ob es verschiedene Typen sind (z.B Input = Input wäre falsch, wird also nicht geadded)
            return;
        };
        data.indexFirstElement = indexFirstElement;
        data.indexSecondElement = indexSecondElement;
        data.indexFirst = indexFirst;
        data.indexSecond = indexSecond;
        data.typeFirst = typeFirst;
        data.typeSecond = typeSecond;
        data.connectionLine = new ConnectionLine(dsController);
        connections.add(data);
        drawNewLine(data);
    }

    /* -Bearbeitet von Tim 14.11.16 */
    public void update() //Geht alle Verbindungen durch und schaltet sie durch
    {
        for (ConData d : connections) {
            if ((d.typeFirst != d.typeSecond) && !d.typeFirst) // ausgang mit eingang verbunden
            {
                dsController.getElements().get(d.indexSecondElement).setInput(d.indexSecond, dsController.getElements().get(d.indexFirstElement).getOutput(d.indexFirst));
                d.connectionLine.setColor(dsController.getElements().get(d.indexFirstElement).getOutput(d.indexFirst)); //Farbe der Verbindung anpassen
                
            } else if ((d.typeFirst != d.typeSecond) && d.typeFirst) // eingang mit ausgang verbunden
            {
                dsController.getElements().get(d.indexFirstElement).setInput(d.indexFirst, dsController.getElements().get(d.indexSecondElement).getOutput(d.indexSecond));
                d.connectionLine.setColor(dsController.getElements().get(d.indexSecondElement).getOutput(d.indexSecond)); //Farbe der Verbindung anpassen
            }
        }
    }

    public void drawNewLine(ConData d) {
            Vector2i start = new Vector2i();
            Vector2i end = new Vector2i();

            if ((d.typeFirst != d.typeSecond) && !d.typeFirst) // ausgang mit eingang verbunden
            {
                start.setX((int)dsController.getElements().get(d.indexFirstElement).getOutputX(d.indexFirst));
                start.setY((int)dsController.getElements().get(d.indexFirstElement).getOutputY(d.indexFirst));
                end.setX((int)dsController.getElements().get(d.indexSecondElement).getInputX(d.indexSecond));
                end.setY((int)dsController.getElements().get(d.indexSecondElement).getInputY(d.indexSecond));
            } else if ((d.typeFirst != d.typeSecond) && d.typeFirst) // eingang mit ausgang verbunden
            {
                start.setX((int)dsController.getElements().get(d.indexFirstElement).getInputX(d.indexFirst));
                start.setY((int)dsController.getElements().get(d.indexFirstElement).getInputY(d.indexFirst));
                end.setX((int)dsController.getElements().get(d.indexSecondElement).getOutputX(d.indexSecond));
                end.setY((int)dsController.getElements().get(d.indexSecondElement).getOutputY(d.indexSecond));
            }
            // Linien zeichenen

            d.connectionLine.setStart(start);
            d.connectionLine.setEnd(end);
            d.connectionLine.update(false, connections);
    }
    
    public void drawUpdate(int eIndex){
        for(ConData d : connections){
            if(d.indexFirstElement == eIndex || d.indexSecondElement == eIndex){
            
            Vector2i start = new Vector2i();
            Vector2i end = new Vector2i();
            if ((d.typeFirst != d.typeSecond) && !d.typeFirst) // ausgang mit eingang verbunden
            {
                start.setX((int)dsController.getElements().get(d.indexFirstElement).getOutputX(d.indexFirst));
                start.setY((int)dsController.getElements().get(d.indexFirstElement).getOutputY(d.indexFirst));
                end.setX((int)dsController.getElements().get(d.indexSecondElement).getInputX(d.indexSecond));
                end.setY((int)dsController.getElements().get(d.indexSecondElement).getInputY(d.indexSecond));
            } else if ((d.typeFirst != d.typeSecond) && d.typeFirst) // eingang mit ausgang verbunden
            {
                start.setX((int)dsController.getElements().get(d.indexFirstElement).getInputX(d.indexFirst));
                start.setY((int)dsController.getElements().get(d.indexFirstElement).getInputY(d.indexFirst));
                end.setX((int)dsController.getElements().get(d.indexSecondElement).getOutputX(d.indexSecond));
                end.setY((int)dsController.getElements().get(d.indexSecondElement).getOutputY(d.indexSecond));
            }
            // Linien zeichenen

            d.connectionLine.setStart(start);
            d.connectionLine.setEnd(end);
            d.connectionLine.update(false, connections);
            }
        }
    }
    
    public void drawDirectLineUpdate(int eIndex){
        for(ConData d : connections){
            if(d.indexFirstElement == eIndex || d.indexSecondElement == eIndex){
            
            Vector2i start = new Vector2i();
            Vector2i end = new Vector2i();
            if ((d.typeFirst != d.typeSecond) && !d.typeFirst) // ausgang mit eingang verbunden
            {
                start.setX((int)dsController.getElements().get(d.indexFirstElement).getOutputX(d.indexFirst));
                start.setY((int)dsController.getElements().get(d.indexFirstElement).getOutputY(d.indexFirst));
                end.setX((int)dsController.getElements().get(d.indexSecondElement).getInputX(d.indexSecond));
                end.setY((int)dsController.getElements().get(d.indexSecondElement).getInputY(d.indexSecond));
            } else if ((d.typeFirst != d.typeSecond) && d.typeFirst) // eingang mit ausgang verbunden
            {
                start.setX((int)dsController.getElements().get(d.indexFirstElement).getInputX(d.indexFirst));
                start.setY((int)dsController.getElements().get(d.indexFirstElement).getInputY(d.indexFirst));
                end.setX((int)dsController.getElements().get(d.indexSecondElement).getOutputX(d.indexSecond));
                end.setY((int)dsController.getElements().get(d.indexSecondElement).getOutputY(d.indexSecond));
            }
            // Linien zeichenen

            d.connectionLine.setStart(start);
            d.connectionLine.setEnd(end);
            d.connectionLine.update(true, connections);
            }
        }
    }

    /**
     * @author tim sehen ob nah an einem input geklickt wurde (erstes von 2)
     */
    public static final int EINDEX = 0; // element index
    public static final int CINDEX = 1; // connection index
    public static final int CETYPE = 2; // input(1) oder output(0)

    public int[] closeToInOrOut(MouseEvent event) {
        int result[] = new int[3];
        for (int n = 0; n < dsController.getElements().size(); n++) {
            // schauen ob in der nähe eines inputs geklickt wurde
            for (int i = 0; i < dsController.getElements().get(n).numInputs; i++) {
                double dInX = dsController.getElements().get(n).getInputX(i);
                double dInY = dsController.getElements().get(n).getInputY(i);
                if (Draw.isInArea(event.getX(), event.getY(), dInX, dInY, 5)) {
                    result[EINDEX] = n; // element index
                    result[CINDEX] = i; // input index
                    result[CETYPE] = 1; // es ist ein input
                    return result;
                }
            }

            // schauen ob in der nähe eines outputs geklickt wurde
            for (int i = 0; i < dsController.getElements().get(n).getNumOutputs(); i++) {
                double dInX = dsController.getElements().get(n).getOutputX(i);
                double dInY = dsController.getElements().get(n).getOutputY(i);
                if (Draw.isInArea(event.getX(), event.getY(), dInX, dInY, 5)) {
                    result[EINDEX] = n; // element index
                    result[CINDEX] = i; // output index
                    result[CETYPE] = 0; // es ist ein output
                    return result;
                }
            }
        }
        return null;
    }

    //Author: Dominik
    //Löscht alle Verbindungen
    public void clear() {
        connections.clear();
    }

    // Author Tim
    // bestimmte verbindung entfernen
    public void removeConnection(ConData data) {
        connections.remove(data);
    }

    public ArrayList<ConData> getConnectionData() { //Verbindungen zurückgeben
        return connections;
    }

    public ConData getSpecificConnectionData(int choice) { //Einzelne Verbindungen zurückgeben
        return connections.get(choice);
    }
    
    public void removeAllConncectionsRelatedTo(Element pE){ 
        ArrayList<ConData> temp = new ArrayList<>(); //Wir müssen alle zu löschenden Verbindungen hier speichern, erklärung siehe (1)
        for(ConData d : connections){
            if(dsController.getElements().get(d.indexFirstElement).hashCode() == pE.hashCode() || dsController.getElements().get(d.indexSecondElement).hashCode() == pE.hashCode()){
                d.connectionLine.clear(); 
                temp.add(d); //(1) würden wir hier das ConData Objekt aus connections enfernen gibt es einen Error, da die For-Schleife weiterhin versucht alles durchzulaufen, allerdings hat sich die Größe vom Array geändern wenn das Objekt entfernt wurde = IndexOutOfBoundException
            }
        }
        for(ConData t : temp){
            connections.remove(t);
        }
        temp.clear();
    }
    
    public void reset(){
        connections.forEach(d -> d.connectionLine.resetColor());
    }
}