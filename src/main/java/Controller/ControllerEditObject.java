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

public class ControllerEditObject {
	private MainController mainController;
	@FXML
	private Label EdycjaLBL;

	@FXML
	private Label idObiektu;

	@FXML
	private TextField TFENazwaS;

	@FXML
	private TextField TFEMiastoS;

	@FXML
	private TextField TFERokBudowyS;

	@FXML
	private TextField TFEPojemnosc;

	@FXML
	private Button EdytujObiekt;

	@FXML
	private Button powrot;

	@FXML
	private TextField TFEditLatitude;

	@FXML
	private TextField TFEditLongitude;

	@FXML
	private Button EditLocBTN;

	@FXML
	private TextField TFNewAddress;
	@FXML
    private Label NazwaObiektu;

	@FXML
	private Button BTNCheckNewAddress;
    
	public int idobiektuobiektu;

	@FXML
	DBconnect DBC = new DBconnect();

	public void initialize() {
		TFERokBudowyS.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFERokBudowyS.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		TFEPojemnosc.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFEPojemnosc.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	@FXML
	public void EdytujObject(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Czy na pewno chcesz Edytowaæ obiekt o id " + idobiektuobiektu + "?", ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();
		Boolean DlugoscNazwyOk = false;
		Boolean RokBudowyOk = false;
		if (result.get() == ButtonType.OK) {
			System.out.println(TFENazwaS.getText());
			System.out.println(TFEMiastoS.getText());
			System.out.println(String.valueOf(TFEPojemnosc.getText()));
			System.out.println(String.valueOf(TFERokBudowyS.getText()));
			Double latitude, longitude;
			String Miasto;
			Integer Pojemnosc = null, RokBudowy = null;

			// Integer pojemnosc, RokBudowy;
			Miasto = TFEMiastoS.getText();
			if (TFENazwaS.getText().length() < 50 && TFENazwaS.getText().length() > 5) {
				DlugoscNazwyOk = true;
			} else {
				AlertInformacyjny("B³¹d", "Za krótka lub zbyt d³uga nazwa",
						"Wprowadzona nazwa zajmuje mniej ni¿ 5 lub wiêcej ni¿ 50 znaków");
				DlugoscNazwyOk = false;
			}
			if (TFERokBudowyS.getText().trim().isEmpty()) {
				RokBudowyOk = true;
			} else if (Integer.valueOf(TFERokBudowyS.getText()) > Calendar.getInstance().get(Calendar.YEAR)
					|| Integer.valueOf(TFERokBudowyS.getText()) < 1900) {
				AlertInformacyjny("B³¹d", "B³êdny rok", "Wpisany rok jest b³êdny");
				RokBudowyOk = false;
			} else {
				RokBudowy = Integer.valueOf(TFERokBudowyS.getText());
				RokBudowyOk = true;
			}
			if (TFEPojemnosc.getText().trim().isEmpty()) {
				Pojemnosc = null;
			}
			if (TFERokBudowyS.getText().trim().isEmpty()) {
				RokBudowy = null;
			}
			try {
				latitude = Double.valueOf(TFEditLatitude.getText());
				longitude = Double.valueOf(TFEditLongitude.getText());
			} catch (NumberFormatException e) {
				latitude = null;
				longitude = null;
			}
			if (TFENazwaS.getText().trim().isEmpty() || TFEMiastoS.getText().trim().isEmpty()) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("B³¹d");
				alert.setHeaderText("Brak wymaganych informacji");
				alert.setContentText("Wygl¹da na to ¿e nie wpisa³eœ nazwy lub miasta które s¹ wymagane.");
				alert.showAndWait();

			} else if (DlugoscNazwyOk == true && RokBudowyOk == true) {
				// pojemnosc = Integer.valueOf(TFEPojemnosc.getText());
				try {
					Statement myStmt = DBC.con.createStatement();
					myStmt.executeUpdate("UPDATE stadiony SET NazwaS = '" + TFENazwaS.getText() + "', MiastoS = '"
							+ Miasto + "', RokBudowyS = " + RokBudowy + ", PojemnoscS = " + Pojemnosc + ", latitude = "
							+ latitude + ", longitude = " + longitude + " WHERE IDStadionu = " + idobiektuobiektu
							+ ";");
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
					Stage stage = (Stage) powrot.getScene().getWindow();
					stage.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void CheckAddress(ActionEvent event) {

		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyCHidDzIHLw1sxrrWBh4T76o-AKlLp4sNo").build();
		GeocodingResult[] results = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			results = GeocodingApi.geocode(context, TFNewAddress.getText()).await();
			System.out.println(gson.toJson(results[0].geometry));
			JSONObject jsonObj = new JSONObject(gson.toJson(results[0].geometry));
			Double wspó³rzêdnalat = jsonObj.getJSONObject("location").getDouble("lat");
			Double wspó³rzêdnalon = jsonObj.getJSONObject("location").getDouble("lng");
			TFEditLatitude.setText(String.valueOf(Math.floor(wspó³rzêdnalat * 1000000) / 1000000));
			TFEditLongitude.setText(String.valueOf(Math.floor(wspó³rzêdnalon * 1000000) / 1000000));
			System.out.println(wspó³rzêdnalat);
			System.out.println(wspó³rzêdnalon);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Nie mo¿na wyszukaæ adresu");
			alert.setContentText("Wyst¹pi³ b³¹d podczas sprawdzania poprawnoœci adresu");
			alert.showAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Interrupted");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("B³¹d");
		}
	}

	public void EdytujLokalizacje(ActionEvent event) {
		EditLocBTN.setVisible(false);
		TFNewAddress.setVisible(true);
		BTNCheckNewAddress.setVisible(true);
	}

	@FXML
	public void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();

	}

	public void setText(int id, String nazwa, String Miasto, int Rok, int Pojemnosc) {
		this.TFENazwaS.setText(nazwa);
		this.TFEMiastoS.setText(Miasto);
		this.TFERokBudowyS.setText(String.valueOf(Rok));
		this.TFEPojemnosc.setText(String.valueOf(Pojemnosc));
		this.idObiektu.setText(String.valueOf(id));
		this.NazwaObiektu.setText(nazwa);
		idobiektuobiektu = id;
		try {
			ResultSet rs = DBC.con.createStatement()
					.executeQuery("Select latitude, longitude from stadiony where IDStadionu = " + id);
			if (rs.next()) {
				TFEditLatitude.setText(String.valueOf(rs.getDouble(1)));
				TFEditLongitude.setText(String.valueOf(rs.getDouble(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.id.setText(String.valueOf(id));
		// idZawodnika = id;
		// Druzyny.setValue(druzyna);
	}

	private ObservableList<ObjectDetails> items;

	public void setItems(ObservableList<ObjectDetails> items) {
		this.items = items;
	}

	private void AlertInformacyjny(String title, String HeaderText, String Contenttext) {
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
