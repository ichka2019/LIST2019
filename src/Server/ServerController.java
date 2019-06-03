package Server;

// Stampa
public class ServerController {

	public ServerController(ServerModel model, ServerView view) {
		view.serverStarten.setOnAction(event -> {
			view.serverStarten.setDisable(true);
			model.connect();
			System.out.println("started");

		});
		view.getStage().setOnCloseRequest(event->model.stopServer());
	}	
}


