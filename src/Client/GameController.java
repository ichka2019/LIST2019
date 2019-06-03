package Client;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CommonClass.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Schibli/Stampa
public class GameController {
	private static ClientModel model;
	private KartenCheck kCheck;
	private List <Button> stapel;
	private List <ImageView> stapelImages;
	private List<String> players= new ArrayList<>();
	private List<Label> spieler1Data=new ArrayList<>();
	private List<Label> spieler2Data=new ArrayList<>();
	private List<Label> spieler3Data=new ArrayList<>();
	private List<List<Label>> resourcen=new ArrayList<>();
	private List<String> gezogeneKarten=new ArrayList<>();
	private CardInfo cardInfo;
	private List<Label> cards=new ArrayList<>();
	private List<Message> messages=null;
	private List<String> nextCards;
	private String gewaehlteKarte;
	private int runde=0;
	@FXML
	private Label welcome;
	@FXML
	public Label text;

	@FXML
	private Button ok;
	@FXML
	private Label board;

	@FXML
	private ImageView img1;

	@FXML
	private ImageView img2;

	@FXML
	private ImageView img3;

	@FXML
	private ImageView img4;

	@FXML
	private ImageView img5;

	@FXML
	private ImageView img6;

	@FXML
	private ImageView img7;

	@FXML
	private Button stapelCard1;

	@FXML
	private Button stapelCard2;

	@FXML
	private Button stapelCard3;

	@FXML
	private Button stapelCard4;

	@FXML
	private Button stapelCard5;

	@FXML
	private Button stapelCard6;

	@FXML
	private Button stapelCard7;

	@FXML
	private Label spielerName;

	@FXML
	private Label spieler1;

	@FXML
	private Label spieler2;

	@FXML
	private Label spieler3;

	@FXML
	private Label cards1;

	@FXML
	private Label cards2;

	@FXML
	private Label cards3;

	@FXML
	private Label cards4;

	@FXML
	private Label cards5;

	@FXML
	private Label cards6;

	@FXML
	private Label holz1;

	@FXML
	private Label holz2;

	@FXML
	private Label holz3;

	@FXML
	private Label erz1;

	@FXML
	private Label erz2;

	@FXML
	private Label erz3;

	@FXML
	private Label stein1;

	@FXML
	private Label stein2;

	@FXML
	private Label stein3;

	@FXML
	private Label ziegel1;

	@FXML
	private Label ziegel2;

	@FXML
	private Label ziegel3;

	@FXML
	private Label glas1;

	@FXML
	private Label glas2;

	@FXML
	private Label glas3;

	@FXML
	private Label papyrus1;

	@FXML
	private Label papyrus2;

	@FXML
	private Label papyrus3;

	@FXML
	private Label stoff1;

	@FXML
	private Label stoff2;

	@FXML
	private Label stoff3;

	@FXML
	private Label board1;

	@FXML
	private Label board2;

	@FXML
	private Label board3;
	@FXML
	private Label muenze1;

	@FXML
	private Label muenze2;

	@FXML
	private Label muenze3;

	@FXML
	private Button weltwunderBauen;

	@FXML
	private Button gebaeudeBauen;

