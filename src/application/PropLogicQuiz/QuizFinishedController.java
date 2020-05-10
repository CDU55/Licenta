package application.PropLogicQuiz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuizFinishedController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    
    public void back(ActionEvent event) throws IOException
    {
    	Parent quizParent=FXMLLoader.load(getClass().getResource("../PropLogicQuiz/PropLogicQuizMenu.fxml"));
    	Scene quizParentScene=new Scene(quizParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(quizParentScene);
    	window.show();
    }
}

