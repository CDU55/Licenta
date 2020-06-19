package application.NormalFormsFOL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WritePrenexTransformationController {
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
    @FXML
    void initialize() {
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'FNC.fxml'.";
        assert message != null : "fx:id=\"message\" was not injected: check your FXML file 'FNC.fxml'.";
        transformator=new PrenexTransformator();

    }
    
    public void setFormula()
    {
    	try {
			currentFormula=new FOLFormula(textField.getText());
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
    
    public void checkFNC()
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
    	Parent normalFormsParent=FXMLLoader.load(getClass().getResource("./NormalFormsFOLMenu.fxml"));
    	Scene normalFormsParentScene=new Scene(normalFormsParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(normalFormsParentScene);
    	window.show();
    }
    
    public void ruleInfo() throws IOException
    {
    	Stage infoStage=new Stage();
    	infoStage.getIcons().add(new Image("./application/Resources/Logo-FII.png"));
    	Parent infoParent=FXMLLoader.load(getClass().getResource("PrenexTransformationRules.fxml"));
    	infoStage.setTitle("Rule info");
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
