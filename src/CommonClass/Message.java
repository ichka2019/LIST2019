package CommonClass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// Stampa
public class Message implements Serializable{
	private static final long serialVersionUID = 8540558636874947755L;
	private String name;
	private String message;
	private List<String> players;
	private List<String> stapelKarten= new ArrayList<String>();
	private HashMap<String, Integer> daten;
	private String gewinner;
	private String gewaehlteKarte;
	private List<Integer> resourcen;
	private List<Message> messages=new ArrayList<>();
	private List<String> nextCards;
	private String sieger;

	public static Message receive(Socket socket) {
		Message msg = null;
		if(socket!=null) {
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(socket.getInputStream());
				msg = (Message) in.readObject();

			} catch (IOException e) {
				System.out.println("catch1");
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("catch2");
				//e.printStackTrace();
			}
		}
		return msg;
	}

	//Stampa
	public void send(Socket socket) {
		if(socket!=null) {
			try {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(this);
				out.flush();
			}catch(IOException e) {
				System.out.println("Exception");
			}
		}
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name;
	}
	public void setMessage(String message) {
		this.message=message;
	}
	public String getMessage() {
		return this.message;
	}
	public void setPlayers(List<String> players) {
		this.players=players;		
	}
	public List<String> getPlayers(){
		return this.players;
	}
	public String toString() {
		return this.message;
	}
	public List<String> getCards(){
		return this.stapelKarten;
	}
	public HashMap<String, Integer> getDaten() {
		return this.daten;
	}
	public String getGewinner() {
		return this.gewinner;
	}
	public void setGewaehlteKarte(String gewaehlt) {
		this.gewaehlteKarte= gewaehlt;

	}
	public String getGewaehlteKarte() {
		return this.gewaehlteKarte;
	}
	public void setResourcen(List<Integer> resourcenZahlen) {
		this.resourcen=resourcenZahlen;
	}
	public List<Integer> getResourcen() {
		return this.resourcen;
	}
	public void setMessages(List<Message> list) {
		this.messages=list;
	}
	public List<Message> getMessages() {
		return this.messages;
	}

	public void setNextCards(List<String> list) {
		this.nextCards= list;

	}
	public List<String> getNextCards(){
		return this.nextCards;
	}
	public void setSieger(String s) {
		this.sieger= s;
	}
	public String getSieger() {
		return this.sieger;
	}


}
