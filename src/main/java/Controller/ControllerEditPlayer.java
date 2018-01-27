package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PlayerDetails;

public class ControllerEditPlayer {
	private MainController mainController;
	// DBconnect DBC = new DBconnect();
	private int agepars;
	@FXML
	private Button Edytuj;
	@FXML
	private Label id;
	@FXML
	private Button Rejestracja;
	@FXML
	private Button powrot;
	@FXML
	private TextField TFEname;
	@FXML
	private TextField TFEsurname;
	@FXML
	private Label ImieInazwiskoEditlbl;

	@FXML
	private TextField TFEwaga;
	@FXML
	private TextField TFEwzrost;
	@FXML
	private TextField TFEpozycja;
	@FXML
	private TextField TFEDataur;
	@FXML
	private DatePicker DPdataur;
	@FXML
	public ChoiceBox<String> Druzyny;
	@FXML
	DBconnect DBC = new DBconnect();
	public UserpanelController upc = new UserpanelController();
	private ObservableList<String> NazwyDruzyn = FXCollections.observableArrayList();
	public int idZawodnika;

	public void initialize() {
		TFEwaga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFEwaga.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		TFEwzrost.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFEwzrost.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		try {

			NazwyDruzyn = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select NazwaD from druzyny");
			while (rs.next()) {
				// get string from db,whichever way
				NazwyDruzyn.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		Druzyny.setItems(NazwyDruzyn);

		// System.out.print(upc.imie);

		// wybranygraczz =
		// upc.tablePlayers.getSelectionModel().getSelectedItem();
		// System.out.println(wybranygraczz.getImie() +
		// wybranygraczz.getNazwisko());

		// String a = upc.aaateam.getNazwaD();
		// EdycjaLBL.setText(upc.nazwa);
	}

	public void setText(String name, String surname, String dataUrodzenia, int waga, int wzrost, String pozycja, int id,
			String druzyna) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale(Locale.ENGLISH);
		LocalDate date = LocalDate.parse(dataUrodzenia, formatter);
		this.TFEname.setText(name);
		this.TFEsurname.setText(surname);
		this.DPdataur.setValue(date);
		this.TFEwaga.setText(String.valueOf(waga));
		this.TFEwzrost.setText(String.valueOf(wzrost));
		this.TFEpozycja.setText(String.valueOf(pozycja));
		this.ImieInazwiskoEditlbl.setText(name+' ' + surname);
		this.id.setText(String.valueOf(id));
		idZawodnika = id;
		Druzyny.setValue(druzyna);
	}

	public void EdytujZawodnika(ActionEvent event) {
		Boolean wagaok = false;
		Boolean wzrostok = false;
		Boolean ImieInazwiskoOk = false;
		Integer waga = null;
		Integer wzrost = null;
		ControllerAddObject CAO = new ControllerAddObject();
		LocalDate ld = DPdataur.getValue();
		if (TFEname.getText().trim().isEmpty() || TFEsurname.getText().trim().isEmpty()) {
			ImieInazwiskoOk = false;
			CAO.AlertInformacyjny("B³¹d", "Puste pole", "Wygl¹da na to ¿e Pole imienia lub nazwiska jest puste");
		} else {
			ImieInazwiskoOk = true;
		}
		if (TFEwaga.getText().trim().isEmpty()) {
			wagaok = true;
			waga = null;
		} else if (Integer.valueOf(TFEwaga.getText()) < 200 && Integer.valueOf(TFEwaga.getText()) > 30) {
			wagaok = true;
			waga = Integer.valueOf(TFEwaga.getText());
		} else {

			CAO.AlertInformacyjny("B³¹d", "Nieprawid³owe dane", "Nieprawid³owa waga");
			wagaok = false;
		}
		if (TFEwzrost.getText().trim().isEmpty()) {
			wzrost = null;
			wzrostok = true;
		} else if (Integer.valueOf(TFEwzrost.getText()) < 250 && Integer.valueOf(TFEwzrost.getText()) > 70) {

			wzrostok = true;
			wzrost = Integer.valueOf(TFEwzrost.getText());
		} else {
			CAO.AlertInformacyjny("B³¹d", "Nieprawid³owe dane", "Nieprawid³owy wzrost");
			wagaok = false;
		}

		if (wagaok == true && wzrostok == true && ImieInazwiskoOk == true) {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Czy na pewno chcesz Edytowaæ Gracza o id " + idZawodnika + "?", ButtonType.OK, ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				System.out.println(TFEname.getText());
				System.out.println(TFEsurname.getText());
				System.out.println(DPdataur.getValue());
				System.out.println(String.valueOf(TFEwzrost.getText()));
				System.out.println(TFEpozycja.getText());
				try {
					Statement myStmt = DBC.con.createStatement();
					myStmt.executeUpdate("UPDATE Zawodnicy SET imie = '" + TFEname.getText() + "', Nazwisko = '"
							+ TFEsurname.getText() + "', DataUrodzenia = '" + ld + "', waga = " + waga + ", wzrost = "
							+ wzrost + ", " + "pozycja = '" + TFEpozycja.getText()
							+ "', ID_Druzyny = (SELECT druzyny.IDDruzyny" + " FROM druzyny "
							+ "WHERE druzyny.NazwaD = '" + Druzyny.getValue() + "') " + "WHERE IDZawodnik ="
							+ idZawodnika + ";");
					items.clear();
					try {
						// Execute query and store result in a resultset
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"SELECT zawodnicy.IDZawodnik, zawodnicy.Imie, zawodnicy.Nazwisko, zawodnicy.DataUrodzenia, zawodnicy.Waga, zawodnicy.Wzrost, zawodnicy.Pozycja, d.NazwaD from zawodnicy LEFT JOIN druzyny as d on d.IDDruzyny = zawodnicy.ID_Druzyny");
						while (rs.next()) {
							// get string from db,whichever way
							items.add(new PlayerDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
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

	public void Nieklikalny() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Atrybut niezmienialny");
		alert.setHeaderText("");
		alert.setContentText("Ten atrybut nie mo¿e byæ zmieniony");
		alert.showAndWait();
	}

	public void Powrót(ActionEvent event) {

		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();

	}

	private ObservableList<PlayerDetails> items;

	public void setItems(ObservableList<PlayerDetails> items) {
		this.items = items;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
