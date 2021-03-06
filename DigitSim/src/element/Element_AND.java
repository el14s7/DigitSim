/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import toolbox.Draw;
import toolbox.GenFunctions;
import Gestures.NodeGestures;
import connection.HandleState;
import general.State;
import general.Properties;
import static element.Element.elementWidth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *  ----> ACHTUNG: Um dieses Element zu verstehen sollte zuerst "Element.java" (Die Mutterklasse) studiert werden!
 * 
 * @author Dominik
 * -Überarbeitet von Dome 06.11.2016
 * -Überarbeitet von Dome 11.11.2016 (Logik sowie Inputs funktionieren jetzt mit beliebiger größe
 * -Überarbeitet von Dome 11.11.2016
 * -Überarbeitet von Dome 13.11.2016
 */
public class Element_AND extends Element{

    //Globals
    public static final Type TYPE = Type.AND; //Der Typ des Bausteines
   //Die Elemente aus denen der Baustein zusammengestezt ist
    private Label lbl;
    private Element thisElement = this; //Referenz auf sich selbst
    
    //Constructor
    public Element_AND(double pX, double pY, int pInputs, NodeGestures dNodeGestures){ //Baustein zeichnen
        outputs = new int[]{0}; //Outputs

        //Überarbeitet von Elias
        //Der Baustein wird nun (egal bei welcher BausteinWeite/Höhe) plaziert mit der Maus als Mittelpunkt
        pX = pX-elementWidth/2;
        pY = pY-elementHeight/2;
        numOutputs = 1;
        rec = Draw.drawRectangle(pX, pY, elementWidth, elementHeight, 10, 10, Color.BLACK, Properties.getElementOpacity(), 5);           //das AND zeichnen
        rec.addEventFilter(MouseEvent.MOUSE_ENTERED, NodeGestures.getOverNodeMouseHanlderEnterRec(this));
        rec.addEventFilter(MouseEvent.MOUSE_EXITED, NodeGestures.getOverNodeMouseHanlderExitRec(this));
        lbl = Draw.drawLabel((pX + 10), (pY - 15), "&", Color.BLACK, false, 75);
        outputLines.add(Draw.drawLine((pX + 85), (pY + 29.5), (pX + 90), (pY + 29.5), Color.BLACK, 5));
        outputLines.get(0).addEventFilter(MouseEvent.MOUSE_ENTERED, NodeGestures.getOverNodeMouseHanlderEnter());
        outputLines.get(0).addEventFilter(MouseEvent.MOUSE_EXITED, NodeGestures.getOverNodeMouseHanlderExit());
        outputLines.get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, NodeGestures.getOverOutputMouseHanlderClicked(this, 0));
        outputs[0] = 3;
        
            numInputs = pInputs;
            inputs = new int[numInputs];
            Arrays.fill(inputs, 0); //Setzt alle Inputs auf '0'
            grp = new Group(lbl, rec, outputLines.get(0));
            for(int i = 0; i < numInputs; i++)
            {
                //  * Überarbeitet von Tim 05.11.16
                //  * Überarbeitet von Tim 21.11.16
                // korrekte stelle für jeden eingang berechnen, egal wie viele eingänge
                // *Überarbeitet von Elias 11.11.16
                // Bausteine passen sich nun automatisch mit ihrer Höhe an die anzahl der Eingänge an
                inputs[i] = 3;
                double gridOffset = (double) Properties.GetGridOffset();
                
                if(rec.getHeight() <= (numInputs) * gridOffset) {
                    rec.setHeight((numInputs) * gridOffset);
                }
                double offsetY = i * gridOffset + gridOffset - 12.5;
                
                inputLines.add(Draw.drawLine((pX - 5), pY + offsetY, (pX - 10), pY + offsetY, Color.BLACK, 5)); //Linie zeichnen
                inputLines.get(i).addEventFilter(MouseEvent.MOUSE_ENTERED, NodeGestures.getOverNodeMouseHanlderEnter());
                inputLines.get(i).addEventFilter(MouseEvent.MOUSE_EXITED, NodeGestures.getOverNodeMouseHanlderExit());
                inputLines.get(i).addEventFilter(MouseEvent.MOUSE_CLICKED, NodeGestures.getOverInputMouseHanlderClicked(this, i));
                grp.getChildren().add(inputLines.get(i)); //Linie hinzufügen
            }
            
          //Die Hanlder hinzufügenn (Beschreibung der Hander in  DraggableCanvas.java)
        grp.addEventFilter( MouseEvent.MOUSE_PRESSED, dNodeGestures.getOnMousePressedEventHandler());
        grp.addEventFilter( MouseEvent.MOUSE_DRAGGED, dNodeGestures.getOnMouseDraggedEventHandler());
        grp.addEventFilter( MouseEvent.MOUSE_RELEASED, dNodeGestures.getOnMouseReleasedEventHandler());
    }
    
    //Diese Methoden müssen überschrieben werden (Beschreibung in der Mutterklasse)
    @Override
    public void update(){ 
        List<State> states = new ArrayList();
        
        for(int input : inputs) {
            states.add(HandleState.getState(input));
        }
    
        
        State result = HandleState.logicAND(states);
        outputs[0] = HandleState.getIntFromState(result);
//        boolean logic = true;
//        for(int i = 0; i < numInputs; i++){ //Eingänge durchiterieren & Logik überprüfen
//            if(inputs[i] == 0){
//                logic = false;
//            }
//        }
//        if(logic){
//            outputs[0] = 1;
////            outputLines.get(0).setStroke(Color.RED);
//        }else{
//            outputs[0] = 0;
////            outputLines.get(0).setStroke(Color.BLUE);
//        }      
    }
    
    @Override
    public void showProperties(){ //Zeigt das "Eigenschaften"-Fenster für dieses Element
        GenFunctions.showBasicElementProperties(numInputs, thisElement);
    }
    
    @Override
    public String getTypeName() {
        return TYPE.name();
    }
}
