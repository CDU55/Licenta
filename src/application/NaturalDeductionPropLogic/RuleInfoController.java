package application.NaturalDeductionPropLogic;
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
        assert link != null : "fx:id=\"link\" was not injected: check your FXML file 'InferenceRulesInfo.fxml'.";

    }
    public void accesLink()
    {
    	Main.webService.showDocument(link.getText());  	
    }
}