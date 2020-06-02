package application.FormulaAnalysisFOL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import FOLToJSON.JSONFormula;
import Formulas.FOLFormula;
import Util.PythonFunctionEvaluation;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ValidityCheckController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField currentFormula;

    @FXML
    private Label implementations;

    @FXML
    private Label assignation;
    @FXML
    private TextArea message;
    @FXML
    private Label domains;
    private String implementationsPath;
    private String assignationPath;
    private String domainPath;


    @FXML
    void initialize() {
        assert currentFormula != null : "fx:id=\"currentFormula\" was not injected: check your FXML file 'ValidityCheckFOL.fxml'.";
        assert implementations != null : "fx:id=\"implementations\" was not injected: check your FXML file 'ValidityCheckFOL.fxml'.";
        assert assignation != null : "fx:id=\"assignation\" was not injected: check your FXML file 'ValidityCheckFOL.fxml'.";
        assert domains != null : "fx:id=\"domains\" was not injected: check your FXML file 'ValidityCheckFOL.fxml'.";

    }
    
    private void selectFile(int fileType)
    {
    	FileChooser chooser=new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
		File f=chooser.showOpenDialog(null);
		if(f!=null)
		{
			if(fileType==1)
			{
				this.implementations.setText(f.getAbsolutePath());
				this.implementationsPath=f.getAbsolutePath();
			}
			else if(fileType==2)
			{
				this.assignation.setText(f.getAbsolutePath());
				this.assignationPath=f.getAbsolutePath();

			}
			else
			{
				this.domains.setText(f.getAbsolutePath());
				this.domainPath=f.getAbsolutePath();
			}
		}
    }
    public void selectImplementations()
    {
    	this.selectFile(1);
    }
    
    public void selectAssignation()
    {
    	this.selectFile(2);
    }
    
    public void selectDomains()
    {
    	this.selectFile(3);
    }
    
    public void evaluate(int evaluationType)
    {
    	try {
			FOLFormula formula=new FOLFormula(currentFormula.getText());
			if(implementationsPath==null)
			{
				message.setText("Please select an implementations file");

			}
			else if(assignationPath==null)
			{
				message.setText("Please select an assignation file");
			}
			else if(domainPath==null)
			{
				message.setText("Please select a domains file");
			}
			else
			{
				JSONFormula toConvert=null;
				if(evaluationType==1)
				{
					toConvert=new JSONFormula(formula.syntaxTree.getRoot());
				}
				else if(evaluationType==2)
				{
					FOLFormula existentialClosure=new FOLFormula(formula.syntaxTree.existentialClosure());
					toConvert=new JSONFormula(existentialClosure.syntaxTree.getRoot());
				}
				else
				{
					FOLFormula universalClosure=new FOLFormula(formula.syntaxTree.UniversalClosure());
					toConvert=new JSONFormula(universalClosure.syntaxTree.getRoot());
				}
				String result=PythonFunctionEvaluation.execPy(toConvert, implementationsPath, assignationPath, domainPath);
				if(evaluationType==1)
				{
					message.setText(result);
				}
				else if(evaluationType==2)
				{
					if(result.equals("True"))
					{
						result=formula.toString()+" is satisfiable";
					}
					else if(result.equals("False"))
					{
						result=formula.toString()+" is NOT satisfiable";
					}
				}
				else
				{
					if(result.equals("True"))
					{
						result=formula.toString()+" is a tautology";
					}
					else if(result.equals("False"))
					{
						result=formula.toString()+" is NOT a tautology(it is a Contradiction)";
					}
				}
				message.setText(result);
			}
		} catch (InvalidPropositionalLogicFormula | IOException e) {
			message.setText(e.getMessage());
		}
    }
    
    public void evaluateSimple()
    {
    	this.evaluate(1);
    }
    public void evaluateSAT()
    {
    	this.evaluate(2);
    }
    public void evaluateTautology()
    {
    	this.evaluate(3);
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