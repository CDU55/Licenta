package application.NaturalDeductionPropLogic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import NaturalDeduction.ProofReader;
import PropositionalLogicAnalysis.TableGenerator;
import PropositionalLogicFormula.Formula;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class CheckProofPropLogicController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea console;
    
    private String lastResult;

    public void check()
    {
    	try {
			if(lastResult==null)
			{
				String checkResult=ProofReader.checkProofString(console.getText());
				console.setText(console.getText()+"\n\n"+checkResult);
				lastResult=checkResult;
			}
			else
			{	
				String checkResult=ProofReader.checkProofString(console.getText().replace(lastResult, ""));
				if(console.getText().contains(lastResult))
				{
					console.setText(console.getText().replace(lastResult, checkResult));
				}
				else
				{
					console.setText(console.getText()+"\n\n"+checkResult);
				}
				lastResult=checkResult;
			}
		} catch (InvalidRuleName e) {
			// TODO Auto-generated catch block
			console.setText(console.getText()+"\n\n"+e.getMessage());
		}
    	
    }
    
    public void checkFromFile()
    {
    	try {
			FileChooser chooser=new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
			File f=chooser.showOpenDialog(null);
			if(f!=null)
			{
					String checkResult=ProofReader.checkProofFromFile(f.getAbsolutePath());
					console.setText(checkResult);
				
			}
    	}
    	catch (InvalidRuleName e) {
			// TODO Auto-generated catch block
			console.setText(e.getMessage());
		}
    }
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent deductionParent=FXMLLoader.load(getClass().getResource("NaturalDeductionPropLogicMenu.fxml"));
    	Scene deductionScene=new Scene(deductionParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(deductionScene);
    	window.show();
    }
    @FXML
    void initialize() {
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'CheckProofPropLogic.fxml'.";

    }
}
