package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeamStatDetails {
	private final IntegerProperty pozycja;
	private final StringProperty druzyna;
	private final IntegerProperty rozegranemecze;
	private final IntegerProperty BramkiZdobyte;
	private final IntegerProperty BramkiStracone;
	private final IntegerProperty Punkty;

	// Default constructor
	public TeamStatDetails(Integer pozycja, String druzyna, int rozegranemecze, int BramkiZdobyte, int BramkiStracone,
			int Punkty) {
		this.pozycja = new SimpleIntegerProperty(pozycja);
		this.druzyna = new SimpleStringProperty(druzyna);
		this.rozegranemecze = new SimpleIntegerProperty(rozegranemecze);
		this.BramkiZdobyte = new SimpleIntegerProperty(BramkiZdobyte);
		this.BramkiStracone = new SimpleIntegerProperty(BramkiStracone);
		this.Punkty = new SimpleIntegerProperty(Punkty);
	}

	public Integer getpozycja() {
		return pozycja.get();
	}

	public String getdruzyna() {
		return druzyna.get();
	}

	public Integer getrozegranemecze() {
		return rozegranemecze.get();
	}

	public Integer getBramkiZdobyte() {
		return BramkiZdobyte.get();
	}

	public Integer getBramkiStracone() {
		return BramkiStracone.get();
	}

	public Integer getPunkty() {
		return Punkty.get();
	}

	public void setdruzyna(String value) {
		druzyna.set(value);
	}

	public void setpozycja(Integer value) {
		pozycja.set(value);
	}

	public void setrozegranemecze(Integer value) {
		rozegranemecze.set(value);
	}

	public void setbramkizdobyte(Integer value) {
		BramkiZdobyte.set(value);
	}

	public void setbramkistracone(Integer value) {
		BramkiStracone.set(value);
	}

	public void setPunkty(Integer value) {
		Punkty.set(value);
	}

	public IntegerProperty pozycjaProperty() {
		return pozycja;
	}

	public StringProperty druzynaProperty() {
		return druzyna;
	}

	public IntegerProperty rozegranemeczeProperty() {
		return rozegranemecze;
	}

	public IntegerProperty bramkizdobyteProperty() {
		return BramkiZdobyte;
	}

	public IntegerProperty bramkistraconeProperty() {
		return BramkiStracone;
	}

	public IntegerProperty PunktyProperty() {
		return Punkty;
	}

}
