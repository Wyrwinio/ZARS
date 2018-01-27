package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutgoingMessageDetails {

	private IntegerProperty idwiadomosci;
	private StringProperty autor;
	private StringProperty adresat;
	private StringProperty tresc;

	public OutgoingMessageDetails(Integer idwiadomosci, String autor, String adresat, String tresc) {
		this.idwiadomosci = new SimpleIntegerProperty(idwiadomosci);
		this.autor = new SimpleStringProperty(autor);
		this.adresat = new SimpleStringProperty(adresat);
		this.tresc = new SimpleStringProperty(tresc);

	}

	public Integer getidwiadomosci() {
		return idwiadomosci.get();
	}

	public String getautor() {
		return autor.get();
	}

	public String getadresat() {
		return adresat.get();
	}

	public String gettresc() {
		return tresc.get();
	}

	// Setters
	public void setidwiadomosci(Integer value) {
		idwiadomosci.set(value);
	}

	public void setautor(String value) {
		autor.set(value);
	}

	public void setadresat(String value) {
		adresat.set(value);
	}

	public void settresc(String value) {
		tresc.set(value);
	}

	public IntegerProperty idwiadomosciProperty() {
		return idwiadomosci;
	}

	public StringProperty autorProperty() {
		return autor;
	}

	public StringProperty adresatProperty() {
		return adresat;
	}

	public StringProperty trescProperty() {
		return tresc;
	}

}