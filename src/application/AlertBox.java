package application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {
	
	public static void display(String message)
	{
		Stage window=new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("Notification");
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(400);
		window.setHeight(600);
		
		Label label=new Label(message);
		VBox layout=new VBox(20);
		layout.setAlignment(Pos.CENTER);
		Button closeBtn=new Button("Close");
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				window.close();
			}
			}
			);
		layout.getChildren().addAll(label,closeBtn);
		Scene scene=new Scene(layout,800,400);
		scene.getStylesheets().add(AlertBox.class.getResource("Resources/theme.css").toExternalForm());
		window.setScene(scene);
		window.show();
		
	}

}