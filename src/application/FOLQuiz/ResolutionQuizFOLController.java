package application.FOLQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.ResolutionChapter;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Resolution.ResolutionFirstOrderLogic.ResolutionFOL;
import application.AlertBox;
import application.PropLogicQuiz.FNCQuizController;
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
import javafx.stage.Stage;

public class ResolutionQuizFOLController {
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private Spinner<Integer> clause1;

	    @FXML
	    private Spinner<Integer> clause2;

	    @FXML
	    private TextField literal;

	    @FXML
	    private TextArea console;
		private ResolutionFOL resolution;
		
	    private int currentLevel;



	    @FXML
	    public void back(ActionEvent event) throws IOException
	    {
	    	Parent quizParent=FXMLLoader.load(getClass().getResource("FOLQuizMenu.fxml"));
	    	Scene quizParentScene=new Scene(quizParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(quizParentScene);
	    	window.show();
	    }

	    @FXML
	    void initialize() {
	        assert clause1 != null : "fx:id=\"clause1\" was not injected: check your FXML file 'ResolutionQuiz.fxml'.";
	        assert clause2 != null : "fx:id=\"clause2\" was not injected: check your FXML file 'ResolutionQuiz.fxml'.";
	        assert literal != null : "fx:id=\"literal\" was not injected: check your FXML file 'ResolutionQuiz.fxml'.";
	        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'ResolutionQuiz.fxml'.";

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
	    
	    public void initializeData(int level)
	    {
	    		this.currentLevel=level;
	    		String formula;
				try {
					formula = ResolutionChapter.getEntry(false, level);
					this.resolution=new ResolutionFOL(new FOLFormula(formula));
					 initializeSpinners();
					 this.console.setText(resolution.toString());
				} catch (ClassNotFoundException | SQLException | InvalidPropositionalLogicFormula | InvalidLiteral | InvalidSubstitution e) {
					// TODO Auto-generated catch block
					AlertBox.display("An error occured,please go back to the quiz menu");
				}
	    }
	    public void apply() {
			int index1 = clause1.getValue();
			int index2 = clause2.getValue();
			if(index1!=index2)
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
		
		public void positiveFactorization()
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
	    public void next(ActionEvent event) throws IOException
	    {
	    	if(!this.resolution.getNullClauseAchieved())
	    	{
	    		AlertBox.display("Please solve the current exercise before progressing to the next one");
	    	}
	    	else
	    	{
	    		try {
					int maxLevel=ResolutionChapter.currentMaxLevel( false);
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
				    	loader.setLocation(getClass().getResource("ResolutionQuizFOL.fxml"));
				    	nextParent=loader.load();
				    	FNCQuizController controller=loader.getController();
				    	controller.initializeData(this.currentLevel+1);
				    	nextScene=new Scene(nextParent);
					}
					CurrentQuizLevel.incrementLevel("Resolution", false);
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
