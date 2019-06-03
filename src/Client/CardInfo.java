package Client;

//Stampa / Schibli
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;

public class CardInfo {

	private boolean b;
	private List<List<Label>> resourcen=new ArrayList<>();

	public CardInfo(boolean bool, List<List<Label>> resourcen) {
		this.b=bool;
		this.resourcen=resourcen;
	}

	public boolean getB() {
		return b;
	}
	public void setB(boolean b) {
		this.b = b;
	}
	public List<List<Label>> getResourcen() {
		return resourcen;
	}
	public void setResourcen(List<List<Label>> resourcen) {
		this.resourcen = resourcen;
	}
	public String toString() {
		return ""+this.b+" ";
	}

}
