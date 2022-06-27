/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodGrassiDiff;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrassi"
    private Button btnGrassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");

    	try {
    		int porzioni = Integer.parseInt(txtPorzioni.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			txtResult.clear();
			txtResult.appendText("\nDevi inserire un numero intero che indichi le porzioni!");
		}
    	
		int porzioni = Integer.parseInt(txtPorzioni.getText());
    	
    	this.model.creaGrafo(porzioni);
    	for(Food f : this.model.listaDeiVertici()) {
    		this.boxFood.getItems().add(f);
    	}
    	
    	txtResult.appendText("\nGrafo creato!");
    	txtResult.appendText("\nN° vertici: " + this.model.nVertici().toString());
    	txtResult.appendText("\nN° archi: " + this.model.nArchi().toString());
    }

    @FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi grassi...");
    	
    	Food food = boxFood.getValue();
    	
    	if(food == null)
    		txtResult.appendText("\nSelezionare un cibo dalla tendina!");
    	
    	for(int i = 0; i <= 4; i++  ) {
    		txtResult.appendText("\n" + this.model.listaFoodDiff(food).get(i).getFood().toString() + " - " + this.model.listaFoodDiff(food).get(i).getDifferenzaGrassi());
    	}
    	
    		
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
