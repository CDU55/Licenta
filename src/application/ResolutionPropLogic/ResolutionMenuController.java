package application.ResolutionPropLogic;

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

public class ResolutionMenuController {

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
    	Parent writeProofParent=FXMLLoader.load(getClass().getResource("ResolutionWriteProof.fxml"));
    	Scene writeProofScene=new Scene(writeProofParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(writeProofScene);
    	window.show();
    }
    
    public void checkProof(ActionEvent event) throws IOException
    {
    	Parent checkProofParent=FXMLLoader.load(getClass().getResource("CheckProofResolutionPropLogic.fxml"));
    	Scene checkProofScene=new Scene(checkProofParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(checkProofScene);
    	window.show();
    }
    
    
}