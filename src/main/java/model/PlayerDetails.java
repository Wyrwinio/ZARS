package model;



import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerDetails {

    private final IntegerProperty IDZawodnik;
    private final StringProperty Imie;
    private final StringProperty Nazwisko;
    private final StringProperty DataUrodzenia;
    private final IntegerProperty Waga;
    private final IntegerProperty Wzrost;
    private final StringProperty Pozycja;
    private final StringProperty Druzyna;

    //Default constructor
    public PlayerDetails(Integer IDZawodnik, String Imie, String Nazwisko, String DataUrodzenia, Integer Waga, Integer Wzrost, String Pozycja, String Druzyna) {
        this.IDZawodnik = new SimpleIntegerProperty(IDZawodnik);
        this.Imie = new SimpleStringProperty(Imie);
        this.Nazwisko = new SimpleStringProperty(Nazwisko);    
        this.DataUrodzenia = new SimpleStringProperty(DataUrodzenia);  
        this.Waga = new SimpleIntegerProperty(Waga);
        this.Wzrost = new SimpleIntegerProperty(Wzrost);
        this.Pozycja = new SimpleStringProperty(Pozycja);  
        this.Druzyna = new SimpleStringProperty(Druzyna);  
    }

    //Getters
    public Integer getIDZawodnik() {
        return IDZawodnik.get();
    }

    public String getImie() {
        return Imie.get();
    }

    public String getNazwisko() {
        return Nazwisko.get();
    }
    public String getDataUrodzenia(){
    	return DataUrodzenia.get();
    }
    public Integer getWaga() {
        return Waga.get();
    }
    public Integer getWzrost() {
        return Wzrost.get();
    }
    public String getPozycja() {
        return Pozycja.get();
    }
    public String getDruzyna() {
        return Druzyna.get();
    }

    //Setters
    public void setIDZawodnik(Integer value) {
    	IDZawodnik.set(value);
    }

    public void setImie(String value) {
    	Imie.set(value);
    }

    public void setNazwisko(String value) {
    	Nazwisko.set(value);
    }
    public void setDataUrodzenia(String value)
    {
    	DataUrodzenia.set(value);
    }
    public void setWaga(Integer value) {
    	Waga.set(value);
    }
    public void setWzrost(Integer value) {
    	Wzrost.set(value);
    }
    public void setPozycja(String value) {
    	Pozycja.set(value);
    }
    public void setDruzyna(String value) {
    	Druzyna.set(value);
    }

    //Property values
    public IntegerProperty IDZawodnikProperty() {
        return IDZawodnik;
    }

    public StringProperty ImieProperty() {
        return Imie;
    }

    public StringProperty NazwiskoDProperty() {
        return Nazwisko;
    }
    public StringProperty DataUrodzeniaDProperty() {
        return DataUrodzenia;
    }
    public IntegerProperty WagaProperty() {
        return Waga;
    }
    public IntegerProperty WzrostProperty() {
        return Wzrost;
    }
    public StringProperty PozycjaProperty() {
        return Pozycja;
    }
    public StringProperty DruzynaProperty() {
        return Druzyna;
    }
}
