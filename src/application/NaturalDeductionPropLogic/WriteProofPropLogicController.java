package application.NaturalDeductionPropLogic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidPropositionalLogicFormula;
import NaturalDeduction.DeductiveSystem;
import NaturalDeduction.Sequence;
import PropositionalLogicFormula.Formula;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WriteProofPropLogicController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Integer> sequence1;

    @FXML
    private Spinner<Integer> sequence2;

    @FXML
    private Spinner<Integer> sequence3;

    @FXML
    private TextField formula1;

    @FXML
    private TextArea console;
    
    @FXML
    private Label goal;
    
    private DeductiveSystem deductiveSystem;

    @FXML
    void initialize() {
        assert sequence1 != null : "fx:id=\"sequence1\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
        assert sequence2 != null : "fx:id=\"sequence2\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
        assert sequence3 != null : "fx:id=\"sequence3\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
        assert formula1 != null : "fx:id=\"formula1\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
        assert goal != null : "fx:id=\"goal\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";

    }
    
    public void initiData(List<Formula> hypothesis,Sequence goalSequence)
    {
    	this.deductiveSystem=new DeductiveSystem(hypothesis,goalSequence);
    	this.console.setText(deductiveSystem.toString());
    	this.goal.setText("Goal : "+goalSequence.toString());
    	initializeSpinners();
    }
    private void initializeSpinners()
    {
    	SpinnerValueFactory<Integer> factory1=new SpinnerValueFactory.IntegerSpinnerValueFactory(1, this.deductiveSystem.sequences.size());
    	SpinnerValueFactory<Integer> factory2=new SpinnerValueFactory.IntegerSpinnerValueFactory(1, this.deductiveSystem.sequences.size());
    	SpinnerValueFactory<Integer> factory3=new SpinnerValueFactory.IntegerSpinnerValueFactory(1, this.deductiveSystem.sequences.size());
    	this.sequence1.setValueFactory(factory1);
    	this.sequence2.setValueFactory(factory2);
    	this.sequence3.setValueFactory(factory3);
    }
    public void back(ActionEvent event) throws IOException
    {
    	Parent deductionParent=FXMLLoader.load(getClass().getResource("WriteProofPropLogicArguments.fxml"));
    	Scene deductionScene=new Scene(deductionParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(deductionScene);
    	window.show();
    }
    public void createConjunction()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	int arg2=(Integer)this.sequence2.getValue();
    	try {
			deductiveSystem.apply("/\\i",arg1,arg2);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void removeFromConjunction1()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
			deductiveSystem.apply("/\\e1",arg1);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void removeFromConjunction2()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
			deductiveSystem.apply("/\\e2",arg1);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void extractFromImplication()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	int arg2=(Integer)this.sequence2.getValue();
    	try {
			deductiveSystem.apply("->e",arg1,arg2);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void createImplication()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
        	Formula formula=new Formula(this.formula1.getText());
			deductiveSystem.apply("->i",arg1,formula);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void createDisjunction1()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
        	Formula formula=new Formula(this.formula1.getText());
			deductiveSystem.apply("\\/i1",arg1,formula);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    public void createDisjunction2()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
        	Formula formula=new Formula(this.formula1.getText());
			deductiveSystem.apply("\\/i2",arg1,formula);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void removeDisjunction()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	int arg2=(Integer)this.sequence2.getValue();
    	int arg3=(Integer)this.sequence3.getValue();
    	try {
			deductiveSystem.apply("\\/e",arg1,arg2,arg3);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void createBottom()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	int arg2=(Integer)this.sequence2.getValue();
    	try {
			deductiveSystem.apply("!e",arg1,arg2);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    public void createNegationFromBottom()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
        	Formula formula=new Formula(this.formula1.getText());
			deductiveSystem.apply("!i",arg1,formula);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void createProvenFromBottom()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
        	Formula formula=new Formula(this.formula1.getText());
			deductiveSystem.apply("|e",arg1,formula);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void hypothesis()
    {
    	try {
        	Sequence sequence=new Sequence(formula1.getText());
			deductiveSystem.apply("IPOTEZA",sequence,sequence.proven);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void extension()
    {
        	int arg1=(Integer)this.sequence1.getValue();
        	try {
            	Formula formula=new Formula(this.formula1.getText());
    			deductiveSystem.apply("EXTINDERE",arg1,formula);
    			this.console.setText(deductiveSystem.toString());
    			initializeSpinners();
    		} catch (InvalidInferenceRuleApplication e) {
    			// TODO Auto-generated catch block
    			AlertBox.display(e.getMessage());
    		} catch (GoalReached e) {
    			// TODO Auto-generated catch block
    			AlertBox.display(e.getMessage());
    		} catch (InvalidPropositionalLogicFormula e) {
    			// TODO Auto-generated catch block
    			AlertBox.display(e.getMessage());
    		}
    }
    
    public void removeDoubleNegation()
    {
    	int arg1=(Integer)this.sequence1.getValue();
    	try {
			deductiveSystem.apply("!!e",arg1);
			this.console.setText(deductiveSystem.toString());
			initializeSpinners();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    }
    
    public void info() throws IOException
    {
    	Stage infoStage=new Stage();
    	Parent infoParent=FXMLLoader.load(getClass().getResource("InferenceRulesInfo.fxml"));
    	Scene infoScene=new Scene(infoParent);
    	infoStage.setScene(infoScene);
    	infoStage.show();
    }
    public void remove()
    {
    	this.deductiveSystem.remove();
    	this.console.setText(deductiveSystem.toString());
    }
 
}

