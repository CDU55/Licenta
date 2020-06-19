package application.FOLQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NaturalDeductionChapter;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Formulas.Formula;
import NaturalDeduction.NaturalDeductionFOL.DeductiveSystemFOL;
import NaturalDeduction.NaturalDeductionFOL.SequenceFOL;
import application.AlertBox;
import application.PropLogicQuiz.FNCQuizController;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class NaturalDeductionQuizFOLController {
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
	    private TextField formula2;

	    @FXML
	    private TextArea console;
	    
	    @FXML
	    private Label goal;
	    
	    private DeductiveSystemFOL deductiveSystem;
	    
	    private int currentLevel;

	    @FXML
	    void initialize() {
	        assert sequence1 != null : "fx:id=\"sequence1\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
	        assert sequence2 != null : "fx:id=\"sequence2\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
	        assert sequence3 != null : "fx:id=\"sequence3\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
	        assert formula1 != null : "fx:id=\"formula1\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
	        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";
	        assert goal != null : "fx:id=\"goal\" was not injected: check your FXML file 'WriteProofPropLogic.fxml'.";

	    }
	    public void initializeData(int currentLevel)
	    {
	    	try {
	    	this.currentLevel=currentLevel;
	    	List<String> hypothesis=NaturalDeductionChapter.getEntryHypothesis(false, currentLevel);
	    	String goalSequence=NaturalDeductionChapter.getEntryGoal(false, currentLevel);
	    	List<FOLFormula> formulas=new ArrayList<FOLFormula>();
	    	for(String hypo:hypothesis)
	    	{
	    		formulas.add(new FOLFormula(hypo));
	    	}
	    	SequenceFOL sequence=new SequenceFOL(goalSequence);
	    	this.deductiveSystem=new DeductiveSystemFOL(formulas,sequence);
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
	    	Parent quizParent=FXMLLoader.load(getClass().getResource("FOLQuizMenu.fxml"));
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			}
	    }
	    
	    public void hypothesis()
	    {
	    	try {
	        	SequenceFOL sequence=new SequenceFOL(formula1.getText());
				deductiveSystem.apply("IPOTEZA",sequence);
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
			} catch (InvalidSubstitution e) {
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
	    		} catch (InvalidSubstitution e) {
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
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			}
	    }
	    
	    public void createExistentialCuantifier()
	    {
	    	int arg1=(Integer)this.sequence1.getValue();
	    	String arg2=this.formula1.getText();
	    	String arg3=this.formula2.getText();
	    	try {
				deductiveSystem.apply("Ei",arg1,arg2,arg3);
				this.console.setText(deductiveSystem.toString());
				initializeSpinners();
			} catch (InvalidInferenceRuleApplication e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (GoalReached e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			}
	    }
	    
	    public void removeUniversalCuantifier()
	    {
	    	int arg1=(Integer)this.sequence1.getValue();
	    	String arg2=this.formula1.getText();
	    	try {
				deductiveSystem.apply("Ve",arg1,arg2);
				this.console.setText(deductiveSystem.toString());
				initializeSpinners();
			} catch (InvalidInferenceRuleApplication e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (GoalReached e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			}
	    }
	    
	    public void createUniversalCuantifier()
	    {
	    	int arg1=(Integer)this.sequence1.getValue();
	    	String arg2=this.formula1.getText();
	    	String arg3=this.formula2.getText();
	    	try {
				deductiveSystem.apply("Vi",arg1,arg2,arg3);
				this.console.setText(deductiveSystem.toString());
				initializeSpinners();
			} catch (InvalidInferenceRuleApplication e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (GoalReached e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			}
	    }
	    
	    public void removeExistentialCuantifier()
	    {
	    	int arg1=(Integer)this.sequence1.getValue();
	    	int arg2=(Integer)this.sequence2.getValue();
	    	try {
				deductiveSystem.apply("Ee",arg1,arg2);
				this.console.setText(deductiveSystem.toString());
				initializeSpinners();
			} catch (InvalidInferenceRuleApplication e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (GoalReached e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				AlertBox.display(e.getMessage());
			}
	    }
	    public void info() throws IOException
	    {
	    	Stage infoStage=new Stage();
	    	infoStage.getIcons().add(new Image("./application/Resources/Logo-FII.png"));
	    	infoStage.setTitle("Rule info");
	    	Parent infoParent=FXMLLoader.load(getClass().getResource("../NaturalDeductionFOL/InferenceRulesInfoFOL.fxml"));
	    	Scene infoScene=new Scene(infoParent);
	    	infoStage.setScene(infoScene);
	    	infoStage.show();
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
					int maxLevel=NaturalDeductionChapter.currentMaxLevel( false);
					Parent nextParent=null;
					Scene nextScene=null;
					if(currentLevel>=maxLevel)
					{
						nextParent=FXMLLoader.load(getClass().getResource("QuizFinishedFOL.fxml"));
				    	nextScene=new Scene(nextParent);
					}
					else
					{
						FXMLLoader loader=new FXMLLoader();
				    	loader.setLocation(getClass().getResource("NaturalDeductionQuizFOL.fxml"));
				    	nextParent=loader.load();
				    	FNCQuizController controller=loader.getController();
				    	controller.initializeData(this.currentLevel+1);
				    	nextScene=new Scene(nextParent);
					}
					CurrentQuizLevel.incrementLevel("Natural Deduction", false);
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