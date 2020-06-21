package application.ResolutionFOL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Resolution.ResolutionFirstOrderLogic.ResolutionFOL;
import Resolution.ResolutionFirstOrderLogic.ResolutionProofFOL;
import Util.WriteFile;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ResolutionWriteProofFOLController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField formulaField;

	@FXML
	private Spinner<Integer> clause1;

	@FXML
	private Spinner<Integer> clause2;

	@FXML
	private TextField literal;

	@FXML
	private TextArea console;

	private FOLFormula currentFormula;

	private ResolutionFOL resolution;

	@FXML
	void initialize() {
		assert formulaField != null : "fx:id=\"formulaField\" was not injected: check your FXML file 'ResolutionWriteProof.fxml'.";
		assert clause1 != null : "fx:id=\"clause1\" was not injected: check your FXML file 'ResolutionWriteProof.fxml'.";
		assert clause2 != null : "fx:id=\"clause2\" was not injected: check your FXML file 'ResolutionWriteProof.fxml'.";
		assert literal != null : "fx:id=\"literal\" was not injected: check your FXML file 'ResolutionWriteProof.fxml'.";
		assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'ResolutionWriteProof.fxml'.";

	}

	public void initializeSpinners() {
		if (resolution != null) {
			SpinnerValueFactory<Integer> factory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
					this.resolution.getClausesNumber());
			SpinnerValueFactory<Integer> factory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
					this.resolution.getClausesNumber());
			this.clause1.setValueFactory(factory1);
			this.clause2.setValueFactory(factory2);
		}
	}

	public void setFormula() {
		try {
			this.currentFormula = new FOLFormula(formulaField.getText());
			resolution = new ResolutionFOL(currentFormula);
			this.console.setText(resolution.toString());
			initializeSpinners();
		} catch (InvalidPropositionalLogicFormula | InvalidLiteral | InvalidSubstitution e) {
			AlertBox.display(e.getMessage());
		}
	}

	public void generateProof() {
		try {
			if (this.currentFormula == null) {
				AlertBox.display("Please set a formula");
			} else {
				ResolutionFOL proof = ResolutionProofFOL.findProof(currentFormula);
				if (proof == null) {
					AlertBox.display("Couldn't find a proof for the given formula");
				} else {
					resolution = proof;
					this.console.setText(resolution.toString());
					initializeSpinners();
				}

			}
		} catch (GoalReached | InvalidSubstitution e) {
			AlertBox.display(e.getMessage());
		}
	}

	public void back(ActionEvent event) throws IOException {
		Parent resolutionParent = FXMLLoader.load(getClass().getResource("ResolutionFOLMenu.fxml"));
		Scene resolutionParentScene = new Scene(resolutionParent);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(resolutionParentScene);
		window.show();
	}

	public void apply() {
		if (this.currentFormula == null) 
		{
			AlertBox.display("Please set a formula");
		}
		else
		{
		int index1 = clause1.getValue();
		int index2 = clause2.getValue();
		if(index1==index2)
		{
			AlertBox.display("Please select two different clauses");
		}
		else
		{
		try {
			String l = literal.getText();
			if(l.trim().charAt(0)=='!')
			{
				AlertBox.display("The literal must not be negated");
			}
			else if(!l.trim().matches("[A-DF-UW-Z][a-zA-DF-UW-Z]*"))
			{
				AlertBox.display("Please provide a valid predicate symbol");
			}
			else
			{
				resolution.applyResolution(index1, index2, literal.getText());
				this.console.setText(resolution.toString());
				initializeSpinners();
			}
		} catch (InvalidInferenceRuleApplication | GoalReached | InvalidSubstitution e) {
			AlertBox.display(e.getMessage());
		}
		}
		}
	}
	
	public void positiveFactorization()
	{
		if (this.currentFormula == null) 
		{
			AlertBox.display("Please set a formula");
		}
		else
		{
		int index1 = clause1.getValue();
		try {
			String l = literal.getText();
			 if(!l.trim().matches("[A-DF-UW-Z][a-zA-DF-UW-Z]*"))
			{
				AlertBox.display("Please provide a valid predicate symbol");
			}
			else
			{
				resolution.positiveFactorization(index1, l);
				this.console.setText(resolution.toString());
				initializeSpinners();
			}
		} catch (InvalidSubstitution | InvalidPropositionalLogicFormula e) {
			AlertBox.display(e.getMessage());
		}
		}
	}
	
	public void undo()
	{
		if(this.resolution==null)
		{
			AlertBox.display("Please select a formula");
		}
		else
		{
			this.resolution.remove();
			this.console.setText(resolution.toString());
		}
	}
	  public void export()
	    {
	    	FileChooser chooser=new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
			File f=chooser.showOpenDialog(null);
			if(f!=null)
			{
					try {
						WriteFile.writeResolutionProofFOL(this.resolution, f.getAbsolutePath());
						AlertBox.display("Succes!\nThe proof can be found in\n"+f.getAbsolutePath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						AlertBox.display("An error occured");
					}
				
			}
	    }
}
