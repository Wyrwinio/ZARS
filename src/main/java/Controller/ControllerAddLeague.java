package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.LeagueDetails;

public class ControllerAddLeague {
	private MainController mainController;
	DBconnect DBC = new DBconnect();
	@FXML
	private Button dodajL;

	@FXML
	private Button powrot;

	@FXML
	private TextField ADDNazwaL;

	@FXML
	private TextField ADDObszar;

	@FXML
	private TextField ADDPoziomL;

	@FXML
	private TextField ADDMaxteams;

	@FXML
	private ChoiceBox<Integer> ADDRozp;

	@FXML
	private ChoiceBox<Integer> ADDZak;
	@FXML
	private ChoiceBox<String> CBDyscypliny;
	private ObservableList<String> ADDDyscypliny = FXCollections.observableArrayList();
	private ObservableList<Integer> ADDRozpy = FXCollections.observableArrayList();
	private ObservableList<Integer> ADDZaki = FXCollections.observableArrayList();

	public void initialize() {
		try {

			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select Nazwa from dyscypliny");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));
				ADDDyscypliny.add(rs.getString(1));

			}
			rs = DBC.con.createStatement().executeQuery("select DISTINCT Rok_Zakonczenia from sezony");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));

				ADDZaki.add(rs.getInt(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		CBDyscypliny.setItems(ADDDyscypliny);
		CBDyscypliny.setValue("Pi³ka No¿na");
		try {

			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select DISTINCT Rok_Rozpoczecia from sezony");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));
				ADDRozpy.add(rs.getInt(1));

			}
			rs = DBC.con.createStatement().executeQuery("select DISTINCT Rok_Zakonczenia from sezony");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));

				ADDZaki.add(rs.getInt(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		ADDRozp.setItems(ADDRozpy);
		ADDZak.setItems(ADDZaki);
		ADDRozp.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			System.out.println(newValue);
			ADDZaki.clear();
			try {
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"select DISTINCT Rok_Zakonczenia from sezony WHERE Rok_Rozpoczecia = " + newValue);
				while (rs.next()) {
					// get string from db,whichever way
					ADDZaki.add(rs.getInt(1));
				}
			} catch (SQLException e) {
				System.err.println("Error" + e);
			}

		});
		ADDMaxteams.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					ADDMaxteams.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		ADDPoziomL.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					ADDPoziomL.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	@FXML
	void DodajLige(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz Dodaæ Ligê" + ADDNazwaL.getText() + "?",
				ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();
		Boolean DlugoscNazwyOk = false;
		Boolean ObszarOk = false;
		Boolean LimitDruzynOk = false;
		Boolean SezonWybrany = false;

		if (result.get() == ButtonType.OK) {
			System.out.println(ADDNazwaL.getText());
			System.out.println(ADDObszar.getText());
			System.out.println(ADDPoziomL.getText());
			System.out.println(ADDMaxteams.getText());
			System.out.println(CBDyscypliny.getValue().toString());
			System.out.println(String.valueOf(ADDRozp.getValue()));
			System.out.println(String.valueOf(ADDZak.getValue()));
			Integer poziomrozgrywkowy = null;
			Integer LimitDruzyn = null;

			if (ADDNazwaL.getText().length() < 100 && ADDNazwaL.getText().length() > 5) {
				DlugoscNazwyOk = true;
			} else {
				AlertInformacyjny("B³¹d", "Za krótka lub zbyt d³uga nazwa",
						"Wprowadzona nazwa zajmuje mniej ni¿ 5 lub wiêcej ni¿ 100 znaków");
				DlugoscNazwyOk = false;
			}

			if (ADDObszar.getText().trim().isEmpty() || ADDObszar.getText().length() > 50) {
				AlertInformacyjny("B³¹d", "Brak Obszaru lub zbyt d³ugi",
						"Brak Wpisanego obszaru lub wpisany obszar zawiera powy¿ej 50 znaków");
				ObszarOk = false;
			} else {
				ObszarOk = true;
			}
			if (ADDPoziomL.getText().trim().isEmpty()) {
				poziomrozgrywkowy = null;
			} else {
				poziomrozgrywkowy = Integer.valueOf(ADDPoziomL.getText());
			}
			if (ADDMaxteams.getText().trim().isEmpty()) {
				LimitDruzyn = null;
				LimitDruzynOk = true;
			} else {
				if (Integer.valueOf(ADDMaxteams.getText()) < 3) {
					AlertInformacyjny("B³¹d", "B³êdna wartoœæ", "Minimalna iloœæ dru¿yn wynosi 3");
					LimitDruzynOk = false;
				} else {
					LimitDruzynOk = true;
				}
			}
			if (ADDRozp.getValue() == null || ADDZak.getValue() == null) {
				AlertInformacyjny("B³¹d", "B³¹d", "Nie wybra³eœ Sezonu");
				SezonWybrany = false;
			} else {
				SezonWybrany = true;
			}

			System.out.println(ADDRozp.getValue());
			if (ADDNazwaL.getText().trim().isEmpty() || ADDObszar.getText().trim().isEmpty()
					|| ADDMaxteams.getText().trim().isEmpty()) {
				AlertInformacyjny("B³¹d", "Brak WYmaganych informacji",
						"Wygl¹da na to ¿e nie wpisa³eœ nazwy, obszaru lub limitu druzyn które s¹ wymagane.");
			} else if (DlugoscNazwyOk == true && ObszarOk == true && SezonWybrany == true && LimitDruzynOk == true) {
				try {
					Statement myStmt = DBC.con.createStatement();
					String sql = "INSERT into ligi (NazwaL, Obszar, PoziomL, MaksymalnaLiczbaDruzyn,id_Sezon,IDDyscyplina)"
							+ "VALUES ('" + ADDNazwaL.getText() + "', '" + ADDObszar.getText() + "', "
							+ poziomrozgrywkowy + ", " + "" + ADDMaxteams.getText() + ", (" + "SELECT id_Sezon "
							+ "FROM sezony " + "WHERE sezony.Rok_Rozpoczecia = " + ADDRozp.getValue()
							+ " AND sezony.Rok_Zakonczenia = " + ADDZak.getValue()
							+ "), (Select IDDyscyplina from dyscypliny where Nazwa = '"
							+ CBDyscypliny.getValue().toString() + "'));";
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
								"SELECT ligi.IDLigi,ligi.NazwaL,ligi.obszar,ligi.PoziomL,ligi.MaksymalnaLiczbaDruzyn,s.Rok_Rozpoczecia,s.Rok_Zakonczenia, d.Nazwa FROM ligi left join sezony as s on s.id_Sezon = ligi.id_Sezon join dyscypliny as d on d.IDDyscyplina = ligi.IDDyscyplina");
						while (rs.next()) {
							// get string from db,whichever way
							items.add(new LeagueDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
									rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8)));

						}

					} catch (SQLException ex) {
						System.err.println("Error" + ex);
					}
					Stage stage = (Stage) powrot.getScene().getWindow();
					stage.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}
			} else {
			}
		}
	}

	private ObservableList<LeagueDetails> items;

	public void setItems(ObservableList<LeagueDetails> items) {
		this.items = items;
	}

	@FXML
	void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
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
