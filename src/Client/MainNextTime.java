
package Client;

// Stampa
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
public class MainNextTime extends Application {
	FXMLLoader fxmlLoader;
	@Override
	public void start(Stage primaryStage)  {
		try {
			URL fxmlUrl = getClass().getResource("NextTime.fxml");
			this.fxmlLoader = new FXMLLoader(fxmlUrl);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void begin(Stage stage) {
		start(stage);
	}
}



