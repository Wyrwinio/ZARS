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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.TeamDetails;

public class ControllerEditTeam {
	@FXML
	public Label EdycjaLBL;
	@FXML
	private Button Edytuj;
	@FXML
	private Label id;
	@FXML
	private Button Rejestracja;
	@FXML
	private Button powrot;
	@FXML
	private TextField TFEnazwadruzyny;
	@FXML
	private TextField TFEMiasto;
	@FXML
	private Label NazwaDruzynyEditlbl;
	@FXML

	private TextField TFERokZalozenia;
	@FXML
	private TextField Obiekt;
	@FXML
	private ChoiceBox<String> ChoiceObiekt;
	@FXML
	private ChoiceBox<String> ChoiceLiga;
	DBconnect DBC = new DBconnect();
	private MainController mainController;
	private UserpanelController upc = new UserpanelController();
	private ObservableList<String> ListaLig = FXCollections.observableArrayList();
	private ObservableList<String> ListaObiektow = FXCollections.observableArrayList();
	public TeamDetails aaateam;
	public int idDruzyny;

	public void initialize() {
		TFERokZalozenia.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFERokZalozenia.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		try {
			ListaLig = FXCollections.observableArrayList();

			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select NazwaL from ligi");

			while (rs.next()) {
				// get string from db,whichever way
				ListaLig.add(rs.getString(1));

			}
			ListaLig.add(null);

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		try {

			ListaObiektow = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("Select NazwaS from stadiony");
			while (rs.next()) {
				// get string from db,whichever way
				ListaObiektow.add(rs.getString(1));

			}
			ListaObiektow.add(null);

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		ChoiceLiga.setItems(ListaLig);
		ChoiceObiekt.setItems(ListaObiektow);
		// String a = upc.aaateam.getNazwaD();
		// EdycjaLBL.setText(upc.nazwa);
	}

	public void EdytujTeam(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz Edytowaæ dru¿ynê o id " + idDruzyny + "?",
				ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();
		Boolean NazwaOk, Rokok, Ligadostepna = true, dostepnemiejsce = null;
		Integer RokZalozenia = null, iloscdruzynwlidze = null, maksymalnailoscdruzynwlidze = null;

		if (result.get() == ButtonType.OK) {
			System.out.println(TFEnazwadruzyny.getText());
			System.out.println(TFERokZalozenia.getText());
			System.out.println(TFEMiasto.getText());
			System.out.println(ChoiceObiekt.getValue());
			System.out.println(ChoiceLiga.getValue());
			ResultSet rs;
			if (ChoiceLiga.getValue() == null) {
				Ligadostepna = true;
				dostepnemiejsce = true;

			} else {
				try {
					rs = DBC.con.createStatement()
							.executeQuery("Select * from mecze where idligi = (Select IDLigi from ligi where NazwaL = '"
									+ ChoiceLiga.getValue().toString() + "')");
					if (rs.next()) {
						Ligadostepna = false;
						upc.AlertInformacyjny("B³¹d", "Liga niedostêpna",
								"Liga do której próbujesz przypisaæ druzynê jest w trakcie lub posiada zatwierdzony terminarz");
					}
					rs = DBC.con.createStatement().executeQuery(
							"Select count(iddruzyny) from druzyny where idligi = (Select IDLigi from ligi where NazwaL = '"
									+ ChoiceLiga.getValue().toString() + "')");
					if (rs.next()) {
						iloscdruzynwlidze = rs.getInt(1);
					}
					System.out.println(iloscdruzynwlidze);
					rs = DBC.con.createStatement().executeQuery(
							"Select maksymalnaliczbadruzyn from ligi where idligi = (Select IDLigi from ligi where NazwaL = '"
									+ ChoiceLiga.getValue().toString() + "')");
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

			if (TFEnazwadruzyny.getText().trim().isEmpty() || TFEnazwadruzyny.getText().trim().length() < 3
					|| TFEnazwadruzyny.getText().trim().length() > 100) {
				upc.AlertInformacyjny("B³¹d", "B³¹d", "Za krótka b¹dz za d³uga nazwa");
				NazwaOk = false;
			} else {
				NazwaOk = true;
			}
			if (TFERokZalozenia.getText().trim().isEmpty()) {
				RokZalozenia = null;
				Rokok = true;
			} else if (Integer.valueOf(TFERokZalozenia.getText()) > 2018
					|| Integer.valueOf(TFERokZalozenia.getText()) < 1950) {
				upc.AlertInformacyjny("B³¹d", "B³¹d", "Nieprawid³owy rok");
				Rokok = false;
			} else {
				RokZalozenia = Integer.valueOf(TFERokZalozenia.getText());
				Rokok = true;
			}

			if (Rokok == true && NazwaOk == true && Ligadostepna == true && dostepnemiejsce == true) {

				try {
					Statement myStmt = DBC.con.createStatement();
					myStmt.executeUpdate("UPDATE druzyny SET NazwaD = '" + TFEnazwadruzyny.getText()
							+ "', RokZalozenia = " + RokZalozenia + ", " + "MiastoD = '" + TFEMiasto.getText()
							+ "', IDStadionu = (SELECT stadiony.IDStadionu" + " FROM stadiony "
							+ "WHERE stadiony.NazwaS = '" + ChoiceObiekt.getValue() + "') "
							+ ", IDLigi = (SELECT ligi.IDLigi" + " FROM ligi " + "WHERE ligi.NazwaL = '"
							+ ChoiceLiga.getValue() + "') " + "WHERE IDDruzyny =" + idDruzyny + ";");
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

	public void setText(String nazwa, int Rok, String miasto, String obiekt, String Liga, int id) {
		this.TFEnazwadruzyny.setText(nazwa);
		this.TFERokZalozenia.setText(String.valueOf(Rok));
		this.TFEMiasto.setText(miasto);
		ChoiceObiekt.setValue(obiekt);
		ChoiceLiga.setValue(Liga);
		this.NazwaDruzynyEditlbl.setText(nazwa);

		this.id.setText(String.valueOf(id));
		idDruzyny = id;
		// this.id.setText(String.valueOf(id));
		// idZawodnika = id;
		// Druzyny.setValue(druzyna);
	}

	private ObservableList<TeamDetails> items;

	public void setItems(ObservableList<TeamDetails> items) {
		this.items = items;
	}

	public void Powrót(ActionEvent event) {

		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();

	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
