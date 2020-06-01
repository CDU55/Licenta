package application.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button PropositionalLogicBtn;

    @FXML
    private Button FirstOrderLogicBtn;

    public void PropositionalLogicBtnPushed(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../PropositionalLogicMainMenu/PropositionalLogicMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
    
    public void FirstOrderLogicBtnPushed(ActionEvent event) throws IOException
    {
    	Parent FOLParent=FXMLLoader.load(getClass().getResource("../FOLMainMenu/FOLMainMenu.fxml"));
    	Scene FOLScene=new Scene(FOLParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(FOLScene);
    	window.show();
    }
    @FXML
    void initialize() {
        assert PropositionalLogicBtn != null : "fx:id=\"PropositionalLogicBtn\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert FirstOrderLogicBtn != null : "fx:id=\"FirstOrderLogicBtn\" was not injected: check your FXML file 'MainMenu.fxml'.";

    }
}
