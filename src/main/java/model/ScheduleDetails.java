package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleDetails {

	private final StringProperty Team1;
	private final StringProperty Team2;
	private final IntegerProperty kolejka;
	private final StringProperty GoleGosp;
	private final StringProperty GoleGosc;
	private final StringProperty terminmeczu;
	private final StringProperty godzina;

	// Default constructor
	public ScheduleDetails(int kolejka, String terminmeczu, String godzina, String Team1, String GoleGosp, String Team2,
			String GoleGosc) {
		this.kolejka = new SimpleIntegerProperty(kolejka);
		this.terminmeczu = new SimpleStringProperty(terminmeczu);
		this.godzina = new SimpleStringProperty(godzina);
		this.Team1 = new SimpleStringProperty(Team1);
		this.GoleGosp = new SimpleStringProperty(GoleGosp);
		this.Team2 = new SimpleStringProperty(Team2);
		this.GoleGosc = new SimpleStringProperty(GoleGosc);
	}

	public String getTeam1() {
		return Team1.get();
	}

	public String getterminmeczu() {
		return terminmeczu.get();
	}

	public String getgodzina() {
		return godzina.get();
	}

	public String getTeam2() {
		return Team2.get();
	}

	public void setTeam1(String value) {
		Team1.set(value);
	}

	public void setterminmeczu(String value) {
		terminmeczu.set(value);
	}

	public void setgodzina(String value) {
		godzina.set(value);
	}

	public void setTeam2(String value) {
		Team2.set(value);
	}

	public StringProperty Team1Property() {
		return Team1;
	}

	public StringProperty Team2Property() {
		return Team2;
	}

	public StringProperty terminmeczuProperty() {
		return terminmeczu;
	}

	public StringProperty godzinaProperty() {
		return godzina;
	}

	public IntegerProperty kolejkaProperty() {
		return kolejka;
	}

	public Integer getKolejka() {
		return kolejka.get();
	}

	public String getGoleGosp() {
		return GoleGosp.get();
	}

	public String getGoleGosc() {
		return GoleGosc.get();
	}

	public void setKolejka(Integer value) {
		kolejka.set(value);
	}

	public void setGoleGosp(String value) {
		GoleGosp.set(value);
	}

	public void setGoleGosc(String value) {
		GoleGosc.set(value);
	}

}
