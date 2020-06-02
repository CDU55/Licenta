package application.FormulaAnalysisFOL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FomulaAnalysisMenuController {
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    void initialize() {

	    }
	    
	    public void back(ActionEvent event) throws IOException
	    {
	    	Parent FOLParent=FXMLLoader.load(getClass().getResource("../FOLMainMenu/FOLMainMenu.fxml"));
	    	Scene FOLScene=new Scene(FOLParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(FOLScene);
	    	window.show();
	    }
	    
	    public void TreeAnalysis(ActionEvent event) throws IOException
	    {
	    	Parent FOLParent=FXMLLoader.load(getClass().getResource("TreeAnalysisFOL.fxml"));
	    	Scene FOLScene=new Scene(FOLParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(FOLScene);
	    	window.show();
	    }
	    public void FindSubstitution(ActionEvent event) throws IOException
	    {
	    	Parent FOLParent=FXMLLoader.load(getClass().getResource("FindSubstitutionFOL.fxml"));
	    	Scene FOLScene=new Scene(FOLParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(FOLScene);
	    	window.show();
	    }
	    
	    public void ValueAnalysis(ActionEvent event) throws IOException
	    {
	    	Parent FOLParent=FXMLLoader.load(getClass().getResource("ValidityCheckFOL.fxml"));
	    	Scene FOLScene=new Scene(FOLParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(FOLScene);
	    	window.show();
	    }
}
