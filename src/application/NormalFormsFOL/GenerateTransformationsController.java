package application.NormalFormsFOL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import NormalForms.PrenexNormalForm.PrenexTransformationProof;
import NormalForms.SkolemNormalForm.SkolemClausalTransformator;
import NormalForms.SkolemNormalForm.SkolemTransformator;
import Util.WriteFile;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class GenerateTransformationsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField initialFormula;

    @FXML
    private TextField transformedFormula;

    public void back(ActionEvent event) throws IOException
    {
    	Parent normalFormsParent=FXMLLoader.load(getClass().getResource("./NormalFormsFOLMenu.fxml"));
    	Scene normalFormsParentScene=new Scene(normalFormsParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(normalFormsParentScene);
    	window.show();
    }
    
    public void prenexTransformation()
    {
    	try {
			FOLFormula currentFormula=new FOLFormula(initialFormula.getText());
			PrenexTransformationProof.transform(currentFormula);
			this.transformedFormula.setText(PrenexTransformationProof.lastTransformation.toString());
		} catch (InvalidPropositionalLogicFormula | InvalidSubstitution e) {
			AlertBox.display(e.getMessage());
		}
    }
    
    public void skolemTransformation()
    {
    	try {
			FOLFormula currentFormula=new FOLFormula(initialFormula.getText());
			SkolemTransformator.transform(currentFormula);
			this.transformedFormula.setText(SkolemTransformator.lastTransformations.toString());
		} catch (InvalidPropositionalLogicFormula | InvalidSubstitution e) {
			AlertBox.display(e.getMessage());
		}
    }
    
    public void skolemClausalTransformation()
    {
    	try {
			FOLFormula currentFormula=new FOLFormula(initialFormula.getText());
			SkolemClausalTransformator.transform(currentFormula);
			this.transformedFormula.setText(SkolemClausalTransformator.lastTransformations.toString());
		} catch (InvalidPropositionalLogicFormula | InvalidSubstitution e) {
			AlertBox.display(e.getMessage());
		}
    }

    public void generatePrenexTransformation()
    {
		try {
			FOLFormula currentFormula=new FOLFormula(initialFormula.getText());
			FileChooser chooser=new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
			File f=chooser.showOpenDialog(null);
			if(f!=null)
			{
				try {
					List<String> transformation=PrenexTransformationProof.transform(currentFormula);
					WriteFile.writeLines(transformation, f.getAbsolutePath());
					this.transformedFormula.setText(PrenexTransformationProof.lastTransformation.toString());
					AlertBox.display("Succes!\nThe transformation can be found in\n"+f.getAbsolutePath());
				} catch (InvalidPropositionalLogicFormula | IOException | InvalidSubstitution e) {
					// TODO Auto-generated catch block
					AlertBox.display(e.getMessage());
				}
				
			}
		} catch (InvalidPropositionalLogicFormula e) {
			AlertBox.display(e.getMessage());
		}

    }
    
    public void generateSkolemTransformation()
    {
		try {
			FOLFormula currentFormula=new FOLFormula(initialFormula.getText());
			FileChooser chooser=new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
			File f=chooser.showOpenDialog(null);
			if(f!=null)
			{
				try {
					List<String> transformation=SkolemTransformator.transform(currentFormula);
					WriteFile.writeLines(transformation, f.getAbsolutePath());
					this.transformedFormula.setText(SkolemTransformator.lastTransformations.toString());
					AlertBox.display("Succes!\nThe transformation can be found in\n"+f.getAbsolutePath());
				} catch (InvalidPropositionalLogicFormula | IOException | InvalidSubstitution e) {
					AlertBox.display(e.getMessage());
				}
				
			}
		} catch (InvalidPropositionalLogicFormula e) {
			AlertBox.display(e.getMessage());
		}

    }
    
    public void generateSkolemClausalTransformation()
    {
    	try {
			FOLFormula currentFormula=new FOLFormula(initialFormula.getText());
			FileChooser chooser=new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
			File f=chooser.showOpenDialog(null);
			if(f!=null)
			{
				try {
					List<String> transformation=SkolemClausalTransformator.transform(currentFormula);
					WriteFile.writeLines(transformation, f.getAbsolutePath());
					this.transformedFormula.setText(SkolemClausalTransformator.lastTransformations.toString());
					AlertBox.display("Succes!\nThe transformation can be found in\n"+f.getAbsolutePath());
				} catch (InvalidPropositionalLogicFormula | IOException | InvalidSubstitution e) {
					AlertBox.display(e.getMessage());
				}
				
			}
		} catch (InvalidPropositionalLogicFormula e) {
			AlertBox.display(e.getMessage());
		}
    }

    @FXML
    void initialize() {
        assert initialFormula != null : "fx:id=\"initialFormula\" was not injected: check your FXML file 'GenerateTransformation.fxml'.";
        assert transformedFormula != null : "fx:id=\"transformedFormula\" was not injected: check your FXML file 'GenerateTransformation.fxml'.";

    }
}