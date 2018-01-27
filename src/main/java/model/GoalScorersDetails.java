package model;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GoalScorersDetails {

	private final StringProperty Strzelec;
	private final StringProperty Klub;
	private final IntegerProperty Gole;

	public GoalScorersDetails(String Strzelec, String Klub, Integer Gole)

	{
		this.Strzelec = new SimpleStringProperty(Strzelec);
		this.Klub = new SimpleStringProperty(Klub);
		this.Gole = new SimpleIntegerProperty(Gole);

	}

	public String getStrzelec() {
		return Strzelec.get();
	}

	public String getKlub() {
		return Klub.get();
	}

	public Integer getGole() {
		return Gole.get();
	}

	public void setStrzelec(String value) {
		Strzelec.set(value);
	}

	public void setKlub(String value) {
		Klub.set(value);
	}

	public void setGole(Integer value) {
		Gole.set(value);
	}

	public StringProperty StrzelecProperty() {
		return Strzelec;
	}

	public StringProperty KlubProperty() {
		return Klub;
	}

	public IntegerProperty GoleProperty() {
		return Gole;
	}
}
