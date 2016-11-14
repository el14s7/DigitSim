/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitsim;

import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        rec = Draw.drawRectangle(pX, pY, elementWidth, elementHeight, 10, 10, Color.BLACK, 0.4, 5);           //das OR zeichnen
        lbl = Draw.drawLabel((pX+2), (pY - 17), ">" , Color.BLACK, false, 75);
        lbl2 = Draw.drawLabel((pX+40), (pY - 15), "1" , Color.BLACK, false, 75);
        lUnderL = Draw.drawLine(pX+14.5, pY+63, pX+42, pY+63, Color.BLACK, 5);
        outputLines.add(Draw.drawLine((pX + 95), (pY + 40), (pX + 100), (pY + 40), Color.BLACK, 5)); 
        cOutput = Draw.drawCircle(pX+88, pY+40, 5, Color.BLACK, 5, false, 5);
        
            numInputs = pInputs;
            inputs = new int[numInputs];
            Arrays.fill(inputs, 0); //Setzt alle Inputs auf '0;
            grp = new Group(rec, lbl, outputLines.get(0), lUnderL, lbl2, cOutput);
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
        Stage stage = new Stage(StageStyle.DECORATED); //Ein Fenster für die Eigenschaften erstellen und Anzeigen
        stage.setTitle("Eigenschaften"); //titel
        Label lbl = new Label("Inputs:");
        lbl.setTranslateX(5); //X/Y Koords
        lbl.setTranslateY(5);
        lbl.setFont(new Font(14)); //Schriftgröße
        lbl.setPrefHeight(20); //Größe
        lbl.setPrefWidth(50);
        TextField tf = new TextField(){ //textfeld um die inputs einzugeben
            @Override public void replaceText(int start, int end, String text) {//Diese funktionen werden ausgeführt wenn man den text ändert
                if (text.matches("[0-9]") || text == "") {//Überorüfen ob nur zahlen eingegeben wurden
                   super.replaceText(start, end, text);//Ja
                }else{
                    this.setText("");//nein, also textfeld leeren
                }
            }
     
           @Override public void replaceSelection(String text) {//Selbe
               if (text.matches("[0-9]") || text == "") {
               super.replaceSelection(text);
               }else{
                    this.setText("");
                }
           }
        };
        tf.setText(String.valueOf(numInputs));
        tf.setTranslateX(50);
        tf.setTranslateY(5);
        tf.setPrefHeight(20);
        tf.setPrefWidth(50);
        Button btn = new Button("Übernehmen");
        btn.setTranslateX(5);
        btn.setTranslateY(40);
        btn.setPrefHeight(20);
        btn.setPrefWidth(100);
        btn.setOnAction(new EventHandler<ActionEvent>(){//Wird bei "Übernehmen" ausgeführt
            @Override
            public void handle(ActionEvent e){
                int pInputs = Integer.parseInt(tf.getText().trim());//String zu Integer
                if(pInputs < 9 && pInputs > 1 && pInputs != numInputs){//Testen ob die inputs sinn machen und sich geändert haben
                    DigitSimController.rebuildElement(thisElement, pInputs);//Element ändern
                }else{
                    tf.setText(String.valueOf(numInputs));//zurücksetzen
                } 
                stage.close();
            }         
        });
       Scene scene = new Scene(new Group(lbl, tf, btn));
       stage.setScene(scene);
       stage.show();//Fenster zeigen
    }
}
