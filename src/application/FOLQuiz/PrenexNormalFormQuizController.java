package application.FOLQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NormalFormsChapter;
import DbManagement.PrenexNormalFormChapter;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import NormalForms.PrenexNormalForm.PrenexNormalForm;
import NormalForms.PrenexNormalForm.PrenexTransformator;
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

public class PrenexNormalFormQuizController {
	 
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textField;

    @FXML
    private Label message;
	
	private PrenexTransformator transformator;
	    
	private FOLFormula currentFormula;
	    
	private int currentLevel;
	
	 @FXML
	    void initialize() {
	        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'FNC.fxml'.";
	        assert message != null : "fx:id=\"message\" was not injected: check your FXML file 'FNC.fxml'.";
	        transformator=new PrenexTransformator();

	    }  
	
	public void initializeData(int level)
	    {
	    		this.currentLevel=level;
	    		String formula;
				try {
					formula = PrenexNormalFormChapter.getEntry(level);
					this.currentFormula=new FOLFormula(formula);
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
					FOLFormula toReplace=new FOLFormula(textField.getText());
					if(!toReplace.isSubFormula(currentFormula))
					{
						AlertBox.display(toReplace.toString()+" is not a subformula of "+currentFormula.toString());
					}
					else
					{
						FOLFormula result=transformator.apply(ruleNumber, toReplace);
						currentFormula.replaceSubFormula(toReplace, result);
						this.message.setText(currentFormula.toString());
					}
				} catch (InvalidPropositionalLogicFormula e1) {
					AlertBox.display(e1.getMessage());
				} catch (InvalidRuleName e1) {
					AlertBox.display(e1.getMessage());
				} catch (InvalidSubstitution e1) {
					AlertBox.display(e1.getMessage());

				}
	    	}
	    }
	    
	    public void checkPrenex()
	    {
	    	if(currentFormula==null)
	    	{
	    		AlertBox.display("Please set a formula");
	    	}
	    	else
	    	{
	    		if(PrenexNormalForm.testPrenexNormalForm(currentFormula))
	    		{
	    			AlertBox.display("The selected formula is in Prenex Normal Form");
	    		}
	    		else
	    		{
	    			AlertBox.display("The selected formula is NOT in Prenex Normal Form");
	    		}
	    	}
	    }
	    public void back(ActionEvent event) throws IOException
	    {
	    	Parent quizParent=FXMLLoader.load(getClass().getResource("FOLQuizMenu.fxml"));
	    	Scene quizParentScene=new Scene(quizParent);
	    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(quizParentScene);
	    	window.show();
	    }
	    
	    public void ruleInfo() throws IOException
	    {
	    	Stage infoStage=new Stage();
	    	Parent infoParent=FXMLLoader.load(getClass().getResource("../NormalFormsFOL/PrenexTransformationRules.fxml"));
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
	    	if(!PrenexNormalForm.testPrenexNormalForm(currentFormula))
	    	{
	    		AlertBox.display("Please solve the current exercise before progressing to the next one");
	    	}
	    	else
	    	{
	    		try {
					int maxLevel=PrenexNormalFormChapter.currentMaxLevel();
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
				    	loader.setLocation(getClass().getResource("PrenexNormalFormQuiz.fxml"));
				    	nextParent=loader.load();
				    	PrenexNormalFormQuizController controller=loader.getController();
				    	controller.initializeData(this.currentLevel+1);
				    	nextScene=new Scene(nextParent);
					}
					CurrentQuizLevel.incrementLevel("Prenex", false);
			    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
			    	window.setScene(nextScene);
			    	window.show();

				} catch (ClassNotFoundException e) {
					AlertBox.display(e.getMessage());
				} catch (SQLException e) {
					AlertBox.display(e.getMessage());
				}
	    	}
	    }
}
