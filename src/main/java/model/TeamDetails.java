package model;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeamDetails {
	private final IntegerProperty IDDruzyny;
    private final StringProperty NazwaD;
    private final IntegerProperty RokZalozenia;
    private final StringProperty MiastoD;
    private final StringProperty IDStadionu;
    private final StringProperty Liga;

    //Default constructor
    public TeamDetails(Integer IDDruzyny, String NazwaD, Integer RokZalozenia, String MiastoD, String IDStadionu, String Liga) {
    	this.IDDruzyny = new SimpleIntegerProperty(IDDruzyny);
        this.NazwaD = new SimpleStringProperty(NazwaD);
        this.RokZalozenia = new SimpleIntegerProperty(RokZalozenia);
        this.MiastoD = new SimpleStringProperty(MiastoD);    
        this.IDStadionu = new SimpleStringProperty(IDStadionu);
        this.Liga = new SimpleStringProperty(Liga);        
    }

    //Getters
    public Integer getIDDruzyny() {
        return IDDruzyny.get();
    }
    public String getNazwaD() {
        return NazwaD.get();
    }

    public Integer getRokZalozenia() {
        return RokZalozenia.get();
    }

    public String getMiastoD() {
        return MiastoD.get();
    }
    public String getIDStadionu() {
        return IDStadionu.get();
    }
    public String getLiga() {
        return Liga.get();
    }

    //Setters
    public void setIDDruzyny(Integer value) {
    	IDDruzyny.set(value);
    }
    public void setNazwaD(String value) {
    	NazwaD.set(value);
    }

    public void setRokZalozenia(Integer value) {
    	RokZalozenia.set(value);
    }

    public void setMiastoD(String value) {
    	MiastoD.set(value);
    }
    public void setIDStadionu(String value) {
    	IDStadionu.set(value);
    }
    public void setLiga(String value) {
    	Liga.set(value);
    }

    //Property values
    public IntegerProperty IDDruzynyProperty() {
        return IDDruzyny;
    }
    public StringProperty NazwaDProperty() {
        return NazwaD;
    }

    public IntegerProperty RokZalozeniaProperty() {
        return RokZalozenia;
    }

    public StringProperty MiastoDProperty() {
        return MiastoD;
    }
    public StringProperty IDStadionuProperty() {
        return IDStadionu;
    }
    public StringProperty LigaProperty() {
        return Liga;
    }
}
