/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import digitsim.Draw;
import digitsim.GenFunctions;
import Gestures.NodeGestures;
import digitsim.Properties;
import element.Element;
import static element.Element.elementWidth;
import java.util.Arrays;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author Elias (Nach Dominiks AND - Vorlage)
 * -Überarbeitet von Dome 11.11.2016
 * -Überarbeitet von Dome 13.11.2016
 */
public class Element_NOR extends Element{

    //Globals
    public static final String TYPE = "NOR"; //Der Typ des Bausteines
    //Die Elemente aus denen der Baustein zusammengestezt ist
    private Label lbl;
    private Label lbl2;
    private Line lUnderL;
    private Circle cOutput;
    private Element thisElement = this; //Referenz auf sich selbst
    
    //Constructor
    public Element_NOR(double pX, double pY, int pInputs, NodeGestures dNodeGestures){//Baustein zeichnen
        outputs = new int[]{0}; //Outputs
        
        //Der Baustein wird (egal bei welcher BausteinWeite/Höhe) plaziert mit der Maus als Mittelpunkt
        pX = pX-elementWidth/2;
        pY = pY-elementHeight/2;
        numOutputs = 1;
        rec = Draw.drawRectangle(pX, pY, elementWidth, elementHeight, 10, 10, Color.BLACK, Properties.getElementOpacity(), 5);           //das OR zeichnen
        rec.addEventFilter(MouseEvent.MOUSE_ENTERED, GenFunctions.getOverNodeMouseHanlderEnterRec());
        rec.addEventFilter(MouseEvent.MOUSE_EXITED, GenFunctions.getOverNodeMouseHanlderExitRec());
        lbl = Draw.drawLabel((pX+2), (pY - 17), ">" , Color.BLACK, false, 75);
        lbl2 = Draw.drawLabel((pX+40), (pY - 15), "1" , Color.BLACK, false, 75);
        lUnderL = Draw.drawLine(pX+14.5, pY+63, pX+42, pY+63, Color.BLACK, 5);
        outputLines.add(Draw.drawLine((pX + 95), (pY + 29.5), (pX + 100), (pY + 29.5), Color.BLACK, 5)); 
        outputLines.get(0).addEventFilter(MouseEvent.MOUSE_ENTERED, GenFunctions.getOverNodeMouseHanlderEnter());
        outputLines.get(0).addEventFilter(MouseEvent.MOUSE_EXITED, GenFunctions.getOverNodeMouseHanlderExit());
        cOutput = Draw.drawCircle(pX+88, pY+29.5, 5, Color.BLACK, 5, false, 5);
        
            numInputs = pInputs;
            inputs = new int[numInputs];
            Arrays.fill(inputs, 0); //Setzt alle Inputs auf '0;
            grp = new Group(lbl, outputLines.get(0), lUnderL, lbl2, cOutput, rec);
            for(int i = 0; i < numInputs; i++)
            {
                //  * Überarbeitet von Tim 05.11.16
                //  * Überarbeitet von Tim 21.11.16
                // korrekte stelle für jeden eingang berechnen, egal wie viele eingänge
                // *Überarbeitet von Elias 11.11.16
                // Bausteine passen sich nun automatisch mit ihrer Höhe an die anzahl der Eingänge an
                
                double gridOffset = (double) Properties.GetGridOffset();
                
                if(rec.getHeight() <= (numInputs) * gridOffset) {
                    rec.setHeight((numInputs) * gridOffset);
                }
                double offsetY = i * gridOffset + gridOffset - 11.5;
                
                inputLines.add(Draw.drawLine((pX - 5), pY + offsetY, (pX - 15), pY + offsetY, Color.BLACK, 5));
                inputLines.get(i).addEventFilter(MouseEvent.MOUSE_ENTERED, GenFunctions.getOverNodeMouseHanlderEnter());
                inputLines.get(i).addEventFilter(MouseEvent.MOUSE_EXITED, GenFunctions.getOverNodeMouseHanlderExit());
                grp.getChildren().add(inputLines.get(i));
            }
            
         //Die Hanlder hinzufügenn (Beschreibung der Hander in  DraggableCanvas.java)  
        grp.addEventFilter( MouseEvent.MOUSE_PRESSED, dNodeGestures.getOnMousePressedEventHandler());
        grp.addEventFilter( MouseEvent.MOUSE_DRAGGED, dNodeGestures.getOnMouseDraggedEventHandler());
        grp.addEventFilter( MouseEvent.MOUSE_RELEASED, dNodeGestures.getOnMouseReleasedEventHandler());
    }
    
    //Diese Methoden müssen überschrieben werden (Beschreibung in der Mutterklasse)
    @Override
    public void update(){
        boolean logic = false;
        for(int i = 0; i < numInputs; i++){ //Eingänge durchiterieren & Logik überprüfen
            if(inputs[i] == 1){
                logic = true;
            }
        }
        if(logic){                             
            outputs[0] = 0;
            outputLines.get(0).setStroke(Color.BLACK);
        }else{
            outputs[0] = 1;
            outputLines.get(0).setStroke(Color.RED);
        }  
    }
    
    @Override
    public void showProperties(){ //Zeigt das "Eigenschaften"-Fenster für dieses Element
        GenFunctions.showBasicElementProperties(numInputs, thisElement);
    }
}
