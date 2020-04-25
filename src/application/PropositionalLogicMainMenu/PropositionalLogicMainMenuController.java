package application.PropositionalLogicMainMenu;
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

public class PropositionalLogicMainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public void backBtnPushed(ActionEvent event) throws IOException
    {
    	Parent mainMenuParent=FXMLLoader.load(getClass().getResource("../MainMenu/MainMenu.fxml"));
    	Scene mainMenuScene=new Scene(mainMenuParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(mainMenuScene);
    	window.show();
    }
    public void analysisBtnPushed(ActionEvent event) throws IOException
    {
    	Parent analysisParent=FXMLLoader.load(getClass().getResource("../FormulaAnalysis/FormulaAnalysis.fxml"));
    	Scene analysisScene=new Scene(analysisParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(analysisScene);
    	window.show();
    }
    public void deductionBtnPushed(ActionEvent event) throws IOException
    {
    	Parent deductionParent=FXMLLoader.load(getClass().getResource("../NaturalDeductionPropLogic/NaturalDeductionPropLogicMenu.fxml"));
    	Scene deductionScene=new Scene(deductionParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(deductionScene);
    	window.show();
    }
    
    public void normalFormsBtnPushed(ActionEvent event) throws IOException
    {
    	Parent normalFormsParent=FXMLLoader.load(getClass().getResource("../NormalFormsPropLogic/NormalFormsMenu.fxml"));
    	Scene normalFormsParentScene=new Scene(normalFormsParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(normalFormsParentScene);
    	window.show();
    }
    
    public void resolutionBtnPushed(ActionEvent event) throws IOException
    {
    	Parent resolutionParent=FXMLLoader.load(getClass().getResource("../ResolutionPropLogic/ResolutionMenu.fxml"));
    	Scene resolutionParentScene=new Scene(resolutionParent);
    	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(resolutionParentScene);
    	window.show();
    }
    @FXML
    void initialize() {

    }
}
