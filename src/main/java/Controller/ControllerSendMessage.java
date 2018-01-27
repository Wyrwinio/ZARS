package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.OutgoingMessageDetails;
import model.PlayerDetails;
import model.TeamDetails;

public class ControllerSendMessage {

	DBconnect DBC = new DBconnect();
	private MainController mainController;
	@FXML
	private ChoiceBox<String> ChoiceAdresaci;

	@FXML
	private TextArea TAtresc;

	@FXML
	private Button Wyslij;

	int idadmina;
	int iduzytkownika;
	int idzgloszenia;
	boolean ZwrotNaZgloszenieDruzyny;
	boolean ZwrotNaZgloszenieZawodnika;
	private ObservableList<String> ListaUzytkownikow = FXCollections.observableArrayList();

	public void setidadministrator(int id) {
		idadmina = id;
	}

	public void setidzgloszenia(int id) {
		idzgloszenia = id;
	}

	public void initialize() {
		System.out.println(idadmina);
		ListaUzytkownikow = FXCollections.observableArrayList();

		try {
			ResultSet rs = DBC.con.createStatement()
					.executeQuery("Select username from uzytkownicy where poziomkonta = 1 Order by username asc");
			while (rs.next()) {
				// get string from db,whichever way
				ListaUzytkownikow.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ChoiceAdresaci.setItems(null);
		ChoiceAdresaci.setItems(ListaUzytkownikow);
		ChoiceAdresaci.setValue(ListaUzytkownikow.get(0));

	}

	public void WiadomoscZwrotnanazgloszenieDruzyny(String nazwauzytkownika) {
		initialize();
		ZwrotNaZgloszenieDruzyny = true;
		ChoiceAdresaci.setValue(nazwauzytkownika);
		ChoiceAdresaci.setDisable(true);

	}

	public void WiadomoscZwrotnanazgloszenieZawodnika(String nazwauzytkownika) {
		initialize();
		ZwrotNaZgloszenieZawodnika = true;
		ChoiceAdresaci.setValue(nazwauzytkownika);
		ChoiceAdresaci.setDisable(true);

	}

	private ObservableList<OutgoingMessageDetails> items;

	public void setItems(ObservableList<OutgoingMessageDetails> items) {
		this.items = items;
	}

	private ObservableList<TeamDetails> items2;

	public void setItems2(ObservableList<TeamDetails> items2) {
		this.items2 = items2;
	}

	private ObservableList<PlayerDetails> items3;

	public void setItems3(ObservableList<PlayerDetails> items3) {
		this.items3 = items3;
	}

	public void wyslijwiadomosc(ActionEvent event) {
		System.out.println(idadmina);
		System.out.println(idzgloszenia);
		if (TAtresc.getText().isEmpty()) {
			UserpanelController UPC = new UserpanelController();
			UPC.AlertInformacyjny("B³¹d", "Pusta wiadomoœæ", "Nie mo¿esz wys³aæ pustej wiadomoœci");

		} else {
			if (ZwrotNaZgloszenieDruzyny == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"Czy na pewno chcesz odrzuciæ zg³oszenie i wys³aæ wiadomoœæ? ", ButtonType.OK,
						ButtonType.CANCEL);
				alert.setTitle("Potwierdzenie");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					try {

						DBC.st.executeUpdate(
								"DELETE FROM zgloszenia_druzyn where idZgloszeniaDruzyny = " + idzgloszenia + " ");
						items2.clear();
						DBC.con.createStatement()
								.executeUpdate("Insert into wiadomosci_wychodzace (id_autora,id_adresata,Tresc) "
										+ "VALUES (" + idadmina + ",(Select id from uzytkownicy where username = '"
										+ ChoiceAdresaci.getValue() + "'),'" + TAtresc.getText() + "')");
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"SELECT zd.idZgloszeniaDruzyny,zd.NazwaDruzyny,zd.RokZalozenia,zd.Miasto,s.NazwaS,l.NazwaL FROM "
										+ "zgloszenia_druzyn zd join stadiony as s on s.IDStadionu = zd.Obiekt join ligi as l on l.IDLigi = zd.Liga");
						while (rs.next()) {
							// get string from db,whichever way
							items2.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
									rs.getString(5), rs.getString(6)));

						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Stage stage = (Stage) Wyslij.getScene().getWindow();
				stage.close();
			} else if (ZwrotNaZgloszenieZawodnika == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"Czy na pewno chcesz odrzuciæ zg³oszenie i wys³aæ wiadomoœæ? ", ButtonType.OK,
						ButtonType.CANCEL);
				alert.setTitle("Potwierdzenie");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					try {

						DBC.st.executeUpdate("DELETE FROM zgloszenia_zawodnikow where idZgloszeniaZawodnika = "
								+ idzgloszenia + " ");
						items3.clear();
						DBC.con.createStatement()
								.executeUpdate("Insert into wiadomosci_wychodzace (id_autora,id_adresata,Tresc) "
										+ "VALUES (" + idadmina + ",(Select id from uzytkownicy where username = '"
										+ ChoiceAdresaci.getValue() + "'),'" + TAtresc.getText() + "')");
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"Select zz.idZgloszeniaZawodnika, zz.Imie, zz.Nazwisko, zz.DataUrodzenia, "
										+ "zz.Waga,zz.Wzrost,zz.Pozycja, d.NazwaD from zgloszenia_zawodnikow as zz join druzyny as d on d.IDDruzyny = zz.Druzyna");
						while (rs.next()) {
							// get string from db,whichever way
							items3.add(new PlayerDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
									rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));

						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				Stage stage = (Stage) Wyslij.getScene().getWindow();
				stage.close();
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz Wys³aæ wiadomoœæ? ", ButtonType.OK,
						ButtonType.CANCEL);
				alert.setTitle("Potwierdzenie");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					try {
						DBC.con.createStatement()
								.executeUpdate("Insert into wiadomosci_wychodzace (id_autora,id_adresata,Tresc) "
										+ "VALUES (" + idadmina + ",(Select id from uzytkownicy where username = '"
										+ ChoiceAdresaci.getValue() + "'),'" + TAtresc.getText() + "')");

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					items.clear();

					try {
						// Execute query and store result in a resultset
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"Select  ww.id, u.username, u2.username, ww.Tresc from wiadomosci_wychodzace ww JOIN "
										+ "uzytkownicy u on u.id=ww.id_autora join uzytkownicy u2 on u2.id = ww.id_adresata");
						while (rs.next()) {
							// get string from db,whichever way
							items.add(new OutgoingMessageDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
									rs.getString(4)));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Stage stage = (Stage) Wyslij.getScene().getWindow();
					stage.close();
				}
			}
		}
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
