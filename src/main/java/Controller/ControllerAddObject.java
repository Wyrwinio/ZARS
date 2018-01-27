package Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Optional;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ObjectDetails;

public class ControllerAddObject {
	private MainController mainController;
	DBconnect DBC = new DBconnect();
	public int idObiektu;
	@FXML
	private Button dodajS;

	@FXML
	private TextField ADDNazwaS;

	@FXML
	private TextField ADDMiastoS;

	@FXML
	private TextField ADDRokBudowyS;

	@FXML
	private TextField AddPojemnoscS;

	@FXML
	private Button powrot;
	@FXML
	private TextField TFAdres;
	@FXML
	private Button CheckBTN;
	@FXML
	private TextField latTF;

	@FXML
	private TextField lngTF;
	@FXML
    private Label SzerokoscADDlbl;

    @FXML
    private Label DlugoscADDlbl;

	public void initialize() {
		ADDRokBudowyS.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					ADDRokBudowyS.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		AddPojemnoscS.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					AddPojemnoscS.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	@FXML
	public void CheckAdress(ActionEvent event) {
		if (TFAdres.getText().trim() == null || TFAdres.getText().trim().isEmpty()) {
			AlertInformacyjny("B³¹d", "B³¹d", "Pole adresu jest puste");
		}
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyCHidDzIHLw1sxrrWBh4T76o-AKlLp4sNo").build();
		GeocodingResult[] results = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			results = GeocodingApi.geocode(context, TFAdres.getText()).await();
			System.out.println(gson.toJson(results[0].geometry));
			JSONObject jsonObj = new JSONObject(gson.toJson(results[0].geometry));
			Double wspó³rzêdnalat = jsonObj.getJSONObject("location").getDouble("lat");
			Double wspó³rzêdnalon = jsonObj.getJSONObject("location").getDouble("lng");
			latTF.setText(String.valueOf(Math.floor(wspó³rzêdnalat * 1000000) / 1000000));
			lngTF.setText(String.valueOf(Math.floor(wspó³rzêdnalon * 1000000) / 1000000));
			System.out.println(wspó³rzêdnalat);
			System.out.println(wspó³rzêdnalon);
			SzerokoscADDlbl.setVisible(true);
			DlugoscADDlbl.setVisible(true);
			latTF.setVisible(true);
			lngTF.setVisible(true);
		} catch (ApiException e) {
			// TODO Auto-generated catch block

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("B³¹d sprawdzenia adresu");
			alert.setContentText("Wyst¹pi³ b³¹d podczas sprawdzania adresu.");
			TFAdres.clear();
			alert.showAndWait();
		}
	}

	@FXML
	void DodajObiekt(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Czy na pewno chcesz Dodaæ obiekt o nazwie " + ADDNazwaS.getText() + "?", ButtonType.OK,
				ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();
		Boolean DlugoscNazwyOk = false;
		Boolean RokBudowyOk = false;

		if (result.get() == ButtonType.OK) {
			System.out.println(ADDNazwaS.getText());
			System.out.println(ADDMiastoS.getText());
			System.out.println(String.valueOf(ADDRokBudowyS.getText()));
			System.out.println(String.valueOf(AddPojemnoscS.getText()));
			Double latitude, longitude;
			String Miasto;
			Integer Pojemnosc = null, RokBudowy = null;
			Miasto = ADDMiastoS.getText();
			if (ADDNazwaS.getText().length() < 50 && ADDNazwaS.getText().length() > 5) {
				DlugoscNazwyOk = true;
			} else {
				AlertInformacyjny("B³¹d", "Za krótka lub zbyt d³uga nazwa",
						"Wprowadzona nazwa zajmuje mniej ni¿ 5 lub wiêcej ni¿ 50 znaków");
				DlugoscNazwyOk = false;
			}
			if (ADDRokBudowyS.getText().trim().isEmpty()) {
				RokBudowyOk = true;
			} else if (Integer.valueOf(ADDRokBudowyS.getText()) > Calendar.getInstance().get(Calendar.YEAR)
					|| Integer.valueOf(ADDRokBudowyS.getText()) < 1900) {
				AlertInformacyjny("B³¹d", "B³êdny rok", "Wpisany rok jest b³êdny");
				RokBudowyOk = false;
			} else {
				RokBudowy = Integer.valueOf(ADDRokBudowyS.getText());
				RokBudowyOk = true;
			}
			if (AddPojemnoscS.getText().trim().isEmpty()) {
				Pojemnosc = null;
			}
			if (ADDRokBudowyS.getText().trim().isEmpty()) {
				RokBudowy = null;
			}

			try {
				latitude = Double.valueOf(latTF.getText());
				longitude = Double.valueOf(lngTF.getText());
			} catch (NumberFormatException e) {
				latitude = null;
				longitude = null;
			}
			if (ADDNazwaS.getText().trim().isEmpty() || ADDMiastoS.getText().trim().isEmpty()) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("B³¹d");
				alert.setHeaderText("Brak wymaganych informacji");
				alert.setContentText("Wygl¹da na to ¿e nie wpisa³eœ nazwy lub miasta które s¹ wymagane.");
				alert.showAndWait();

			} else if (DlugoscNazwyOk == true && RokBudowyOk == true) {
				try {
					Statement myStmt = DBC.con.createStatement();
					String sql = "INSERT into stadiony (NazwaS, MiastoS, RokBudowyS, PojemnoscS,latitude,longitude)"
							+ "VALUES ('" + ADDNazwaS.getText() + "', '" + ADDMiastoS.getText() + "', " + RokBudowy
							+ ", " + Pojemnosc + "," + latitude + "," + longitude + ");";
					// "INSERT into zawodnicy (Imie,Nazwisko,DataUrodzenia,
					// Waga,
					// Wzrost, Pozycja, ID_Druzyny)"
					// + "VALUES ('Jakub', 'Wyrwa', '1988-04-06', 80, 192,
					// 'Napastnik', ("
					// + " SELECT druzyny.IDDruzyny"
					// + " FROM druzyny "
					// + " WHERE druzyny.NazwaD = 'Amatorska Druzyna 1'"
					// + " ));");
					// + "VALUES ('" + ADDname.getText() + "','"+
					// ADDsurname.getText()+"',"
					// + "'" + ld + ","
					// + String.valueOf(ADDwaga.getText()) +"'," +
					// ADDwzrost.getText() + "','"
					// + ADDpozycja.getText() +"',(SELECT druzyny.IDDruzyny FROM
					// druzyny WHERE druzyny.NazwaD ='Amatorska Druzyna 1'"
					// + " ));";
					myStmt.executeUpdate(sql);
					items.clear();
					try {
						// Execute query and store result in a resultset
						ResultSet rs = DBC.con.createStatement().executeQuery("SELECT * from stadiony");
						while (rs.next()) {
							// get string from db,whichever way
							items.add(new ObjectDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
									rs.getInt(5)));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Brawo");
					alert2.setHeaderText("Uda³o siê");
					alert2.setContentText("Pomyœlnie dodano Obiekt");
					alert2.showAndWait();
					Stage stage = (Stage) powrot.getScene().getWindow();
					stage.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
	}

	private ObservableList<ObjectDetails> items;

	public void setItems(ObservableList<ObjectDetails> items) {
		this.items = items;
	}

	@FXML
	void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
	}

	public void AlertInformacyjny(String title, String HeaderText, String Contenttext) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(HeaderText);
		alert.setContentText(Contenttext);
		alert.showAndWait();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
