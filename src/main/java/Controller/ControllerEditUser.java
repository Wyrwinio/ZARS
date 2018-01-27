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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserDetails;

public class ControllerEditUser {

	private MainController mainController;
	@FXML
	private Button Edytuj;

	@FXML
	private TextField TFEnameUser;

	@FXML
	private TextField TFEsurnameUser;

	@FXML
	private TextField TFEusername;
	DBconnect DBC = new DBconnect();
	@FXML
	private TextField TFEemailUser;

	@FXML
	private TextField TFEpasswordUser;

	@FXML
	private Button powrot;

	@FXML
	private Label idUser;

	@FXML
	private ChoiceBox<String> ChoicePoziomKonta;
	public int idUzytkownika;
	UserpanelController upc = new UserpanelController();
	private ObservableList<String> RoleUzytkownikow = FXCollections.observableArrayList();

	public void initialize() {
		RoleUzytkownikow.add("Administrator");
		RoleUzytkownikow.add("Uzytkownik");
		ChoicePoziomKonta.setItems(RoleUzytkownikow);
	}

	private ObservableList<UserDetails> items;

	public void setItems(ObservableList<UserDetails> items) {
		this.items = items;
	}

	@FXML
	void EdytujUzytkownika(ActionEvent event) {
		{
			Boolean LoginOK = false, HasloOK = false, emailOK = false;
			String imie = null, nazwisko = null;
			Integer poziomKonta;
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Czy na pewno chcesz Edytowaæ U¿ytkownika o id " + idUzytkownika + "?", ButtonType.OK,
					ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				System.out.println(TFEnameUser.getText());
				System.out.println(TFEsurnameUser.getText());
				System.out.println(TFEusername.getText());
				System.out.println(TFEemailUser.getText());
				System.out.println(TFEpasswordUser.getText());
				ResultSet rs;

				try {
					rs = DBC.con.createStatement().executeQuery("Select * from uzytkownicy where username = '"
							+ TFEusername.getText() + "' and id !=" + Integer.valueOf(idUser.getText()) + "");
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

				if (TFEusername.getText().trim().length() < 6 || TFEusername.getText().trim().isEmpty()) {
					LoginOK = false;
					upc.AlertInformacyjny("Alert", "Login Za krótki",
							"Wybrany login Jest za krótki (Minimum 6 znaków)");
				}

				try {
					rs = DBC.con.createStatement().executeQuery("Select * from uzytkownicy where email = '"
							+ TFEemailUser.getText() + "'and id !=" + Integer.valueOf(idUser.getText()) + "");

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
			if (TFEpasswordUser.getText().trim().length() < 6 || TFEpasswordUser.getText().trim().isEmpty()) {
				upc.AlertInformacyjny("B³¹d", "Has³o za krótkie", "Minimalna d³ugoœæ has³a to 6 znaków");
				HasloOK = false;
			} else {
				HasloOK = true;
			}
			if (ChoicePoziomKonta.getValue().toString() == "Administrator") {
				poziomKonta = 2;
			} else {
				poziomKonta = 1;
			}
			if (TFEnameUser.getText().trim().isEmpty()) {
				imie = null;
			} else {
				imie = "'" + TFEnameUser.getText().toString() + "'";
			}
			if (TFEsurnameUser.getText().trim().isEmpty()) {
				nazwisko = null;
			} else {
				nazwisko = "'" + TFEnameUser.getText().toString() + "'";
			}

			if (LoginOK == true && HasloOK == true && emailOK == true) {
				try {
					Statement myStmt = DBC.con.createStatement();
					myStmt.executeUpdate("UPDATE uzytkownicy SET name = " + imie + ", surname = " + nazwisko + ", "
							+ "username = '" + TFEusername.getText() + "', email = '" + TFEemailUser.getText() + "', "
							+ "password = '" + TFEpasswordUser.getText() + "', poziomkonta = " + poziomKonta
							+ " WHERE id =" + idUzytkownika + ";");
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

	public void setText(int id, String name, String surname, String login, String email, String Password,
			String poziom) {
		if (name == null || name.equals("")) {
			this.TFEnameUser.setText("");
		} else {
			this.TFEnameUser.setText(name);
		}

		if (surname == null || surname.equals("")) {
			this.TFEsurnameUser.setText("");
		} else {
			this.TFEsurnameUser.setText(name);
		}
		this.TFEusername.setText(login);
		this.TFEemailUser.setText(email);
		this.TFEpasswordUser.setText(Password);
		this.idUser.setText(String.valueOf(id));
		idUzytkownika = id;
		ChoicePoziomKonta.setValue(poziom);
	}

	@FXML
	void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

}
