package Server;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// Stampa / Schibli
public class ServerView {
	protected Stage stage;
	private ServerModel model;
	Region topSpacer = new Region();
	Button serverStarten = new Button(" Server starten! ");

	public ServerView(Stage stage, ServerModel model) {
		this.stage = stage;
		this.model = model;
		BorderPane pane= new BorderPane();
		pane.setMinHeight(200);
		serverStarten.setTextAlignment(TextAlignment.CENTER);
		serverStarten.setPrefHeight(40);
		pane.setCenter(serverStarten);
		serverStarten.setMinSize(Button.USE_COMPUTED_SIZE, Button.USE_COMPUTED_SIZE);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Server");
	}
	protected void start() {
		stage.show();
		stage.setMaxHeight(200);
		stage.setWidth(300);
	}
	public Stage getStage() {
		return this.stage;
	}
	public void stop() {
		stage.hide();
	}

}