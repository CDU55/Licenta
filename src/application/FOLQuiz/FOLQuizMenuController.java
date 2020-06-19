package application.FOLQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NaturalDeductionChapter;
import DbManagement.NormalFormsChapter;
import DbManagement.PrenexNormalFormChapter;
import DbManagement.ResolutionChapter;
import application.PropLogicQuiz.FNCQuizController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FOLQuizMenuController {
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    
    public void Prenex(ActionEvent event) throws IOException
    {
    	try {
			int currentLevel=CurrentQuizLevel.getCurrentLevel("Prenex",false);
			int maxLevel=PrenexNormalFormChapter.currentMaxLevel();
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
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
		    	controller.initializeData(currentLevel);
		    	nextScene=new Scene(nextParent);
			}
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
    
    
    public void Resolution(ActionEvent event) throws IOException
    {
    	try {
			int currentLevel=CurrentQuizLevel.getCurrentLevel("Resolution", false);
			int maxLevel=ResolutionChapter.currentMaxLevel(false);
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
			{
				nextParent=FXMLLoader.load(getClass().getResource("QuizFinishedFOL.fxml"));
		    	 nextScene=new Scene(nextParent);
			}
			else
			{
				FXMLLoader loader=new FXMLLoader();
		    	loader.setLocation(getClass().getResource("ResolutionQuizFOL.fxml"));
		    	nextParent=loader.load();
		    	ResolutionQuizFOLController controller=loader.getController();
		    	controller.initializeData(currentLevel);
		    	nextScene=new Scene(nextParent);
			}
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
    
    public void NaturalDeduction(ActionEvent event) throws IOException
    {
    	try {
			int currentLevel=CurrentQuizLevel.getCurrentLevel("Natural Deduction", false);
			int maxLevel=NaturalDeductionChapter.currentMaxLevel(false);
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
			{
				nextParent=FXMLLoader.load(getClass().getResource("QuizFinishedFOL.fxml"));
		    	 nextScene=new Scene(nextParent);
			}
			else
			{
				FXMLLoader loader=new FXMLLoader();
		    	loader.setLocation(getClass().getResource("NaturalDeductionQuizFOL.fxml"));
		    	nextParent=loader.load();
		    	NaturalDeductionQuizFOLController controller=loader.getController();
		    	controller.initializeData(currentLevel);
		    	nextScene=new Scene(nextParent);
			}
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
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../FOLMainMenu/FOLMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
}
