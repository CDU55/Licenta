package application.FormulaAnalysisFOL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindSubstitutionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField initialFormula;

    @FXML
    private TextField finalFormula;

    @FXML
    private TextArea result;

    @FXML
    void initialize() {
        assert initialFormula != null : "fx:id=\"initialFormula\" was not injected: check your FXML file 'FindSubstitutionFOL.fxml'.";
        assert finalFormula != null : "fx:id=\"finalFormula\" was not injected: check your FXML file 'FindSubstitutionFOL.fxml'.";
        assert result != null : "fx:id=\"result\" was not injected: check your FXML file 'FindSubstitutionFOL.fxml'.";

    }
    
    public void find()
    {
    	try {
			FOLFormula initial=new FOLFormula(this.initialFormula.getText());
			FOLFormula Final=new FOLFormula(this.finalFormula.getText());
			SubstitutionsResult s=SubstitutionsFinder.findSubstitutions(initial, Final);
			this.result.setText(s.toString());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			this.result.setText(e.getMessage());
		}
    }
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent analysisParent=FXMLLoader.load(getClass().getResource("FormulaAnalysisMenu.fxml"));
    	Scene analysisScene=new Scene(analysisParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(analysisScene);
    	window.show();
    }
}