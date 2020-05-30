package application.FormulaAnalysis;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import Formulas.Formula;
import PropositionalLogicAnalysis.ContradictionChecker;
import PropositionalLogicAnalysis.SatisfiabilityChecker;
import PropositionalLogicAnalysis.TableGenerator;
import PropositionalLogicAnalysis.TautologyChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PropFormulaAnalysisController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField formulaField;

    @FXML
    private TextArea console;

    @FXML
    void initialize() {
        assert formulaField != null : "fx:id=\"formulaField\" was not injected: check your FXML file 'FormulaAnalysis.fxml'.";
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'FormulaAnalysis.fxml'.";

    }
    
    public void CheckSyntax()
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			console.setText("Valid formula");
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void subformulas()
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			console.setText(formula.syntaxTree.getSubfExplanation().toString());
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void vars( )
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			console.setText(formula.syntaxTree.getVarsExplanation().toString());
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void removeImplications( )
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			formula.replaceImplications();
			formulaField.setText(formula.toString());
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void treeSize( )
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			int size=formula.syntaxTree.getSize();
			console.setText("Tree size : "+size);
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void treeHeight( )
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			int height=formula.syntaxTree.getHeight();
			console.setText("Tree height : "+height);
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void checkSat( )
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			boolean evaluationResult=formula.analyse(new SatisfiabilityChecker());
			if(evaluationResult)
			{
				console.setText(formula.toString()+" is satisfiable");
			}
			else
			{
				console.setText(formula.toString()+" is NOT satisfiable");

			}
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void draw()
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			TreeDraw.draw(formula);
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    public void checkTautology()
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			boolean evaluationResult=formula.analyse(new TautologyChecker());
			if(evaluationResult)
			{
				console.setText(formula.toString()+" is a tautology");
			}
			else
			{
				console.setText(formula.toString()+" is NOT a tautology");

			}
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void checkContradiction( )
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			boolean evaluationResult=formula.analyse(new ContradictionChecker());
			if(evaluationResult)
			{
				console.setText(formula.toString()+" is a contradiction");
			}
			else
			{
				console.setText(formula.toString()+" is NOT a contradiction (is satisfiable)");

			}
		} catch (InvalidPropositionalLogicFormula e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void generateTable()
    {
    	try {
			Formula formula=new Formula(formulaField.getText());
			FileChooser chooser=new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("HTML Files","*.html"));
			File f=chooser.showOpenDialog(null);
			if(f!=null)
			{
				TableGenerator.generateTable(formula, f.getAbsolutePath());
			}
			console.setText("Table generated");
		} catch (InvalidPropositionalLogicFormula | IOException e1) {
			// TODO Auto-generated catch block
			console.setText(e1.getMessage());
		}
    }
    
    public void back(ActionEvent e) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../PropositionalLogicMainMenu/PropositionalLogicMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)e.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
}
