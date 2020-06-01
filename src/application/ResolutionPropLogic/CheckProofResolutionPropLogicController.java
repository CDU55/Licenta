package application.ResolutionPropLogic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.InvalidProof;
import Exceptions.InvalidRuleName;
import NaturalDeduction.ProofReader;
import Resolution.ResolutionProofCheck;
import application.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class CheckProofResolutionPropLogicController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea console;
    

    public void check()
    {
    	try {
				
				String checkResult=ResolutionProofCheck.checkProof(console.getText().trim(),false);
				AlertBox.display(checkResult);
		} 
    	catch (IOException | InvalidProof e) {
			console.setText(console.getText()+"\n\n"+e.getMessage());
		}
    	
    }
    
    public void checkFromFile()
    {
    	FileChooser chooser=new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
		File f=chooser.showOpenDialog(null);
		if(f!=null)
		{
				String checkResult;
				try {
					checkResult = ResolutionProofCheck.checkProof(f.getAbsolutePath(),true);
					AlertBox.display(checkResult);
				} catch (IOException | InvalidProof e) {
					// TODO Auto-generated catch block
					console.setText(e.getMessage());
				}
			
		}
    }
    
    public void back(ActionEvent event) throws IOException {
		Parent resolutionParent = FXMLLoader.load(getClass().getResource("ResolutionMenu.fxml"));
		Scene resolutionParentScene = new Scene(resolutionParent);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(resolutionParentScene);
		window.show();
	}
    
    @FXML
    void initialize() {
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'CheckProofPropLogic.fxml'.";

    }
}
