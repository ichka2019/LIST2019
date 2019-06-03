package Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Cards {

	private List<String> stapelKarten;
	private List<String> spielKarten1=new ArrayList<>();
	private List<String> spielKarten2=new ArrayList<>();
	private List<String> spielKarten3=new ArrayList<>();
	private List<List<String>> spielerKarten=new ArrayList<>();
	private List<List<String>> spielerGelegteKarten=new ArrayList<>();
	private HashMap<String, String> forschungsgebäudeCards=new HashMap<>();
	private HashMap<String, Integer> militaerCards=new HashMap<>();
	HashMap<String, Integer> spieler1Punkte= new HashMap<>();
	HashMap<String, Integer> spieler2Punkte= new HashMap<>();
	HashMap<String, Integer> spieler3Punkte= new HashMap<>();

	// Stampa
	public void zeitalter1Cards() {
		String[] list1= { "befestigungsanlage", "kaserne", "wachturm", "theater", "altar", "baeder", "apotheke", "skriptorium", "werkstatt", "markt", 
				"kontorOst", "kontorWest", "ziegelei", "erzbergwerk", "holzplatz", "tongrube", "forstwirtschaft", "steinbruch", "webstuhl", "presse",
		"glashuette"};
		kartenSetzen(list1);
	}
	//Stampa
	public void zeitalter2Cards() {
		String[] list2= {"schiessplatz", "mauern", "staelle", "aquaedukt", "tempel", "statue", "gericht", "schule", "arzneiausgabe", "laboratorium", "bibliothek", "forum",
				"weinberg", "karawanserei", "saegewerk", "ziegelbrennerei", "bildhauerei", "giesserei", "webstuhl2", "presse2", "glashuette2"};
		this.spielKarten1.clear();
		this.spielKarten2.clear();
		this.spielKarten3.clear();
		kartenSetzen(list2);
	}

	// Schibli
	public void setForschungsgebäudeCards() {
		String[] zeitalterCards= {"apotheke", "skriptorium", "werkstatt", "schule", "arzneiausgabe", "laboratorium", "bibliothek"};
		String [] symbole= {"zirkel", "tafel", "zahnrad", "tafel", "zirkel", "zahnrad", "tafel"};
		for(int i=0; i<symbole.length; i++)
			this.forschungsgebäudeCards.put(zeitalterCards[i], symbole[i]);
	}

	//Schibli
	public void setMilitaerCards() {
		String[] militaerCards= {"befestigungsanlage", "kaserne", "wachturm", "mauern", "staelle", "schiessplatz"};
		Integer [] schilde= {1, 1, 1, 2, 2, 2};
		for(int i=0; i<schilde.length; i++)
			this.militaerCards.put(militaerCards[i], schilde[i]);
	}

	//Schibli
	public void initgelegteKarten() {
		this.spielerGelegteKarten.add(new ArrayList<>());
		this.spielerGelegteKarten.add(new ArrayList<>());
		this.spielerGelegteKarten.add(new ArrayList<>());
	}

	// Stampa
	private void kartenSetzen(String[] list) {
		this.stapelKarten=Arrays.asList(list);

		Collections.shuffle(this.stapelKarten);
		for(int i=0; i<7; i++) {
			this.spielKarten1.add(this.stapelKarten.get(i)); 
		}
		for(int j=7; j<14; j++) {
			this.spielKarten2.add(this.stapelKarten.get(j)); 
		}
		for(int k=14; k<21; k++) {
			this.spielKarten3.add(this.stapelKarten.get(k)); 
		}
		this.spielerKarten.add(this.spielKarten1);
		this.spielerKarten.add(this.spielKarten2);
		this.spielerKarten.add(this.spielKarten3);		
	}

	// Schibli
	public List<List<String>> getSpielerKarten() {
		return this.spielerKarten;
	}


	public HashMap<String, String> getForschungsgebäudeCards(){
		return this.forschungsgebäudeCards;
	}

	public HashMap<String, Integer> getMilitaerCards(){
		return this.militaerCards;
	}

	public List<List<String>> getSpielerGelegteKarten() {
		return this.spielerGelegteKarten;
	}


	// Stampa
	public void nextZug1() {
		List<List<String>> list= new ArrayList<>();
		list.add(this.spielerKarten.get(2));
		list.add(this.spielerKarten.get(0));
		list.add(this.spielerKarten.get(1
				));
		this.spielerKarten=list;
	}
	public void nextZug2() {
		List<List<String>> list= new ArrayList<>();
		list.add(this.spielerKarten.get(1));
		list.add(this.spielerKarten.get(2));
		list.add(this.spielerKarten.get(0));
		this.spielerKarten=list;
	}

}
