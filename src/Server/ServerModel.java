package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import CommonClass.Message;

//Stampa, Schibli

public class ServerModel {
	protected List<ClientThread> clients= new ArrayList<>();
	protected List<String> players= new ArrayList<String>();
	protected ServerSocket serverSocket;
	protected List<Socket> sockets= new ArrayList<Socket>();
	private Cards cards= new Cards();
	private Map<String, Integer> listDaten=new HashMap<String,Integer>();
	Integer portNumber = 8080;
	private Message msgIn=new Message();
	private int num=0;
	private Socket clientSocket;
	private boolean stop;
	private int zugNumber=0;
	private List<Message> messages=new ArrayList<>();
	String sieger;
	private int runde=0;

	//	Stampa 
	public void connect() {
		try {
			serverSocket=new ServerSocket(portNumber);
			Runnable r= new Runnable() {
				@Override
				public void run() {
					while(!stop) {
						try {
							clientSocket = serverSocket.accept();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						msgIn =(Message) Message.receive(clientSocket);
						getMessage();
					}
				}
			};
			Thread t=new Thread(r);
			t.start();
		} catch (IOException e1) {
			e1.printStackTrace();

		}
	}
	// Stampa
	private void getMessage() {
		if(this.num<3&&this.msgIn.getMessage().equals("anmelden")) {
			sockets.add(clientSocket);
			players.add(msgIn.getName());
		}
		if(this.num==2) {
			startSpiel();
		}else if(this.num>2&&msgIn.getMessage().equals("anmelden")){
			Message msgOut= new Message();
			msgOut.setMessage("Sorry");
			msgOut.send(clientSocket);
			try {
				if(clientSocket!=null) 
					clientSocket.close();
			} catch( Exception e) {
				e.toString();
			}
		}
		this.num++;
	}
	// Stampa / Schibli
	private void startSpiel() {
		for(String player: players) {
			listDaten.put(player, 0);
		}

		cards.initgelegteKarten();
		cards.zeitalter1Cards();
		Message msgOut=new Message();
		msgOut.setPlayers(players);
		msgOut.setMessage("StartSpiel");

		for(int i=0; i<sockets.size(); i++) {
			msgOut.setNextCards(cards.getSpielerKarten().get(i));
			msgOut.send(sockets.get(i));
			ClientThread c=new ClientThread(ServerModel.this, sockets.get(i));
			clients.add(c);
			c.start();
		}
	}

	// Stampa
	public void stopServer() {
		for(ClientThread c: clients) c.stopClient();
		stop=true;
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Uninteresting
			}
		}
	}

	// Stampa
	public void broadcast(Message msg) {
		for(ClientThread c: clients) {
			c.send(msg);
		}
	}

	//Stampa
	public void messageIn(Message msg) {
		this.messages.add(msg);

		if(this.messages.size()==3) {
			gelegteKartenAktualisieren();

			Message msgOut=new Message();
			if(this.zugNumber<11) {
				msgOut.setMessage("NextZug");
				this.zugNumber++;
				if(this.runde<5) {
					this.messages= Arrays.asList(spielerKartenAktualisieren());
					if(this.zugNumber<6) {
						cards.nextZug1();
					}else {
						cards.nextZug2();
					}
					for(int i=0; i<cards.getSpielerKarten().size(); i++) {
						this.messages.get(i).setNextCards(cards.getSpielerKarten().get(i));
					}
					msgOut.setMessages(this.messages);
					broadcast(msgOut);
					this.runde++;
				}else {
					cards.zeitalter2Cards();
					msgOut.setMessages(this.messages);
					msgOut.setMessage("Zeitalter2");
					for(int i=0; i<clients.size(); i++) {
						msgOut.setNextCards(cards.getSpielerKarten().get(i));
						clients.get(i).send(msgOut);
					}
					this.runde=0;
				}
				this.messages=new ArrayList<>();
			}else {
				msgOut.setMessages(this.messages);
				msgOut.setMessage("SpielEnde");
				punkteErmitteln();
				siegerErmitteln();
				msgOut.setSieger(this.sieger);
				broadcast(msgOut);
			}
		}
	}

	//	 Schibli
	// Punkte ermitteln
	private void punkteErmitteln() {

		int tafel = 0;
		int zirkel = 0;
		int zahnrad = 0;

		// Militaerkarten
		List<Integer> militaerSchilde = militaerKartenAktualisieren();
		List<Integer> militaerPunkte = new ArrayList<>();
		for (int i = 0; i < this.messages.size(); i++) {
			militaerPunkte.add(0);
		}
		militaerPunkte = konflikteKlaeren(militaerSchilde, militaerPunkte, 0, 1);
		militaerPunkte = konflikteKlaeren(militaerSchilde, militaerPunkte, 0, 2);
		militaerPunkte = konflikteKlaeren(militaerSchilde, militaerPunkte, 1, 2);


		for(int i = 0; i<this.messages.size(); i++) {
			int punkte = 0;

			// Punkte von Münzen
			int muenzen = this.messages.get(i).getResourcen().get(8);
			punkte = (int) Math.floor(muenzen/3);

			for(int j = 0; j < cards.getSpielerGelegteKarten().get(i).size(); j++) {
				// Profanbau
				if(cards.getSpielerGelegteKarten().get(i).get(j).equals("theater") || (cards.getSpielerGelegteKarten().get(i).get(j).equals("alter"))){
					punkte += 2;
				}
				if(cards.getSpielerGelegteKarten().get(i).get(j).equals("baeder")|| (cards.getSpielerGelegteKarten().get(i).get(j).equals("tempel"))) {
					punkte += 3;
				}
				if(cards.getSpielerGelegteKarten().get(i).get(j).equals("gericht") || (cards.getSpielerGelegteKarten().get(i).get(j).equals("statue"))){
					punkte += 4;
				}
				if(cards.getSpielerGelegteKarten().get(i).get(j).equals("aquaedukt")){
					punkte += 5;
				}

				// Forschungsgebäude
				if(cards.getForschungsgebäudeCards().keySet().contains(cards.getSpielerGelegteKarten().get(i).get(j))) {
					if(cards.getForschungsgebäudeCards().get(cards.getSpielerGelegteKarten().get(i).get(j)).equals("tafel")) {
						tafel +=1;
					}
					if(cards.getForschungsgebäudeCards().get(cards.getSpielerGelegteKarten().get(i).get(j)).equals("zirkel")) {
						zirkel +=1;
					}
					if(cards.getForschungsgebäudeCards().get(cards.getSpielerGelegteKarten().get(i).get(j)).equals("zahnrad")) {
						zahnrad +=1;
					}
				}	

			}
			if(tafel>0 && zirkel>0 && zahnrad>0 ) {
				punkte += 7;
				if(tafel>1 && zirkel>1 && zahnrad>1) {
					punkte += 7;
				}					
			}
			punkte += tafel * tafel + zirkel * zirkel + zahnrad * zahnrad;	

			// Weltwunder
			int weltwunder = this.messages.get(i).getResourcen().get(7);
			if (weltwunder == 1) {
				punkte += 3;
			} else if (weltwunder == 2) {
				punkte += 5;
			} else if (weltwunder == 3) {
				punkte += 7;
			}

			//Militaer
			punkte += militaerPunkte.get(i);

			this.listDaten.put(this.messages.get(i).getName(), punkte);
			System.out.println("Punkte von "+ this.messages.get(i).getName() + ": "+ punkte);
		}	
	}
	// Schibli
	private List<Integer> konflikteKlaeren(List<Integer> militaerSchilde, List<Integer> militaerPunkte, int i, int j) {
		if (militaerSchilde.get(i) > militaerSchilde.get(j)) { 
			militaerPunkte.set(i, militaerPunkte.get(i)+3);
			militaerPunkte.set(j, militaerPunkte.get(j)-1);
		} else if (militaerSchilde.get(i) < militaerSchilde.get(j)) {
			militaerPunkte.set(i, militaerPunkte.get(i)-1);
			militaerPunkte.set(j, militaerPunkte.get(j)+3);
		} 

		return militaerPunkte;
	}
	// Schibli
	private List<Integer> militaerKartenAktualisieren() {
		List<Integer> militaerPunkte = new ArrayList<>();

		for(int i = 0; i<this.messages.size(); i++) {
			int punkte = 0;
			for(int j = 0; j < cards.getSpielerGelegteKarten().get(i).size(); j++) {
				String card = cards.getSpielerGelegteKarten().get(i).get(j);
				if(cards.getMilitaerCards().keySet().contains(card)) {
					punkte += cards.getMilitaerCards().get(card);
				}
			}
			militaerPunkte.add(punkte);
		}

		return militaerPunkte;
	}
	//Schibli
	private void siegerErmitteln() {
		int t=0;
		for(Map.Entry<String, Integer> e: this.listDaten.entrySet()) {
			if(e.getValue()>t) {
				this.sieger=e.getKey();
				t=e.getValue();
			}
		}
	}
	//Schibli
	/* gelegte karten speichern */
	private void gelegteKartenAktualisieren() {
		for(int i=0; i<this.players.size(); i++) {
			String n=this.players.get(i);
			for(Message m: this.messages) {
				if(m.getName().equals(n)){
					cards.getSpielerGelegteKarten().get(i).add(m.getGewaehlteKarte());
				}
			}
		}
	}
	//Schibli
	private Message[] spielerKartenAktualisieren() {
		Message[] listOut=new Message[3];
		for(int i=0; i<this.players.size(); i++) {
			String n=this.players.get(i);
			for(Message m: this.messages) {
				if(m.getName().equals(n)){
					// falls die karte verkauft wurde, oder ein weltwunder ist: [gewaehlteKarte (verkauft)] ==> [gewaehlteKarte]
					String gewaehlteKarte = m.getGewaehlteKarte().split(" ")[0];
					cards.getSpielerKarten().get(i).remove(gewaehlteKarte);
					listOut[i]=m;
				}
			}
		}
		return listOut;		
	}
}

