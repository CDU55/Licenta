package application.NaturalDeductionPropLogic;

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

public class MenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../PropositionalLogicMainMenu/PropositionalLogicMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
    
    public void writeProof(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("WriteProofPropLogicArguments.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
    public void checkProof(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("CheckProofPropLogic.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
}

