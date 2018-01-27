package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserDetails;

public class ControllerAddUser {

	DBconnect DBC = new DBconnect();
	@FXML
	private TextField ADDUImie;
	@FXML
	private TextField ADDUNazwisko;
	@FXML
	private TextField ADDULogin;
	@FXML
	private TextField ADDUEmail;
	@FXML
	private TextField ADDUHaslo;
	@FXML
	private ChoiceBox<String> ChoiceBoxPoziomKonta;
	@FXML
	private Button dodaj;
	@FXML
	private Button powrot;

	UserpanelController upc = new UserpanelController();

	private ObservableList<String> RoleUzytkownikowADD = FXCollections.observableArrayList();

	public void initialize() {
		RoleUzytkownikowADD.add("Administrator");
		RoleUzytkownikowADD.add("Uzytkownik");
		ChoiceBoxPoziomKonta.setItems(RoleUzytkownikowADD);
	}

	@FXML
	void DodajUzytkownika(ActionEvent event) {
		{
			Boolean LoginOK = false, HasloOK = false, emailOK = false;
			String imie = null, nazwisko = null;
			Integer poziomKonta;
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Czy na pewno chcesz Dodaæ U¿ytkownika o id " + ADDULogin.getText().toString() + "?", ButtonType.OK,
					ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				System.out.println(ADDUImie.getText());
				System.out.println(ADDUNazwisko.getText());
				System.out.println(ADDULogin.getText());
				System.out.println(ADDUEmail.getText());
				System.out.println(ADDUHaslo.getText());
				ResultSet rs;
				try {
					rs = DBC.con.createStatement()
							.executeQuery("Select * from uzytkownicy where username = '" + ADDULogin.getText() + "'");
					if (rs.next()) {

						LoginOK = false;
						upc.AlertInformacyjny("Alert", "Login Zajety",
								"Wybrany login ju¿ istnieje w naszej bazie. Wybierz inny");
					} else {
						LoginOK = true;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (ADDULogin.getText().trim().length() < 6 || ADDULogin.getText().trim().isEmpty()) {
					LoginOK = false;
					upc.AlertInformacyjny("Alert", "Login Za krótki",
							"Wybrany login Jest za krótki (Minimum 6 znaków)");
				}
				if (ADDUImie.getText().trim().isEmpty()) {
					imie = null;
				} else {
					imie = "'" + ADDUImie.getText().toString() + "'";
				}
				if (ADDUNazwisko.getText().trim().isEmpty()) {
					nazwisko = null;
				} else {
					nazwisko = "'" + ADDUImie.getText().toString() + "'";
				}

				try {
					rs = DBC.con.createStatement()
							.executeQuery("Select * from uzytkownicy where email = '" + ADDUEmail.getText() + "'");

					if (rs.next()) {
						upc.AlertInformacyjny("B³¹d", "email zajêty",
								"wpisany email jest ju¿ przypisany do innego konta");
						emailOK = false;
					} else {
						emailOK = true;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				LoginOK = true;
			}
			if (ADDUHaslo.getText().trim().length() < 6 || ADDUHaslo.getText().trim().isEmpty()) {
				upc.AlertInformacyjny("B³¹d", "Has³o za krótkie", "Minimalna d³ugoœæ has³a to 6 znaków");
				HasloOK = false;
			} else {
				HasloOK = true;
			}
			if (ChoiceBoxPoziomKonta.getValue().toString() == "Administrator") {
				poziomKonta = 2;
			} else {
				poziomKonta = 1;
			}
			if (LoginOK == true && HasloOK == true && emailOK == true) {
				try {
					Statement myStmt = DBC.con.createStatement();
					myStmt.executeUpdate("INSERT into uzytkownicy (name,surname,username,email,password,poziomkonta) "
							+ "VALUES (" + imie + "," + nazwisko + ",'"
							+ ADDULogin.getText().toString() + "','" + ADDUEmail.getText().toString() + "','"
							+ ADDUHaslo.getText().toString() + "'," + poziomKonta + ")");
					items.clear();
					try {
						// Execute query and store result in a resultset
						ResultSet rs = DBC.con.createStatement().executeQuery("SELECT * from uzytkownicy");
						while (rs.next()) {
							String poziom = rs.getString(7);
							if (poziom.equals("1")) {
								poziom = "Uzytkownik";
								// System.out.println(poziom);
								items.add(new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5), rs.getString(6), poziom));
							} else {
								poziom = "Administrator";
								// System.out.println(poziom);
								items.add(new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5), rs.getString(6), poziom));
							}
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

	private ObservableList<UserDetails> items;

	public void setItems(ObservableList<UserDetails> items) {
		this.items = items;
	}

	@FXML
	void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
	}
}
