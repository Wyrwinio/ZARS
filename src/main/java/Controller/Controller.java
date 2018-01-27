package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserDetails;

public class Controller {
	private MainController mainController;

	DBconnect DBC = new DBconnect();

	@FXML
	private Button button;
	@FXML
	public TextField login;
	@FXML
	public PasswordField haslo;
	@FXML
	public TextArea output;
	@FXML
	public Label status;
	@FXML
	private Button test;
	@FXML
	private Button RejestracjaButton;
	@FXML
	public String loginek;
	public ObservableList<UserDetails> data;
	public Stage primaryStage;

	public void loguj(ActionEvent event) {
		try {

			data = FXCollections.observableArrayList();
			String query = "select * from uzytkownicy where username='" + login.getText() + "' and password='"
					+ haslo.getText() + "' and poziomkonta=2";
			// String query2 = "SELECT * FROM `employee_info` JOIN employee_data
			// ON employee_info.id=employee_data.id WHERE employee_data.id=5";
			DBC.rs = DBC.st.executeQuery(query);
			System.out.println("Records from database");
			loginek = login.getText();

			if (DBC.rs.next()) {
				((Node) event.getSource()).getScene().getWindow().hide();
				primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/Userpanel.fxml").openStream());
				UserpanelController upc = (UserpanelController) loader.getController();
				upc.GetUser(login.getText());
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();

				int id = DBC.rs.getInt("id");
				upc.przekazanie = id;
				// String name = rs.getString("name");
				// String surname = rs.getString("surname");
				// int age = rs.getInt("age");
				String username = DBC.rs.getString("username");
				String password = DBC.rs.getString("password");

				upc.GetId(id);
				String query2 = "SELECT * FROM uzytkownicy";
				// String query2 = "SELECT employee_data.id, employee_info.adres
				// FROM `employee_info` JOIN `employee_data` ON
				// employee_info.id=employee_data.id WHERE"
				// + " employee_data.id="+id+"";
				DBC.rs = DBC.st.executeQuery(query2);
				// DBC.rs = DBC.con.createStatement().executeQuery(query2);
				// if (!DBC.rs.isBeforeFirst() ) {
				// upc.GetAddress("Niestety nie masz przypisanych ¿adnych
				// adresów");
				// }
				// else{

				// while(DBC.rs.next())
				// {

				String adres = DBC.rs.getString("adres");
				System.out.println(adres);
				upc.GetAddress(adres);
				// data.add(new UserDetails(DBC.rs.getString(2),
				// DBC.rs.getString(3), DBC.rs.getString(4),
				// DBC.rs.getString(5)));
				/*
				 * upc.columnName.setCellValueFactory(new
				 * PropertyValueFactory<>("name"));
				 * upc.columnSurname.setCellValueFactory(new
				 * PropertyValueFactory<>("email"));
				 * upc.columnAge.setCellValueFactory(new
				 * PropertyValueFactory<>("age")); upc.tableUser.setItems(data);
				 */

				// ObservableList<String> row =
				// FXCollections.observableArrayList();
				/*
				 * for(int i=1 ; i<=DBC.rs.getMetaData().getColumnCount(); i++){
				 * //Iterate Column row.add(DBC.rs.getString(2)); }
				 * System.out.println("Row [1] added "+row ); data.add(row);
				 */

				// }

				// upc.ColumnAdres.setCellValueFactory(new
				// PropertyValueFactory<>("name"));
				// upc.ColumnID.setCellValueFactory(new
				// PropertyValueFactory<>("email"));
				// upc.Columnusername.setCellValueFactory(new
				// PropertyValueFactory<>("username"));
				// upc.tableUser.setItems(null);

				// }

				/*
				 * while(DBC.rs.next()) {
				 * 
				 * if (DBC.rs.wasNull()) { upc.
				 * GetAddress("Niestety nie masz przypisanych ¿adnych adresów");
				 * } else { String adres = DBC.rs.getString("adres");
				 * System.out.println(adres); upc.GetAddress(adres); }
				 * 
				 * 
				 * 
				 * 
				 * ObservableList<String> row =
				 * FXCollections.observableArrayList(); for(int i=1 ;
				 * i<=DBC.rs.getMetaData().getColumnCount(); i++){ //Iterate
				 * Column row.add(DBC.rs.getString(i)); }
				 * System.out.println("Row [1] added "+row ); data.add(row);
				 * 
				 * }
				 */

				/*
				 * if(DBC.rs.next())
				 * 
				 * {
				 * 
				 * String adres = DBC.rs.getString("adres");
				 * System.out.println(adres); upc.GetAddress(adres);
				 * 
				 * 
				 * 
				 * } else { upc.
				 * GetAddress("Niestety nie masz przypisanych ¿adnych adresów");
				 * }
				 */

				/*
				 * while(DBC.rs.next()){ //Iterate Row ObservableList<String>
				 * row = FXCollections.observableArrayList(); for(int i=1 ;
				 * i<=DBC.rs.getMetaData().getColumnCount(); i++){ //Iterate
				 * Column row.add(DBC.rs.getString(i)); }
				 * System.out.println("Row [1] added "+row ); data.add(row);
				 * 
				 * }
				 */

				System.out.print(id);
				output.appendText(" " + "username  " + username + "  " + password + "  " + "password");
				status.setText("Zalogowany");
				System.out.println();
				DBC.kontoistnieje = true;
				// openUserpanel();

			} else {

				// output.setText("Niestety takie konto nie istnieje");
				status.setText("NieZalogowany");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Niepoprawne");
				alert.setHeaderText(null);
				alert.setContentText("Taki administrator nie istnieje");

				alert.showAndWait();

			}

		} catch (Exception ex) {

		}

		// if (output.getText().isEmpty()){
		// output.appendText("Login to :" +login.getText() + "\n");

		// output.appendText("Has³o to :" +haslo.getText());
		// DBC.zaloguj(output, login.getText(), haslo.getText(),status);

	}
	// else {

	// output.setText("");
	// DBC.zaloguj(output, login.getText(), haslo.getText(),status);
	// }
	// }

	@FXML
	public void openUserpanel() {
		/*
		 * FXMLLoader loader = new FXMLLoader
		 * (this.getClass().getResource("Userpanel.fxml")); Pane pane = null;
		 * 
		 * try { pane = loader.load(); } catch (IOException e) {
		 * e.printStackTrace(); } UserpanelController userpan=
		 * loader.getController(); userpan.setMainController(mainController);
		 * mainController.setScreen(pane);
		 */

	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
