package Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;

//Schibli, Stampa

public class KartenCheck {
	private ClientModel model;
	boolean a;
	private String name;
	private List<String> players;
	public KartenCheck(ClientModel mod) {
		this.model=mod;
	}

	//Schibli	
	/* karte wird verkauft und 3 neue muenzen werden verdient */ 
	public CardInfo sellCard(List<List<Label>> resourcen) {
		this.players=model.getPlayers();
		this.name=model.getName();

		for(int i=0; i<this.players.size(); i++) {
			if(this.name.equals(this.players.get(i))) {
				Label l=(Label) resourcen.get(i).get(8);
				Integer cnt= Integer.parseInt(l.getText());	
				cnt = cnt + 3;
				l.setText(Integer.toString(cnt));
			}
		}
		return new CardInfo(true, resourcen);
	}
	//Schibli
	public CardInfo resourceCheck(String card, List<List<Label>> resourcen) {
		this.players=model.getPlayers();
		this.name=model.getName();

		Map<Integer,Integer> checkResources = new HashMap<>();
		Map<Integer,Integer> addResources = new HashMap<>();
		Integer price = 0;
		Boolean b = true;

		// 0: holz, 1: erz, 2: stein, 3: ziegel, 4: glas, 5: papyrus, 6: stoff, 7: board, 8: muenze
		switch(card) {
		case "befestigungsanlage" :
			checkResources.put(0,1);
			break;
		case "kaserne" :
			checkResources.put(1,1);
			break;
		case "wachturm" :
			checkResources.put(3,1);
			break;
		case "baeder" :
			checkResources.put(2,1);
			break;
		case "altar" :
			break;
		case "theater" :
			break;
		case "apotheke" :
			checkResources.put(6,1);
			break;
		case "skriptorum" :
			checkResources.put(5,1);
			break;
		case "werkstatt" :
			checkResources.put(4,1);
			break;
		case "markt" :
			break;
		case "kontorOst" :
			break;
		case "kontorWest" :
			break;
		case "tongrube" :
			checkResources.put(8,1);
			price = 1;
			addResources.put(1,1);
			addResources.put(3, 1);
			break;
		case "holzplatz" :
			addResources.put(0,1);
			break;
		case "ziegelei" :
			addResources.put(3,1);
			break;
		case "erzbergwerk" :
			addResources.put(1,1);
			break;
		case "forstwirtschaft" :
			price = 1;
			addResources.put(0,1);
			addResources.put(2,1);
			break;
		case "steinbruch" :
			addResources.put(2,1);
			break;
		case "presse" :
			addResources.put(5,1);
			break;
		case "presse2" :
			addResources.put(5,1);
			break;
		case "webstuhl" :
			addResources.put(6,1);
			break;
		case "webstuhl2" :
			addResources.put(6,1);
			break;
		case "glashuette" :
			addResources.put(4,1);
			break;
		case "glashuette2" :
			addResources.put(4,1);
			break;
		case "schiessplatz" :
			checkResources.put(0,2);
			checkResources.put(1,1);
			break;
		case "mauern" :
			checkResources.put(2,3);
			break;
		case "staelle" :
			checkResources.put(0,1);
			checkResources.put(1,1);
			checkResources.put(3,1);
			break;
		case "aquaedukt" :
			checkResources.put(2,3);
			break;
		case "tempel" :
			checkResources.put(0,1);
			checkResources.put(3,1);
			checkResources.put(4,1);
			break;
		case "statue" :
			checkResources.put(0,1);
			checkResources.put(1,2);
			break;
		case "gericht" :
			checkResources.put(3,2);
			checkResources.put(6,1);
			break;
		case "schule" :
			checkResources.put(0,1);
			checkResources.put(5,1);
			break;
		case "arzneiausgabe" :
			checkResources.put(1,2);
			checkResources.put(4,1);
			break;
		case "laboratorium" :
			checkResources.put(3,2);
			checkResources.put(5,1);
			break;
		case "bibliothek" :
			checkResources.put(2,2);
			checkResources.put(6,1);
			break;
		case "forum" :
			checkResources.put(3,2);
			addResources.put(4,1);
			addResources.put(5,1);
			addResources.put(6,1);
			break;
		case "weinberg" :
			break;
		case "karawanserei" :
			checkResources.put(0,2);
			addResources.put(0,1);
			addResources.put(1,1);
			addResources.put(2,1);
			addResources.put(3,1);
			break;
		case "saegewerk" :
			checkResources.put(8,1);
			price = 1;
			addResources.put(0,2);
			break;
		case "ziegelbrennerei" :
			checkResources.put(8,1);
			price = 1;
			addResources.put(3,2);
			break;
		case "bildhauerei" :
			checkResources.put(8,1);
			price = 1;
			addResources.put(2,2);
			break;
		case "giesserei" :
			checkResources.put(8,1);
			price = 1;
			addResources.put(1,2);
			break;

		}

		if (checkResources.size()>0) {
			/* checken, ob noetige resourcen vorhanden sind und karte gelegt werden kann */
			b = check(resourcen, checkResources);
		}

		if (b && price > 0) {
			/*muenzen zahlen falls noetig */
			muenzeZahlen(resourcen, price);
		}

		if (b && addResources.size()>0) {
			/* neue resourcen hinzufuegen */
			addResources(resourcen, addResources);
		}

		return new CardInfo(b, resourcen);
	}

