package model;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObjectDetails {
	private final IntegerProperty IDStadionu;
    private final StringProperty NazwaS;
    private final StringProperty MiastoS;
    private final IntegerProperty RokBudowyS;
    private final IntegerProperty PojemnoœæS;
    

    //Default constructor
    public ObjectDetails(Integer IDStadionu, String NazwaS, String MiastoS, Integer RokBudowyS, Integer PojemnoœæS) {
    	this.IDStadionu = new SimpleIntegerProperty(IDStadionu);
        this.NazwaS = new SimpleStringProperty(NazwaS);
        this.MiastoS = new SimpleStringProperty(MiastoS);
        this.RokBudowyS = new SimpleIntegerProperty(RokBudowyS);
        this.PojemnoœæS = new SimpleIntegerProperty(PojemnoœæS);    
            
    }

    //Getters
    public Integer getIDStadionu() {
        return IDStadionu.get();
    }
    public String getNazwaS() {
        return NazwaS.get();
    }

    public String getMiastoS() {
        return MiastoS.get();
    }

    public Integer getRokBudowyS() {
        return RokBudowyS.get();
    }
    public Integer getPojemnoscS() {
        return PojemnoœæS.get();
    }
    

    //Setters
    public void IDStadionu(Integer value) {
    	IDStadionu.set(value);
    }
    public void setNazwaS(String value) {
    	NazwaS.set(value);
    }

    public void setRokBudowyS(Integer value) {
    	RokBudowyS.set(value);
    }

    public void setMiastoS(String value) {
    	MiastoS.set(value);
    }
    public void setPojemnoscS(Integer value) {
    	PojemnoœæS.set(value);
    }
    

    //Property values
    public IntegerProperty IDStadionuProperty() {
        return IDStadionu;
    }
    public StringProperty NazwaSProperty() {
        return NazwaS;
    }

    public IntegerProperty RokBudowySProperty() {
        return RokBudowyS;
    }

    public StringProperty MiastoSProperty() {
        return MiastoS;
    }
    public IntegerProperty PojemnoscProperty() {
        return PojemnoœæS;
    }
   
}
