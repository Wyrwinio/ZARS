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
import model.TeamDetails;

public class ControllerAddTeam {
	DBconnect DBC = new DBconnect();
	private MainController mainController;
	public UserpanelController upc = new UserpanelController();
	@FXML
	private Button dodaj;

	@FXML
	private TextField ADDDnazwa;

	@FXML
	private TextField ADDDRokzalozenia;

	@FXML
	private TextField ADDDMiasto;

	@FXML
	private Button powrot;

	@FXML
	private ChoiceBox<String> ADDDLiga;

	@FXML
	private ChoiceBox<String> ADDDObiekt;
	private ObservableList<String> LigiADD = FXCollections.observableArrayList();
	private ObservableList<String> ObiektyADD = FXCollections.observableArrayList();

	public void initialize() {
		ADDDRokzalozenia.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					ADDDRokzalozenia.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		try {

			LigiADD = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select NazwaL from ligi");
			while (rs.next()) {
				// get string from db,whichever way
				LigiADD.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		LigiADD.add(null);
		ADDDLiga.setItems(LigiADD);
		try {

			ObiektyADD = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select NazwaS from stadiony");
			while (rs.next()) {
				// get string from db,whichever way
				ObiektyADD.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		ObiektyADD.add(null);
		ADDDObiekt.setItems(ObiektyADD);
	}

	private ObservableList<TeamDetails> items;

	public void setItems(ObservableList<TeamDetails> items) {
		this.items = items;
	}

	@FXML
	public void DodajDruzyne(ActionEvent event) {
		Boolean NazwaOk, Rokok, Ligadostepna = true, dostepnemiejsce = null;
		Integer RokZalozenia = null, iloscdruzynwlidze = null, maksymalnailoscdruzynwlidze = null;

		Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz Dodaæ Druzyne " + ADDDnazwa.getText(),
				ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();
		ResultSet rs;
		if (ADDDLiga.getValue() == null) {
			Ligadostepna = true;
			dostepnemiejsce = true;

		} else {
			try {
				rs = DBC.con.createStatement()
						.executeQuery("Select * from mecze where idligi = (Select IDLigi from ligi where NazwaL = '"
								+ ADDDLiga.getValue().toString() + "')");
				if (rs.next()) {
					Ligadostepna = false;
					upc.AlertInformacyjny("B³¹d", "Liga niedostêpna",
							"Liga do której próbujesz przypisaæ druzynê jest w trakcie lub posiada zatwierdzony terminarz");
				}
				rs = DBC.con.createStatement().executeQuery(
						"Select count(iddruzyny) from druzyny where idligi = (Select IDLigi from ligi where NazwaL = '"
								+ ADDDLiga.getValue().toString() + "')");
				if (rs.next()) {
					iloscdruzynwlidze = rs.getInt(1);
				}
				System.out.println(iloscdruzynwlidze);
				rs = DBC.con.createStatement().executeQuery(
						"Select maksymalnaliczbadruzyn from ligi where idligi = (Select IDLigi from ligi where NazwaL = '"
								+ ADDDLiga.getValue().toString() + "')");
				if (rs.next()) {
					maksymalnailoscdruzynwlidze = rs.getInt(1);
				}
				if (iloscdruzynwlidze < maksymalnailoscdruzynwlidze) {
					dostepnemiejsce = true;
				}

				else {
					dostepnemiejsce = false;
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("B³¹d");
					alert2.setHeaderText("B³¹d");
					alert2.setContentText(
							"Ta liga ma ju¿ maksymaln¹ iloœæ dru¿yn i nie mo¿esz przypisaæ do niej kolejnej.");
					alert2.showAndWait();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (ADDDnazwa.getText().trim().isEmpty() || ADDDnazwa.getText().trim().length() < 3
				|| ADDDnazwa.getText().trim().length() > 100) {
			upc.AlertInformacyjny("B³¹d", "B³¹d", "Za krótka b¹dz za d³uga nazwa");
			NazwaOk = false;
		} else {
			NazwaOk = true;
		}
		if (ADDDRokzalozenia.getText().trim().isEmpty()) {
			RokZalozenia = null;
			Rokok = true;
		} else if (Integer.valueOf(ADDDRokzalozenia.getText()) > 2018
				|| Integer.valueOf(ADDDRokzalozenia.getText()) < 1950) {
			upc.AlertInformacyjny("B³¹d", "B³¹d", "Nieprawid³owy rok");
			Rokok = false;
		} else {
			RokZalozenia = Integer.valueOf(ADDDRokzalozenia.getText());
			Rokok = true;
		}

		if (Rokok == true && NazwaOk == true && Ligadostepna == true && dostepnemiejsce == true) {
			if (result.get() == ButtonType.OK) {

				System.out.println(ADDDnazwa.getText());
				System.out.println(ADDDRokzalozenia.getText());
				System.out.println(ADDDMiasto.getText());
				System.out.println(ADDDObiekt.getValue());
				System.out.println(ADDDLiga.getValue());
				try {
					Statement myStmt = DBC.con.createStatement();
					String sql = "INSERT INTO druzyny (NazwaD,RokZalozenia,MiastoD,IDStadionu,IDLigi) VALUES" + " ('"
							+ ADDDnazwa.getText() + "' , " + RokZalozenia + ", '" + ADDDMiasto.getText() + ""
							+ "', (Select IDStadionu from stadiony where stadiony.NazwaS = '" + ADDDObiekt.getValue()
							+ "'  )," + " (Select IDLigi from ligi Where ligi.NazwaL = '" + ADDDLiga.getValue()
							+ "') );";
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
						rs = DBC.con.createStatement().executeQuery(
								"SELECT druzyny.IDDruzyny, druzyny.NazwaD, druzyny.RokZalozenia, druzyny.MiastoD, s.NazwaS, l.NazwaL from druzyny "
										+ "LEFT JOIN stadiony as s on druzyny.IDStadionu = s.IDStadionu "
										+ "LEFT JOIN ligi as l on druzyny.IDLigi = l.IDLigi");
						if (rs.next()) {
							do {
								items.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
										rs.getString(5), rs.getString(6)));
							} while (rs.next());

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
	public void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

}
