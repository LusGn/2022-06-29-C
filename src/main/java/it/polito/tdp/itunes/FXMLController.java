/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	Album a1=this.cmbA1.getValue();
    	this.txtResult.clear();
    	if(a1==null) {
    		txtResult.setText("Scegliere un album!");
    	}
    	
    	txtResult.setText(model.calcolaAdiacenza(a1));
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	Album a2=this.cmbA2.getValue();
    	Album a1=this.cmbA1.getValue();
    	this.txtResult.clear();
    	if(a2==null || a1==null) {
    		txtResult.setText("Scegliere un album!");
    	}
    	try {
    		double x=Double.parseDouble(this.txtX.getText());
    		txtResult.setText(model.calcolaPercorso(a1, a2, x));
    		
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserire Numero valido!!");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	try {
    		double price=Double.parseDouble(this.txtN.getText());
    		model.creaGrafo(price);
    		txtResult.setText("Grafo creato!!! \n");
    		txtResult.appendText("#Numero vertici: "+model.getVerticiSize()+"\n");
    		txtResult.appendText("#Numero archi: "+model.getArcoSize()+"\n");
    		this.cmbA1.getItems().clear();
    		this.cmbA2.getItems().clear();
    		this.cmbA1.getItems().addAll(model.getVertici());
    		this.cmbA2.getItems().addAll(model.getVertici());
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero valido!!!");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
