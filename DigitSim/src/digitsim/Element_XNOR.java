/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitsim;

import static digitsim.Element.elementWidth;
import java.util.Arrays;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *  ----> ACHTUNG: Um dieses Element zu verstehen sollte zuerst "Element.java" (Die Mutterklasse) studiert werden!
 * 
 * @author Luca, Camillo (17.11.2016)


 */
public class Element_XNOR extends Element{

    //Globals
    public static final String TYPE = "XNOR"; //Der Typ des Bausteines
   //Die Elemente aus denen der Baustein zusammengestezt ist
    private Label lbl;
    private Element thisElement = this; //Referenz auf sich selbst
    
    //Constructor
    public Element_XNOR(double pX, double pY, int pInputs, NodeGestures dNodeGestures){ //Baustein zeichnen
        outputs = new int[]{0}; //Outputs

       
        //Der Baustein wird nun (egal bei welcher BausteinWeite/Höhe) plaziert mit der Maus als Mittelpunkt
        pX = pX-elementWidth/2;
        pY = pY-elementHeight/2;
        numOutputs = 1;
        rec = Draw.drawRectangle(pX, pY, elementWidth, elementHeight, 10, 10, Color.BLACK, 0.5, 5);           //das XNOR zeichnen
        lbl = Draw.drawLabel((pX + 15), (pY - 20), "=", Color.BLACK, false, 75);
        outputLines.add(Draw.drawLine((pX + 85), (pY + 29.5), (pX + 100), (pY + 29.5), Color.BLACK, 5));

        
            numInputs = pInputs;
            inputs = new int[numInputs];
            Arrays.fill(inputs, 0); //Setzt alle Inputs auf '0'
            grp = new Group(rec, lbl, outputLines.get(0));
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
                
                inputLines.add(Draw.drawLine((pX - 5), pY + offsetY, (pX - 15), pY + offsetY, Color.BLACK, 5)); //Linie zeichnen
                grp.getChildren().add(inputLines.get(i)); //Linie hinzufügen
            }
            
          //Die Hanlder hinzufügenn (Beschreibung der Hander in  DraggableCanvas.java)
        grp.addEventFilter( MouseEvent.MOUSE_PRESSED, dNodeGestures.getOnMousePressedEventHandler());
        grp.addEventFilter( MouseEvent.MOUSE_DRAGGED, dNodeGestures.getOnMouseDraggedEventHandler());
    }
    
    //Diese Methoden müssen überschrieben werden (Beschreibung in der Mutterklasse)
    @Override
    public void update(){ 
        boolean allON = true;
        boolean allOFF = true;
        for(int i = 0; i < numInputs; i++){ //Eingänge durchiterieren & Logik überprüfen
            if(inputs[i] == 0){
                allON = false;
            }else{
                allOFF = false;
            }
        }
         if(allON || allOFF){
            outputs[0] = 1;
            outputLines.get(0).setStroke(Color.RED);
        }else{
            outputs[0] = 0;
            outputLines.get(0).setStroke(Color.BLACK);
        }    
    }
    
    @Override
    public void showProperties(){ //Zeigt das "Eigenschaften"-Fenster für dieses Element
        GenFunctions.showBasicElementProperties(numInputs, thisElement);
    }
}