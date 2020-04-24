package application.NormalFormsPropLogic;

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

public class NormalFormsMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    
    public void fnc(ActionEvent event) throws IOException
    {
    	Parent fncParent=FXMLLoader.load(getClass().getResource("FNC.fxml"));
    	Scene fncScene=new Scene(fncParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(fncScene);
    	window.show();
    }
    
    public void fnd(ActionEvent event) throws IOException
    {
    	Parent fncParent=FXMLLoader.load(getClass().getResource("FND.fxml"));
    	Scene fncScene=new Scene(fncParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(fncScene);
    	window.show();
    }
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../PropositionalLogicMainMenu/PropositionalLogicMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
}