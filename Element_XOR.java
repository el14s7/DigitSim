/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitsim;

import java.util.Arrays;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Elias (Nach Dominiks AND - Vorlage)
 * -Überarbeitet von Dome 11.11.2016
 */
public class Element_XOR extends Element{

    //Globals
    public static final String TYPE = "XOR"; //Der Typ des Bausteines
    //Die Elemente aus denen der Baustein zusammengestezt ist
    private Label lbl;
    private Label lbl2;
    
    //Constructor
    public Element_XOR(double pX, double pY, int pInputs, NodeGestures dNodeGestures){//Baustein zeichnen
        outputs = new int[]{0}; //Outputs
        
        //Der Baustein wird (egal bei welcher BausteinWeite/Höhe) plaziert mit der Maus als Mittelpunkt
        pX = pX-elementWidth/2;
        pY = pY-elementHeight/2;
        numOutputs = 1;
        rec = Draw.drawRectangle(pX, pY, elementWidth, elementHeight, 10, 10, Color.BLACK, 0.4, 5);           //das OR zeichnen
        lbl = Draw.drawLabel((pX+2), (pY - 12), "=" , Color.BLACK, false, 75);
        lbl2 = Draw.drawLabel((pX+40), (pY - 7), "1" , Color.BLACK, false, 75);
        outputLines.add(Draw.drawLine((pX + 80), (pY + 40), (pX + 100), (pY + 40), Color.BLACK, 5)); 
        
            numInputs = 2; // 2 Da mehr keinen Sinn machen
            inputs = new int[numInputs];
            Arrays.fill(inputs, 0); //Setzt alle Inputs auf '0
            grp = new Group(rec, lbl, outputLines.get(0), lbl2);
            for(int i = 0; i < numInputs; i++)
            {
                //  * Überarbeitet von Tim 05.11.16
                // korrekte stelle für jeden eingang berechnen, egal wie viele eingänge
                // *Überarbeitet von Elias 11.11.16
                // Bausteine passen sich nun automatisch mit ihrer Höhe an die anzahl der Eingänge an
                if(rec.getHeight() <= (numInputs+1)*21) {
                    rec.setHeight((numInputs+1)*21);
                }
                double offsetY = i*21 + 21 - 1;
                
                inputLines.add(Draw.drawLine((pX - 5), pY + offsetY, (pX - 15), pY + offsetY, Color.BLACK, 5));
                grp.getChildren().add(inputLines.get(i));
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
            outputs[0] = 0;
            outputLines.get(0).setStroke(Color.BLACK);
        }else{
            outputs[0] = 1;
            outputLines.get(0).setStroke(Color.RED);
        }  
    }
    
     @Override
    public void showProperties(){ //Zeigt das "Eigenschaften"-Fenster für dieses Element
        JOptionPane.showMessageDialog(null,
			    "'XOR' besitzt keine Eigenschaften",
			    "Info",
			    JOptionPane.INFORMATION_MESSAGE);
    }
}
