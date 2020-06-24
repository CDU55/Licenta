package application.NormalFormsFOL;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class RuleInfoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Hyperlink link;
    @FXML
    void initialize() {

    }
    
    public void accesLink()
    {
    	Main.webService.showDocument(link.getText());  	
    }
}
