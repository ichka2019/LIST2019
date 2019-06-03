package Client;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

//Stampa
public class ClientMain extends Application {
	FXMLLoader fxmlLoader;

	@Override
	public void start(Stage primaryStage)  {
		try {
			URL fxmlUrl = getClass().getResource("Front.fxml");
			this.fxmlLoader = new FXMLLoader(fxmlUrl);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}

