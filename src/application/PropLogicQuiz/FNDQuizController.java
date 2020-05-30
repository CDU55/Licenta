package application.PropLogicQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NormalFormsChapter;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Formulas.Formula;
import NormalForms.FNC;
import NormalForms.FNCTransformator;
import NormalForms.FND;
import NormalForms.FNDTransformator;
import NormalForms.NormalForm;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FNDQuizController {
	  @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField textField;

	    @FXML
	    private Label message;

	    private FNDTransformator transformator;
	    
	    private Formula currentFormula;
	    private NormalForm fnd;
	    private int currentLevel;
	    @FXML
	    void initialize() {
	        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'FNC.fxml'.";
	        assert message != null : "fx:id=\"message\" was not injected: check your FXML file 'FNC.fxml'.";
	        transformator=new FNDTransformator();
	        fnd=new FND();

	    }
	    
	    public void initializeData(int level)
	    {
	    		this.currentLevel=level;
	    		String formula;
				try {
					formula = NormalFormsChapter.getEntry(false, true, level);
					this.currentFormula=new Formula(formula);
					this.message.setText(this.currentFormula.toString());
				} catch (ClassNotFoundException | SQLException | InvalidPropositionalLogicFormula e) {
					// TODO Auto-generated catch block
					AlertBox.display("An error occured,please go back to the quiz menu");
				}
	    }
	    
	    public void applyRule(ActionEvent e)
	    {
	    	if(currentFormula==null)
	    	{
	    		AlertBox.display("Please set a formula");
	    	}
	    	else
	    	{
	    		Button btn=(Button)e.getSource();
	    		String rule=btn.getText();
	    		String ruleNumber=Character.toString(rule.charAt(rule.length()-1));
	    		try {
					Formula toReplace=new Formula(textField.getText());
					if(!toReplace.isSubFormula(currentFormula))
					{
						AlertBox.display(toReplace.toString()+" is not a subformula of "+currentFormula.toString());
					}
					else
					{
						Formula result=transformator.apply(ruleNumber, toReplace);
						currentFormula.replaceSubformula(toReplace, result);
						this.message.setText(currentFormula.toString());
					}
				} catch (InvalidPropositionalLogicFormula e1) {
					AlertBox.display(e1.getMessage());
				} catch (InvalidRuleName e1) {
					AlertBox.display(e1.getMessage());
				}
	    	}
	    }
	    
	    public void checkFND()
	    {
	    	if(currentFormula==null)
	    	{
	    		AlertBox.display("Please set a formula");
	    	}
	    	else
	    	{
	    		if(fnd.checkFormula(currentFormula))
	    		{
	    			AlertBox.display("The selected formula is in FND");
	    		}
	    		else
	    		{
	    			AlertBox.display("The selected formula is NOT in FND");
	    		}
	    	}
	    }
	    public void back(ActionEvent event) throws IOException
	    {
	    	Parent quizParent=FXMLLoader.load(getClass().getResource("../PropLogicQuiz/PropLogicQuizMenu.fxml"));
	    	Scene quizParentScene=new Scene(quizParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(quizParentScene);
	    	window.show();
	    }
	       
	    public void ruleInfo() throws IOException
	    {
	    	Stage infoStage=new Stage();
	    	Parent infoParent=FXMLLoader.load(getClass().getResource("../NormalFormsPropLogic/FNDRuleInformation.fxml"));
	    	Scene infoScene=new Scene(infoParent);
	    	infoStage.setScene(infoScene);
	    	infoStage.show();
	    }
	    public void removeParathesis()
	    {
	    	if(currentFormula==null)
	    	{
	    		AlertBox.display("Please set a formula");
	    	}
	    	else
	    	{
	    		this.currentFormula.removeRedundantParathesis();
	    		this.message.setText(currentFormula.toString());
	    	}
	    }
	    
	    public void next(ActionEvent event) throws IOException
	    {
	    	if(!fnd.checkFormula(currentFormula))
	    	{
	    		AlertBox.display("Please solve the current exercise before progressing to the next one");
	    	}
	    	else
	    	{
	    		try {
					int maxLevel=NormalFormsChapter.currentMaxLevel(false, true);
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
				    	loader.setLocation(getClass().getResource("FNDQuiz.fxml"));
				    	nextParent=loader.load();
				    	FNCQuizController controller=loader.getController();
				    	controller.initializeData(this.currentLevel+1);
				    	nextScene=new Scene(nextParent);
					}
					CurrentQuizLevel.incrementLevel("FND", true);
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
