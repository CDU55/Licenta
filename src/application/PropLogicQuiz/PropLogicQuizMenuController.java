package application.PropLogicQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DbManagement.CurrentQuizLevel;
import DbManagement.NormalFormsChapter;
import DbManagement.ResolutionChapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PropLogicQuizMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    
    public void FNC(ActionEvent event) throws IOException
    {
    	try {
			int currentLevel=CurrentQuizLevel.getCurrentLevel("FNC", true);
			int maxLevel=NormalFormsChapter.currentMaxLevel(true, true);
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
			{
				nextParent=FXMLLoader.load(getClass().getResource("QuizFinished.fxml"));
		    	 nextScene=new Scene(nextParent);
			}
			else
			{
				FXMLLoader loader=new FXMLLoader();
		    	loader.setLocation(getClass().getResource("FNCQuiz.fxml"));
		    	nextParent=loader.load();
		    	FNCQuizController controller=loader.getController();
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
    
    public void FND(ActionEvent event) throws IOException
    {
    	try {
			int currentLevel=CurrentQuizLevel.getCurrentLevel("FND", true);
			int maxLevel=NormalFormsChapter.currentMaxLevel(false, true);
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
			{
				nextParent=FXMLLoader.load(getClass().getResource("QuizFinished.fxml"));
		    	 nextScene=new Scene(nextParent);
			}
			else
			{
				FXMLLoader loader=new FXMLLoader();
		    	loader.setLocation(getClass().getResource("FNDQuiz.fxml"));
		    	nextParent=loader.load();
		    	FNDQuizController controller=loader.getController();
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
			int currentLevel=CurrentQuizLevel.getCurrentLevel("Resolution", true);
			int maxLevel=ResolutionChapter.currentMaxLevel(true);
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
			{
				nextParent=FXMLLoader.load(getClass().getResource("QuizFinished.fxml"));
		    	 nextScene=new Scene(nextParent);
			}
			else
			{
				FXMLLoader loader=new FXMLLoader();
		    	loader.setLocation(getClass().getResource("ResolutionQuiz.fxml"));
		    	nextParent=loader.load();
		    	ResolutionQuizController controller=loader.getController();
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
    
    public void NaturalDeduciton(ActionEvent event) throws IOException
    {
    	try {
			int currentLevel=CurrentQuizLevel.getCurrentLevel("Natural Deduction", true);
			int maxLevel=ResolutionChapter.currentMaxLevel(true);
			
			Parent nextParent=null;
			Scene nextScene=null;
			if(currentLevel>maxLevel)
			{
				nextParent=FXMLLoader.load(getClass().getResource("QuizFinished.fxml"));
		    	 nextScene=new Scene(nextParent);
			}
			else
			{
				FXMLLoader loader=new FXMLLoader();
		    	loader.setLocation(getClass().getResource("NaturalDeductionQuiz.fxml"));
		    	nextParent=loader.load();
		    	NaturalDeductionQuizController controller=loader.getController();
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
    	Parent propLogicParent=FXMLLoader.load(getClass().getResource("../PropositionalLogicMainMenu/PropositionalLogicMainMenu.fxml"));
    	Scene propLogicScene=new Scene(propLogicParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(propLogicScene);
    	window.show();
    }
}