	//Schibli

	/* b = true : resourcen sind vorhanden und karte kann gelegt werden
	 * b = false: resourcen fehlen; karte kann nicht gelegt werden
	 */

	private Boolean check(List<List<Label>> resourcen, Map<Integer,Integer> check) {
		Boolean b = true;

		for(int i=0; i<this.players.size(); i++) {
			if(this.name.equals(this.players.get(i))) {
				for (Integer c : check.keySet()) {
					Label l=(Label) resourcen.get(i).get(c);
					Integer cnt = Integer.parseInt(l.getText());
					if (cnt < check.get(c)) {
						b = false;
					}
				}
			}
		}

		return b;
	}

	//Schibli

	/* resourcen werden aktualisiert */ 
	private void addResources(List<List<Label>> resourcen, Map<Integer,Integer> add) {
		for(int i=0; i<this.players.size(); i++) {
			if(this.name.equals(this.players.get(i))) {
				for (Integer a : add.keySet()) {
					Label l=(Label) resourcen.get(i).get(a);
					Integer cnt= Integer.parseInt(l.getText());
					cnt = cnt + add.get(a);
					l.setText(Integer.toString(cnt));
				}
			}
		}
	}
	//Schibli
	/* muenzen anzahl wird aktualisiert */ 
	private void muenzeZahlen(List<List<Label>> resourcen, int preis) {
		for(int i=0; i<this.players.size(); i++) {
			if(this.name.equals(this.players.get(i))) {
				Label l=(Label) resourcen.get(i).get(8);
				Integer cnt= Integer.parseInt(l.getText());	
				cnt = cnt - preis;
				l.setText(Integer.toString(cnt));
			}
		}
	}
	//Schibli
	public CardInfo weltwunderCheck(List<List<Label>> resourcen) {
		this.players=model.getPlayers();
		this.name=model.getName();

		for(int i=0; i<this.players.size(); i++) {
			if(this.name.equals(this.players.get(i))) {
				Label l=(Label) resourcen.get(i).get(7);
				Integer weltwunder = Integer.parseInt(l.getText());	

				// zwei steine
				if (weltwunder == 0) {
					Label l2=(Label) resourcen.get(i).get(2);
					Integer steine = Integer.parseInt(l2.getText());	
					if (steine > 1) {
						l.setText(Integer.toString(weltwunder + 1));
						return new CardInfo(true, resourcen);
					}

					// drei holz
				} else if (weltwunder == 1) {
					Label l2=(Label) resourcen.get(i).get(0);
					Integer holz = Integer.parseInt(l2.getText());	
					if (holz > 2) {
						l.setText(Integer.toString(weltwunder + 1));
						return new CardInfo(true, resourcen);
					}

					// vier steine
				} else if (weltwunder == 2) {
					Label l2=(Label) resourcen.get(i).get(2);
					Integer steine = Integer.parseInt(l2.getText());	
					if (steine > 3) {
						l.setText(Integer.toString(weltwunder + 1));
						return new CardInfo(true, resourcen);
					}
				} 
			}
		}
		return new CardInfo(false, resourcen);
	}
}