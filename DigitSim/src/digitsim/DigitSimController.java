package digitsim;

import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Digitsim.fxml Controller class
 *
 * @author Elias
 * -Überarbeitet von Dominik 22.10.2016
 */
public class DigitSimController extends Pane{
    //************************ Globals ********************************
    DraggableCanvas simCanvas = new DraggableCanvas();
    ArrayList<Element> elements = new ArrayList<>();
    NodeGestures nodeGestures;
    SceneGestures sceneGestures;
    String selectedElement = "NONE";
     /**
     * FXML OBJEKT-Erstellungs-Bereich:
     * Jedes Element, welches in der DigitSim.FXML verwendet wird, muss in folgendem wege im Code noch erstellt werden.
     */
    @FXML 
    private MenuItem mItemOpenFile;
    @FXML 
    private ToggleButton btnAND;    
    @FXML
    private ToggleButton btnOR;    
    @FXML
    private ToggleButton btnNOT;
    @FXML
    private ToggleButton btnNOR;    
    @FXML
    private ToggleButton btnXOR;    
    @FXML
    private ToggleButton btnNAND;
    @FXML
    private AnchorPane simPane;
    @FXML
    private Button btnStart;
    
    //Constructor
    public DigitSimController() {
    }   
    
    @FXML
    public void initialize() {//initialize Funktion: wird direkt beim Starten der FXML aufgerufen.
        addSimCanvas();
        
        simCanvas.addGrid(simCanvas.getPrefWidth(), simCanvas.getPrefHeight());
        
        loadBtnGroup();  
        
        //Verschiebt simCanvas ein bisschen
        simCanvas.translateXProperty().set(25);
        simCanvas.translateYProperty().set(25);
               
        nodeGestures = new NodeGestures( simCanvas);
        sceneGestures = new SceneGestures(simCanvas, simPane);            
        
        //EVENT FILTER
        simPane.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        simPane.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        simPane.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        
        
        AND and0 = new AND(simCanvas);
    }
    
    @FXML
    private void addSimCanvas() {
        /**
         * Author: Dominik
         * 
         * Sobalt man klickt wird ein neuer Baustein hinzugefügt
         */
        simCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if(event.isPrimaryButtonDown() && !isMouseOverNode()){
                    addElement(event);
                }
            }
        });
        simPane.getChildren().addAll(simCanvas);
    }
      

    /**
    * Bildet nötige Gruppen für Togglebuttons (damit immer nur einer Selected sein kann)
    * 
    * @author Elias
    * 
    */
    public void loadBtnGroup() {
        ToggleGroup group = new ToggleGroup();
        btnAND.setToggleGroup(group);
        btnOR.setToggleGroup(group);
        btnNOT.setToggleGroup(group);
        btnNAND.setToggleGroup(group);
        btnNOR.setToggleGroup(group);
        btnXOR.setToggleGroup(group);
        
    }
    //*********ON ACTION bereich: wird verwendet um z.B. Buttonclicks auszuwerten**********
    /**
    * Öffnet einen Filebrowser um eine Datei zu Öffnen.
    * 
    * @author Elias
    * -Bearbeitet von Dominik 22.10.16
    * -Bearbeitet von Tim 23.10.16
    * -Bearbeitet von Dominik 31.10.16
    */
    public void mItemCloseAction(ActionEvent event){
        System.exit(0);
    }
    
    public void mItemOpenFileAction(ActionEvent event) {        
        File selectedFile = chooseFile("DigitSimFiles (*.dgs)", "*.dgs");  //Datei Auswählen
        //Öffnen der Datei (comming)
    }
    public void mItemPropertiesOnAction(ActionEvent event) {
        Stage stage;
        stage = GenFunctions.openFXML("Properties.fxml", "Einstellungen", "icon.png"); //Öffnen des "Einstellungen"-Fensters
        stage.setResizable(false);
    }
    public void mItemHelpOnAction(ActionEvent event) {
        Stage stage;
        stage = GenFunctions.openFXML("Help.fxml", "Hilfe", "icon.png"); //Öffnen des "Hilfe"-Fensters
        stage.setWidth(600);
        stage.setResizable(false);
    }
    public void btnStartOnAction(ActionEvent event) {
        System.out.printf("simCanvas Weite: %.1f\n", simCanvas.getWidth());
        System.out.printf("simCanvas Höhe: %.1f\n", simCanvas.getHeight());
        System.out.printf("simCanvas TranslateX: %.1f\n", simCanvas.getTranslateX());
        System.out.printf("simCanvas TranslateY: %.1f\n", simCanvas.getTranslateY());
        System.out.printf("simCanvas Scale: %.1f\n", simCanvas.getScale());
    }
    
    public File chooseFile(String description, String extension){ //Die Funktion öffnet einen Filebrowser um eine Datei auszuwählen und lädt dise anschließend.
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extension);
        fc.getExtensionFilters().add(extFilter);
        File selectedFile = fc.showOpenDialog(null);  
        return selectedFile;
    }
    
    public void element_selection_AND(ActionEvent event){
            if(selectedElement != Element_AND.TYPE){
                selectedElement = Element_AND.TYPE;
            }else{
                selectedElement = "NONE";
            }
    }
    
    /**
     * 
     * Author: Dominik
     * 
     * Fügt einen neuen Baustein hinzu
     */
    public void addElement(MouseEvent event){
      if(selectedElement == Element_AND.TYPE){
        elements.add(new Element_AND(event.getX(), event.getY(), 2, nodeGestures));
        simCanvas.getChildren().add(elements.get(elements.size() - 1).getGroup());  
      } 
    }
    
    /**
     * Gibt zurück ob sich die Maus über einer Node (Element) befindet
     * !!!Hat noch keine Funktuion!!! (Muss noch gefüllt werden)
     */
    public boolean isMouseOverNode() {
        return false;
    }

}
      
