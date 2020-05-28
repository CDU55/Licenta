package application.PropLogicQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NormalFormsChapter;
import DbManagement.ResolutionChapter;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import PropositionalLogicFormula.Formula;
import Resolution.Literal;
import Resolution.Resolution;
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
import javafx.stage.Stage;

public class ResolutionQuizController {

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
	private Resolution resolution;
	
    private int currentLevel;



    @FXML
    public void back(ActionEvent event) throws IOException
    {
    	Parent quizParent=FXMLLoader.load(getClass().getResource("../PropLogicQuiz/PropLogicQuizMenu.fxml"));
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
				formula = ResolutionChapter.getEntry(true, level);
				this.resolution=new Resolution(new Formula(formula));
				 initializeSpinners();
				 this.console.setText(resolution.toString());
			} catch (ClassNotFoundException | SQLException | InvalidPropositionalLogicFormula | InvalidLiteral e) {
				// TODO Auto-generated catch block
				AlertBox.display("An error occured,please go back to the quiz menu");
			}
    }
    public void apply() {
		
		int index1 = clause1.getValue();
		int index2 = clause2.getValue();
		try {
			Literal l = new Literal(literal.getText());
			if(l.negated)
			{
				AlertBox.display("The literal must not be negated");
			}
			else
			{
				resolution.applyResolution(index1, index2, l.variable);
				this.console.setText(resolution.toString());
				initializeSpinners();
			}
		} catch (InvalidLiteral | InvalidInferenceRuleApplication | GoalReached e) {
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
				int maxLevel=ResolutionChapter.currentMaxLevel( true);
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
			    	loader.setLocation(getClass().getResource("ResolutionQuiz.fxml"));
			    	nextParent=loader.load();
			    	FNCQuizController controller=loader.getController();
			    	controller.initializeData(this.currentLevel+1);
			    	nextScene=new Scene(nextParent);
				}
				CurrentQuizLevel.incrementLevel("Resolution", true);
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