	@FXML
	private Button verkaufen;
	//Stampa
	@FXML
	void spielBeginnen(ActionEvent event) throws Exception {
		Button button=(Button) event.getSource();
		button.setText("Zeitalter 1");;
		this.kCheck=new KartenCheck(model);
		setStapelKarten();
		setGewaehlteKartenLabel1();
		setResourcenLabels();
		setPlayers();
		setGewaehlteKartenLabel1();
		kartenSetzen();
	}
	//Stampa
	@FXML
	void checkCard(ActionEvent event) {
		Button button= (Button) event.getSource();
		this.gebaeudeBauen.setDisable(false);
		this.verkaufen.setDisable(false);
		this.weltwunderBauen.setDisable(false);
		this.gewaehlteKarte= button.getText();
	}
	//Schibli
	@FXML
	void weltwunderBauenCard(ActionEvent event) {
		this.cardInfo=kCheck.weltwunderCheck(this.resourcen);

		if(!this.cardInfo.getB()) {
			this.text.setText("Weltwunder kann nicht gebaut werden!");
			this.gebaeudeBauen.setDisable(true);
			this.verkaufen.setDisable(true);
			this.weltwunderBauen.setDisable(true);
		} else {
			List<Integer> resourcenZahlen=new ArrayList<>();
			List<Label> list=new ArrayList<>();
			for(int i=0; i<this.players.size(); i++) {
				if(model.getName().equals(this.players.get(i))) {
					list= this.cardInfo.getResourcen().get(i);
					this.cards.get(i).setText(this.cards.get(i).getText()+this.gewaehlteKarte + " (weltwunder)\n");
				}
			}
			for(Label l: list) {
				Integer in=Integer.parseInt(l.getText());
				resourcenZahlen.add(in);
			}
			for(Button b: this.stapel)
				b.setDisable(true);

			this.gezogeneKarten.add(this.gewaehlteKarte + " (weltwunder)");
			model.sendMessage(model.getName(), this.gewaehlteKarte + " (weltwunder)", resourcenZahlen);
			setDaten();
		}
	}
	//Schibli
	@FXML
	void gebaeudeBauenCard(ActionEvent event) throws URISyntaxException {	
		this.cardInfo=kCheck.resourceCheck(this.gewaehlteKarte, this.resourcen);
		if(!this.cardInfo.getB()) {
			this.text.setText("Gebäude kann nicht gebaut werden!");
			this.gebaeudeBauen.setDisable(true);
			this.verkaufen.setDisable(true);
			this.weltwunderBauen.setDisable(true);
		} else {
			List<Integer> resourcenZahlen=new ArrayList<>();
			List<Label> list=new ArrayList<>();
			for(int i=0; i<this.players.size(); i++) {
				if(model.getName().equals(this.players.get(i))) {
					list= this.cardInfo.getResourcen().get(i);
					this.cards.get(i).setText(this.cards.get(i).getText()+this.gewaehlteKarte+"\n");
				}
			}
			for(Label l: list) {
				Integer in=Integer.parseInt(l.getText());
				resourcenZahlen.add(in);
			}
			for(Button b: this.stapel)
				b.setDisable(true);

			this.gezogeneKarten.add(this.gewaehlteKarte);
			model.sendMessage(model.getName(), this.gewaehlteKarte, resourcenZahlen);
			setDaten();
		}
	}
	//Schibli
	@FXML
	/* Karte verkaufen und 3 muenzen bekommen */
	void verkaufen(ActionEvent event) {
		this.cardInfo=kCheck.sellCard(this.resourcen);

		List<Integer> resourcenZahlen=new ArrayList<>();
		List<Label> list=new ArrayList<>();
		for(int i=0; i<this.players.size(); i++) {
			if(model.getName().equals(this.players.get(i))) {
				list= this.cardInfo.getResourcen().get(i);
				this.cards.get(i).setText(this.cards.get(i).getText()+this.gewaehlteKarte + " (verkauft)\n");
			}
		}
		for(Label l: list) {
			Integer in=Integer.parseInt(l.getText());
			resourcenZahlen.add(in);
		}
		for(Button b: this.stapel)
			b.setDisable(true);

		this.gezogeneKarten.add(this.gewaehlteKarte + " (verkauft)");
		model.sendMessage(model.getName(), this.gewaehlteKarte + " (verkauft)", resourcenZahlen);
		setDaten();
	}

	// Stampa 
	public void setDaten() {
		model.getMessage();
		this.messages=model.getMessages();
		gegnerKartenSetzen();
		if(this.runde<5) {
			msgKartenSetzen();
			for(Button b: this.stapel)
				b.setDisable(false);
			this.runde++;
		}else if(model.getMsg().getMessage().equals("Zeitalter2")){
			this.ok.setText("Zeitalter 2");
			this.runde=0;
			setStapelKarten();
			setGewaehlteKartenLabel2();
			kartenSetzen();
		}else{
			spielEnden();
		}
		this.gebaeudeBauen.setDisable(true);
		this.verkaufen.setDisable(true);
		this.weltwunderBauen.setDisable(true);
	}

	// Stampa
	private void spielEnden() {
		System.out.println(model.getSieger());
		this.welcome.setText("Der Sieger ist:  "+ model.getSieger());
		this.welcome.setFont(new Font("Copperplate", 40));
		this.welcome.setTextFill(Color.web("#e40163"));
		this.gebaeudeBauen.setVisible(false);
		this.weltwunderBauen.setVisible(false);
		this.verkaufen.setVisible(false);
		this.ok.setVisible(false);
		this.spielerName.setVisible(false);
		this.text.setVisible(false);

		for(Button b: this.stapel) {
			b.setVisible(false);
		}
	}

