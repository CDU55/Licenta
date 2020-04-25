package application.NormalFormsPropLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import NormalForms.FNC;
import NormalForms.FNCTransformator;
import NormalForms.NormalForm;
import PropositionalLogicFormula.Formula;
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

public class FNCController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textField;

    @FXML
    private Label message;

    private FNCTransformator transformator;
    
    private Formula currentFormula;
    @FXML
    void initialize() {
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'FNC.fxml'.";
        assert message != null : "fx:id=\"message\" was not injected: check your FXML file 'FNC.fxml'.";
        transformator=new FNCTransformator();

    }
    
    public void setFormula()
    {
    	try {
			currentFormula=new Formula(textField.getText());
			this.message.setText(currentFormula.toString());
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
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
    
    public void checkFNC()
    {
    	if(currentFormula==null)
    	{
    		AlertBox.display("Please set a formula");
    	}
    	else
    	{
    		NormalForm fnc=new FNC();
    		if(fnc.checkFormula(currentFormula))
    		{
    			AlertBox.display("The selected formula is in FNC");
    		}
    		else
    		{
    			AlertBox.display("The selected formula is NOT in FNC");
    		}
    	}
    }
    public void back(ActionEvent event) throws IOException
    {
    	Parent normalFormsParent=FXMLLoader.load(getClass().getResource("../NormalFormsPropLogic/NormalFormsMenu.fxml"));
    	Scene normalFormsParentScene=new Scene(normalFormsParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(normalFormsParentScene);
    	window.show();
    }
    
    public void ruleInfo() throws IOException
    {
    	Stage infoStage=new Stage();
    	Parent infoParent=FXMLLoader.load(getClass().getResource("FNCRuleInformation.fxml"));
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
}

