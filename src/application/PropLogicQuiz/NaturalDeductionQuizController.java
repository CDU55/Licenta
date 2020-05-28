package application.PropLogicQuiz;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NaturalDeductionChapter;
import DbManagement.ResolutionChapter;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidPropositionalLogicFormula;
import NaturalDeduction.DeductiveSystem;
import NaturalDeduction.Sequence;
import PropositionalLogicFormula.Formula;
import Util.WriteFile;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class NaturalDeductionQuizController {

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
    
    private int currentLevel;

    @FXML
    void initialize() {
        assert sequence1 != null : "fx:id=\"sequence1\" was not injected: check your FXML file 'NaturalDeductionQuiz.fxml'.";
        assert sequence2 != null : "fx:id=\"sequence2\" was not injected: check your FXML file 'NaturalDeductionQuiz.fxml'.";
        assert sequence3 != null : "fx:id=\"sequence3\" was not injected: check your FXML file 'NaturalDeductionQuiz.fxml'.";
        assert formula1 != null : "fx:id=\"formula1\" was not injected: check your FXML file 'NaturalDeductionQuiz.fxml'.";
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'NaturalDeductionQuiz.fxml'.";
        assert goal != null : "fx:id=\"goal\" was not injected: check your FXML file 'NaturalDeductionQuiz.fxml'.";

    }
    public void initializeData(int currentLevel)
    {
    	try {
    	this.currentLevel=currentLevel;
    	List<String> hypothesis=NaturalDeductionChapter.getEntryHypothesis(true, currentLevel);
    	String goalSequence=NaturalDeductionChapter.getEntryGoal(true, currentLevel);
    	List<Formula> formulas=new ArrayList<Formula>();
    	for(String hypo:hypothesis)
    	{
    		formulas.add(new Formula(hypo));
    	}
    	Sequence sequence=new Sequence(goalSequence);
    	this.deductiveSystem=new DeductiveSystem(formulas,sequence);
    	this.console.setText(deductiveSystem.toString());
    	this.goal.setText("Goal : "+goalSequence.toString());
    	initializeSpinners();
    	}
    	catch(Exception e)
    	{
			AlertBox.display("An error occured,please go back to the quiz menu");

    	}
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
    	Parent quizParent=FXMLLoader.load(getClass().getResource("../PropLogicQuiz/PropLogicQuizMenu.fxml"));
    	Scene quizParentScene=new Scene(quizParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(quizParentScene);
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
    	Parent infoParent=FXMLLoader.load(getClass().getResource("../NaturalDeductionPropLogic/InferenceRulesInfo.fxml"));
    	Scene infoScene=new Scene(infoParent);
    	infoStage.setScene(infoScene);
    	infoStage.show();
    }
    
    public void export()
    {
    	FileChooser chooser=new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
		File f=chooser.showOpenDialog(null);
		if(f!=null)
		{
				try {
					WriteFile.writeNaturalDeductionProof(this.deductiveSystem, f.getAbsolutePath());
					AlertBox.display("Succes!\nThe proof can be found in\n"+f.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					AlertBox.display("An error occured");
				}
			
		}
    }
    public void remove()
    {
    	this.deductiveSystem.remove();
    	this.console.setText(deductiveSystem.toString());
    }
    
    public void next(ActionEvent event) throws IOException
    {
    	if(!this.deductiveSystem.getGoalReached())
    	{
    		AlertBox.display("Please solve the current exercise before progressing to the next one");
    	}
    	else
    	{
    		try {
				int maxLevel=NaturalDeductionChapter.currentMaxLevel( true);
				Parent nextParent=null;
				Scene nextScene=null;
				if(currentLevel>=maxLevel)
				{
					nextParent=FXMLLoader.load(getClass().getResource("../PropLogicQuiz/QuizFinished.fxml"));
			    	nextScene=new Scene(nextParent);
				}
				else
				{
					FXMLLoader loader=new FXMLLoader();
			    	loader.setLocation(getClass().getResource("NaturalDeductionQuiz.fxml"));
			    	nextParent=loader.load();
			    	FNCQuizController controller=loader.getController();
			    	controller.initializeData(this.currentLevel+1);
			    	nextScene=new Scene(nextParent);
				}
				CurrentQuizLevel.incrementLevel("Natural Deduction", true);
		    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
		    	window.setScene(nextScene);
		    	window.show();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
