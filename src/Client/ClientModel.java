package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import CommonClass.Message;
import javafx.stage.Stage;

//Stampa
public class ClientModel {
	private MainNextTime fail=new MainNextTime();
	private GameStart gameStart=new GameStart();
	private Socket socket;
	private List<String> players= new ArrayList<String>();
	private String name;
	private Stage stage=new Stage();
	List<Integer> resourcen=new ArrayList<>();
	private List<Message> messages=new ArrayList<>();
	private List<String> zugKarten=new ArrayList<>();
	private String sieger;
	private Message msg;

	//Stampa
	public void connect(String ip, Integer port, String n) {
		try {
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.name=n;
		Message msgOut=new Message();
		msgOut.setMessage("anmelden");
		msgOut.setName(name);
		msgOut.send(socket);
		getMessage();
	}

	//Stampa
	public void getMessage() {
		Message msgIn= (Message) Message.receive(socket);
		this.msg=msgIn;

		String message= msgIn.getMessage();
		switch(message) {
		case "StartSpiel":
			players=msgIn.getPlayers();
			spielerKarten(msgIn);
			gameStart.start(this.stage);
			GameController.setModel(this);
			break;
		case "Sorry":
			fail.begin(this.stage);
			break;
		case "NextZug":
			this.messages=msgIn.getMessages();
			break;
		case "Zeitalter2":
			spielerKarten(this.msg);
			this.messages=this.msg.getMessages();
			break;
		case "SpielEnde":
			this.messages=msgIn.getMessages();
			this.sieger=this.msg.getSieger();
			System.out.println(msg.getSieger()+"CM");
			break;
		}
	}

	public List<String> getPlayers(){
		return this.players;
	}
	public void disconnect() {
		if(socket!=null) {
			try {
				socket.close();
			}catch(IOException e) {
			}
		}
	}

	public String getName() {
		return this.name;
	}
	public Socket getSocket() {
		return this.socket;
	}

	//Stampa
	public void sendMessage(String n, String karte, List<Integer> resource) {
		Message msgOut= new Message();
		msgOut.setMessage("ZugEnd");
		msgOut.setName(n);
		msgOut.setGewaehlteKarte(karte);
		msgOut.setResourcen(resource);
		msgOut.send(getSocket());
	}
	private void spielerKarten(Message msgIn) {
		this.zugKarten=msgIn.getNextCards();
	}
	public Message getMsg() {
		return this.msg;
	}
	public List<Message> getMessages() {
		return this.messages;
	}
	public List<String> getZugKarten() {
		return this.zugKarten;
	}
	public String getSieger() {
		return this.sieger;
	}
}
