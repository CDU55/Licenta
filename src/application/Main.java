package application;
	
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	public static HostServices webService;
	@Override
	public void start(Stage primaryStage) {
		try {
		    Parent root= FXMLLoader.load(getClass().getResource("MainMenu/MainMenu.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Logic E-Learning Assistant");
			primaryStage.getIcons().add(new Image("./application/Resources/Logo-FII.png"));
			webService=this.getHostServices();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
