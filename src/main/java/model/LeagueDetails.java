package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeagueDetails {
	private final IntegerProperty IDLigi;
	private final StringProperty NazwaL;
	private final StringProperty Obszar;
	private final IntegerProperty PoziomL;
	private final IntegerProperty MaksymalnaLiczbaDruzyn;
	private final IntegerProperty RokRozpoczecia;
	private final IntegerProperty RokZakonczenia;
	private final StringProperty Dyscyplina;

	// Default constructor
	public LeagueDetails(Integer IDLigi, String NazwaL, String Obszar, Integer PoziomL, Integer MaksymalnaLiczbaDruzyn,
			Integer RokRozpoczecia, Integer RokZakonczenia, String Dyscyplina) {
		this.IDLigi = new SimpleIntegerProperty(IDLigi);
		this.NazwaL = new SimpleStringProperty(NazwaL);
		this.Obszar = new SimpleStringProperty(Obszar);
		this.PoziomL = new SimpleIntegerProperty(PoziomL);
		this.MaksymalnaLiczbaDruzyn = new SimpleIntegerProperty(MaksymalnaLiczbaDruzyn);
		this.RokRozpoczecia = new SimpleIntegerProperty(RokRozpoczecia);
		this.RokZakonczenia = new SimpleIntegerProperty(RokZakonczenia);
		this.Dyscyplina = new SimpleStringProperty(Dyscyplina);
	}

	// Getters
	public Integer getIDLigi() {
		return IDLigi.get();
	}

	public String getNazwaL() {
		return NazwaL.get();
	}

	public String getObszar() {
		return Obszar.get();
	}

	public Integer getPoziomL() {
		return PoziomL.get();
	}

	public Integer getMaksymalnaLiczbaDruzyn() {
		return MaksymalnaLiczbaDruzyn.get();
	}

	public Integer getRokRozpoczecia() {
		return RokRozpoczecia.get();
	}

	public Integer getRokZakonczenia() {
		return RokZakonczenia.get();
	}

	public String getDyscyplina() {
		return Dyscyplina.get();
	}

	// Setters
	public void IDLigi(Integer value) {
		IDLigi.set(value);
	}

	public void setNazwaL(String value) {
		NazwaL.set(value);
	}

	public void setObszar(String value) {
		Obszar.set(value);
	}

	public void setPoziomL(Integer value) {
		PoziomL.set(value);
	}

	public void setMaksymalnaLiczbaDruzyn(Integer value) {
		MaksymalnaLiczbaDruzyn.set(value);
	}

	public void setRokRozpoczecia(Integer value) {
		RokRozpoczecia.set(value);
	}

	public void setDyscyplina(String value) {
		Dyscyplina.set(value);
	}

	// Property values
	public IntegerProperty IDLigiProperty() {
		return IDLigi;
	}

	public StringProperty NazwaLProperty() {
		return NazwaL;
	}

	public StringProperty ObszarProperty() {
		return Obszar;
	}

	public IntegerProperty PoziomLProperty() {
		return PoziomL;
	}

	public IntegerProperty MaksymalnaLiczbaDruzynProperty() {
		return MaksymalnaLiczbaDruzyn;
	}

	public IntegerProperty RokRozpoczeciaProperty() {
		return RokRozpoczecia;
	}

	public IntegerProperty RokZakonczeniaProperty() {
		return RokZakonczenia;
	}

	public StringProperty DyscyplinaProperty() {
		return Dyscyplina;
	}

}
