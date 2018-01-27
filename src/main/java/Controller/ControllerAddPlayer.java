package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PlayerDetails;

public class ControllerAddPlayer {
	private MainController mainController;
	@FXML
	private Button dodaj;

	@FXML
	private TextField ADDname;

	@FXML
	private TextField ADDsurname;

	@FXML
	private TextField ADDwaga;

	@FXML
	private TextField ADDwzrost;

	@FXML
	private Button powrot;

	@FXML
	private TextField ADDpozycja;

	@FXML
	private ChoiceBox<String> ADDdruzyny;

	@FXML
	private DatePicker ADDdataur;
	DBconnect DBC = new DBconnect();
	public UserpanelController upc = new UserpanelController();
	private ObservableList<String> NazwyDruzynADD = FXCollections.observableArrayList();
	public int idZawodnika;

	public void initialize() {

		ADDwaga.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					ADDwaga.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		ADDwzrost.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					ADDwzrost.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		try {

			NazwyDruzynADD = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select NazwaD from druzyny");
			while (rs.next()) {
				// get string from db,whichever way
				NazwyDruzynADD.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		ADDdruzyny.setItems(NazwyDruzynADD);
	}

	@FXML
	void DodajZawodnika(ActionEvent event) {
		Boolean wagaok = false;
		Boolean wzrostok = false;
		Boolean ImieInazwiskoOk = false;
		Integer waga = null;
		Integer wzrost = null;
		ControllerAddObject CAO = new ControllerAddObject();
		if (ADDname.getText().trim().isEmpty() || ADDsurname.getText().trim().isEmpty()) {
			ImieInazwiskoOk = false;
			CAO.AlertInformacyjny("B³¹d", "Puste pole", "Wygl¹da na to ¿e Pole imienia lub nazwiska jest puste");
		} else {
			ImieInazwiskoOk = true;
		}
		if (ADDwaga.getText().trim().isEmpty()) {
			wagaok = true;
			waga = null;
		} else if (Integer.valueOf(ADDwaga.getText()) < 200 && Integer.valueOf(ADDwaga.getText()) > 30) {
			wagaok = true;
			waga = Integer.valueOf(ADDwaga.getText());
		} else {

			CAO.AlertInformacyjny("B³¹d", "Nieprawid³owe dane", "Nieprawid³owa waga");
			wagaok = false;
		}
		if (ADDwzrost.getText().trim().isEmpty()) {
			wzrost = null;
			wzrostok = true;
		} else if (Integer.valueOf(ADDwzrost.getText()) < 250 && Integer.valueOf(ADDwzrost.getText()) > 70) {

			wzrostok = true;
			wzrost = Integer.valueOf(ADDwzrost.getText());
		} else {

			CAO.AlertInformacyjny("B³¹d", "Nieprawid³owe dane", "Nieprawid³owy wzrost");
			wagaok = false;
		}
		if (wagaok == true && wzrostok == true && ImieInazwiskoOk == true) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz Dodaæ Zawodnika " + ADDname.getText()
					+ "Do Dru¿yny " + ADDdruzyny.getValue() + ".", ButtonType.OK, ButtonType.CANCEL);
			alert.setTitle("Date format warning");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {

				System.out.println(ADDname.getText());
				System.out.println(ADDsurname.getText());
				LocalDate ld = ADDdataur.getValue();
				System.out.println(ld);
				System.out.println(String.valueOf(ADDwaga.getText()));
				System.out.println(String.valueOf(ADDwzrost.getText()));
				System.out.println(ADDpozycja.getText());
				try {
					Statement myStmt = DBC.con.createStatement();
					String sql = "INSERT into zawodnicy (Imie, Nazwisko, DataUrodzenia, Waga, Wzrost, Pozycja, ID_Druzyny)"
							+ "VALUES ('" + ADDname.getText() + "', '" + ADDsurname.getText() + "', '" + ld + "', "
							+ waga + ", " + wzrost + ", '" + ADDpozycja.getText() + "', (" + "SELECT IDDruzyny "
							+ "FROM druzyny " + "WHERE druzyny.NazwaD = '" + ADDdruzyny.getValue().toString() + "'"
							+ "));";
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

	private ObservableList<PlayerDetails> items;

	public void setItems(ObservableList<PlayerDetails> items) {
		this.items = items;
	}

	@FXML
	void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
	}

	public void ShowDate(ActionEvent event) {
		LocalDate ld = ADDdataur.getValue();
		System.out.println(ld);
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

}