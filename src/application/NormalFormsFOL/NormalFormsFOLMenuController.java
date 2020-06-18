package application.NormalFormsFOL;

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

public class NormalFormsFOLMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public void back(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../FOLMainMenu/FOLMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }

    @FXML
    public void writePrenexTransformation(ActionEvent event) throws IOException {
    	Parent prenexParent=FXMLLoader.load(getClass().getResource("./WritePrenexTransformation.fxml"));
    	Scene prenexScene=new Scene(prenexParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(prenexScene);
    	window.show();
    }

    @FXML
    public void generateTransformation(ActionEvent event) throws IOException {
    	Parent prenexParent=FXMLLoader.load(getClass().getResource("./GenerateTransformation.fxml"));
    	Scene prenexScene=new Scene(prenexParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(prenexScene);
    	window.show();
    }

    @FXML
    void initialize() {

    }
}
