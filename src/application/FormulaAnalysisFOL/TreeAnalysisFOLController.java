package application.FormulaAnalysisFOL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import Formulas.FOLFormula;
import Formulas.Formula;
import application.FormulaAnalysis.TreeDraw;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TreeAnalysisFOLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField currentFormula;

    @FXML
    private TextArea result;

    public void back(ActionEvent event) throws IOException
    {
    	Parent analysisParent=FXMLLoader.load(getClass().getResource("FormulaAnalysisMenu.fxml"));
    	Scene analysisScene=new Scene(analysisParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(analysisScene);
    	window.show();
    }
    
    public void treeSize()
    {
    	try {
			FOLFormula formula=new FOLFormula(this.currentFormula.getText());
			result.setText("Tree Size : "+String.valueOf(formula.syntaxTree.getSize()));
		} catch (InvalidPropositionalLogicFormula e) {
			result.setText(e.getMessage());
		}
    }
    
    public void treeHeight()
    {
    	try {
			FOLFormula formula=new FOLFormula(this.currentFormula.getText());
			result.setText("Tree Heigth : "+String.valueOf(formula.syntaxTree.getHeight()));
		} catch (InvalidPropositionalLogicFormula e) {
			result.setText(e.getMessage());
		}
    }
    
    public void removeImplications()
    {
    	try {
			FOLFormula formula=new FOLFormula(this.currentFormula.getText());
			formula.syntaxTree.reaplceImplications(formula.syntaxTree.getRoot());
			this.currentFormula.setText(formula.syntaxTree.toString());
		} catch (InvalidPropositionalLogicFormula e) {
			result.setText(e.getMessage());
		}
    }

    public void subformulas()
    {
    	try {
			FOLFormula formula=new FOLFormula(currentFormula.getText());
			result.setText(formula.syntaxTree.getSubfExplanation().toString());
		} catch (InvalidPropositionalLogicFormula e1) {
			result.setText(e1.getMessage());
		}
    }
    
    public void variables()
    {
    	try {
			FOLFormula formula=new FOLFormula(currentFormula.getText());
			result.setText(formula.syntaxTree.getVarsExplanation().toString());
		} catch (InvalidPropositionalLogicFormula e1) {
			result.setText(e1.getMessage());
		}
    }
    
    public void draw()
    {
    	try {
			FOLFormula formula=new FOLFormula(currentFormula.getText());
			TreeDrawFOL.draw(formula);
		} catch (InvalidPropositionalLogicFormula e1) {
			result.setText(e1.getMessage());
		}
    }
    @FXML
    void initialize() {
        assert currentFormula != null : "fx:id=\"currentFormula\" was not injected: check your FXML file 'TreeAnalysisFOL.fxml'.";
        assert result != null : "fx:id=\"result\" was not injected: check your FXML file 'TreeAnalysisFOL.fxml'.";

    }
}