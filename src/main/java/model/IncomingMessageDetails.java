package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IncomingMessageDetails {

	private IntegerProperty idwiadomosci;
	private StringProperty autor;
	private StringProperty tresc;
	private StringProperty zwrot;

	public IncomingMessageDetails(Integer idwiadomosci, String autor, String tresc, String zwrot) {
		this.idwiadomosci = new SimpleIntegerProperty(idwiadomosci);
		this.autor = new SimpleStringProperty(autor);
		this.tresc = new SimpleStringProperty(tresc);
		this.zwrot = new SimpleStringProperty(zwrot);

	}

	public Integer getidwiadomosci() {
		return idwiadomosci.get();
	}

	public String getautor() {
		return autor.get();
	}

	public String gettresc() {
		return tresc.get();
	}

	public String getzwrot() {
		return zwrot.get();
	}

	// Setters
	public void setidwiadomosci(Integer value) {
		idwiadomosci.set(value);
	}

	public void setautor(String value) {
		autor.set(value);
	}

	public void settresc(String value) {
		tresc.set(value);
	}

	public void setzwrot(String value) {
		zwrot.set(value);
	}

	public IntegerProperty idwiadomosciProperty() {
		return idwiadomosci;
	}

	public StringProperty autorProperty() {
		return autor;
	}

	public StringProperty trescProperty() {
		return tresc;
	}

	public StringProperty zwrotProperty() {
		return zwrot;
	}

}