	// Stampa
	private void gegnerKartenSetzen() {
		for(int i=0; i<this.messages.size(); i++) {
			Message m=this.messages.get(i);
			if(!model.getName().equals(m.getName())){
				for(int k=0; k<this.players.size(); k++) {
					if(this.players.get(k).equals(m.getName())) {
						this.cards.get(k).setText(this.cards.get(k).getText()+m.getGewaehlteKarte()+"\n");
						List<Label> labels= this.resourcen.get(k);
						for(int j=0; j<labels.size(); j++) {
							labels.get(j).setText(Integer.toString(m.getResourcen().get(j)));
						}
					}
				}
			}		
		}
	}

	// Stampa 
	public void setPlayers() {
		this.players= model.getPlayers();
		spielerName.setText(model.getName());
		Label[] names= {spieler1, spieler2, spieler3};
		for(int i=0; i<names.length; i++) {
			names[i].setText(this.players.get(i));
		}
	}

	// Stampa / Schibli
	private void msgKartenSetzen() {
		for(int i=0; i<this.messages.size(); i++) {
			Message m=this.messages.get(i);
			if(model.getName().equals(m.getName())){
				this.nextCards=m.getNextCards();
				this.stapel.get(this.stapel.size()-1).setVisible(false);
				this.stapel.remove((this.stapel.size()-1));
				for(int t=0; t<this.stapel.size(); t++) {
					this.stapel.get(t).setText(this.nextCards.get(t));
					String uri = this.nextCards.get(t) + ".jpg";
					Image img = new Image("/images/cards/" + uri,true);	
					this.stapelImages.get(t).setImage(img);
				}
				this.text.setText("Bitte Karte wählen!");
				this.gebaeudeBauen.setDisable(false);
				this.weltwunderBauen.setDisable(false);
				this.verkaufen.setDisable(false);
			}
		}
	}
	//Stampa
	private void setResourcenLabels() {
		Label[] spieler1Ressourcen= {holz1, erz1, stein1, ziegel1, glas1, papyrus1, stoff1, board1, muenze1};
		Label[] spieler2Ressourcen= {holz2, erz2, stein2, ziegel2, glas2, papyrus2, stoff2, board2, muenze2};
		Label[] spieler3Ressourcen= {holz3, erz3, stein3, ziegel3, glas3, papyrus3, stoff3, board3, muenze3};
		this.spieler1Data=(Arrays.asList(spieler1Ressourcen));
		this.spieler2Data=(Arrays.asList(spieler2Ressourcen));
		this.spieler3Data=(Arrays.asList(spieler3Ressourcen));
		this.resourcen.add(spieler1Data);
		this.resourcen.add(spieler2Data);
		this.resourcen.add(spieler3Data);
	}
	//Stampa
	private void setGewaehlteKartenLabel1() {
		Label [] labs= {cards1, cards2, cards3};
		this.cards=Arrays.asList(labs);
	}
	//Stampa
	private void setStapelKarten() {
		Button[] st= {stapelCard1,stapelCard2,stapelCard3,stapelCard4,stapelCard5,stapelCard6,stapelCard7};
		ImageView[] images = {img1,img2,img3,img4,img5,img6,img7};
		this.stapel=new ArrayList<Button>(Arrays.asList(st));
		this.stapelImages=new ArrayList<ImageView>(Arrays.asList(images));
	}
	//Stampa
	public void setPlayers(String n) {
		spielerName.setText(n);
		Label[] names= {spieler1, spieler2, spieler3};
		for(int i=0; i<names.length; i++) {
			names[i].setText(this.players.get(i));
		}
	}
	//Stampa
	public void kartenSetzen() {
		for(Button b: this.stapel) {
			b.setVisible(true);
			b.setDisable(false);
		}
		for(int i=0; i<this.stapel.size(); i++) {
			this.stapel.get(i).setText(model.getZugKarten().get(i));
			String uri = model.getZugKarten().get(i) + ".jpg";
			Image img = new Image("/images/cards/" + uri,true);			
			this.stapelImages.get(i).setImage(img);
		}
		this.text.setText("Bitte Karte wählen!");
		this.gebaeudeBauen.setDisable(true);
		this.verkaufen.setDisable(true);
		this.weltwunderBauen.setDisable(true);
	}
	//Stampa
	public void setGewaehlteKartenLabel2() {
		Label [] lab= {cards4, cards5, cards6};
		this.cards=Arrays.asList(lab);
	}

	public static void setModel(ClientModel clientModel) {
		model= clientModel;
	}
}
