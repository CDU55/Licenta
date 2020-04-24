package application.NaturalDeductionPropLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Exceptions.InvalidPropositionalLogicFormula;
import NaturalDeduction.Sequence;
import PropositionalLogicFormula.Formula;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WriteProofPropLogicArgumentsController {

	private List<Formula> hypothesis;
	private Sequence goal;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField argument;

    @FXML
    private TextArea console;

    @FXML
    void initialize() {
        assert argument != null : "fx:id=\"argument\" was not injected: check your FXML file 'WriteProofPropLogicArguments.fxml'.";
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'WriteProofPropLogicArguments.fxml'.";
        this.hypothesis=new ArrayList<Formula>();

    }
    
    public void addToHypothesis()
    {
    	try {
			Formula formula=new Formula(argument.getText());
			if(!this.hypothesis.contains(formula))
			{
				this.hypothesis.add(formula);
				writeConsole();
			}
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    	
    }
    
    public void setGoal()
    {
    	try {
			this.goal=new Sequence(argument.getText());
			writeConsole();
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			AlertBox.display(e.getMessage());
		}
    	
    }
    
    public void remove()
    {
    	if(this.hypothesis.size()!=0)
    	{
    		this.hypothesis.remove(this.hypothesis.size()-1);
    		writeConsole();
    	}
    }
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent deductionParent=FXMLLoader.load(getClass().getResource("../NaturalDeductionPropLogic/NaturalDeductionPropLogicMenu.fxml"));
    	Scene deductionScene=new Scene(deductionParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(deductionScene);
    	window.show();
    }
    private void writeConsole()
    {
    	String message=new String();
    	if(!this.hypothesis.isEmpty())
    	{
    		for(Formula formula:this.hypothesis)
    		{
    			message+=formula.toString()+"\n";
    		}
    	}
    	else
    	{
    		message+="No hypothesis \n";
    	}
    	if(this.goal!=null)
    	{
    		message+=this.goal.toString()+"\n";
    	}
    	else
    	{
    		message+="No goal\n";
    	}
    	this.console.setText(message);
    }
    
    public void next(ActionEvent event) throws IOException
    {
    	if(this.hypothesis.isEmpty())
    	{
    		AlertBox.display("Please add formulas to the hypothesis");
    	}
    	else if(this.goal==null)
    	{
    		AlertBox.display("Please select a goal sequence");
    	}
    	else
    	{
    	FXMLLoader loader=new FXMLLoader();
    	loader.setLocation(getClass().getResource("WriteProofPropLogic.fxml"));
    	Parent nextSceneParent=loader.load();
    	Scene nextScene=new Scene(nextSceneParent);
    	WriteProofPropLogicController controller=loader.getController();
    	controller.initiData(this.hypothesis, this.goal);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(nextScene);
    	window.show();
    	}
    }
    
}
