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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.LeagueDetails;

public class ControllerEditLeague {
	private MainController mainController;
	@FXML
	private Button Edytuj;

	@FXML
	private TextField TFENazwaL;

	@FXML
	private TextField TFEObszar;

	@FXML
	private TextField TFEPoziomL;

	@FXML
	private TextField TFEMaxteams;

	@FXML
	private Button powrot;

	@FXML
	private TextField TFESezon;

	@FXML
	private Label id;

	@FXML
	private ChoiceBox<Integer> Rozp;

	@FXML
	private ChoiceBox<Integer> Zak;
	@FXML
	private Label NazwaLigiLBL;
	@FXML
	private ChoiceBox<String> cbDyscyplinyEdit;

	DBconnect DBC = new DBconnect();
	int idligi;
	Boolean edycjamozliwa = false;
	public UserpanelController upc = new UserpanelController();
	private ObservableList<String> Dyscypliny = FXCollections.observableArrayList();
	private ObservableList<Integer> Rozpy = FXCollections.observableArrayList();
	private ObservableList<Integer> Zaki = FXCollections.observableArrayList();

	public void initialize() {
		TFEMaxteams.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFEMaxteams.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		TFEPoziomL.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TFEPoziomL.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		try {

			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select DISTINCT Rok_Rozpoczecia from sezony");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));
				Rozpy.add(rs.getInt(1));

			}
			rs = DBC.con.createStatement().executeQuery("select DISTINCT Rok_Zakonczenia from sezony");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));

				Zaki.add(rs.getInt(1));
			}
			rs = DBC.con.createStatement().executeQuery("select Nazwa from dyscypliny");
			while (rs.next()) {
				// get string from db,whichever way
				// Sezonki.add(rs.getString(1) + ' ' + rs.getString(2));

				Dyscypliny.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		cbDyscyplinyEdit.setItems(Dyscypliny);
		Rozp.setItems(Rozpy);
		Zak.setItems(Zaki);
		Rozp.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			System.out.println(newValue);
			Zaki.clear();
			try {
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"select DISTINCT Rok_Zakonczenia from sezony WHERE Rok_Rozpoczecia = " + newValue);
				while (rs.next()) {
					// get string from db,whichever way
					Zaki.add(rs.getInt(1));
				}
			} catch (SQLException e) {
				System.err.println("Error" + e);
			}

		});

	}

	@FXML
	void EdytujLige(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz Edytowaæ Lige o id " + id.getText() + "?",
				ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();
		Boolean DlugoscNazwyOk = false;
		Boolean ObszarOk = false;
		Boolean SezonWybrany = false;
		Boolean LimitDruzynOk = false;

		if (result.get() == ButtonType.OK) {
			System.out.println(TFENazwaL.getText());
			System.out.println(TFEObszar.getText());
			System.out.println(TFEPoziomL.getText());
			System.out.println(TFEMaxteams.getText());
			Integer poziomrozgrywkowy = null;
			Integer LimitDruzyn = null;
			if (TFENazwaL.getText().length() < 100 && TFENazwaL.getText().length() > 5) {
				DlugoscNazwyOk = true;
			} else {
				AlertInformacyjny("B³¹d", "Za krótka lub zbyt d³uga nazwa",
						"Wprowadzona nazwa zajmuje mniej ni¿ 5 lub wiêcej ni¿ 100 znaków");
				DlugoscNazwyOk = false;
			}

			if (TFEObszar.getText().trim().isEmpty() || TFEObszar.getText().length() > 50) {
				AlertInformacyjny("B³¹d", "Brak Obszaru lub zbyt d³ugi",
						"Brak Wpisanego obszaru lub wpisany obszar zawiera powy¿ej 50 znaków");
				ObszarOk = false;
			} else {
				ObszarOk = true;
			}
			if (TFEPoziomL.getText().trim().isEmpty()) {
				poziomrozgrywkowy = null;
			} else {
				poziomrozgrywkowy = Integer.valueOf(TFEPoziomL.getText());
			}
			if (TFEMaxteams.getText().trim().isEmpty()) {
				LimitDruzyn = null;
				LimitDruzynOk = true;
			} else {
				if (Integer.valueOf(TFEMaxteams.getText()) < 3) {
					LimitDruzynOk = false;
					AlertInformacyjny("B³¹d", "B³êdna wartoœæ", "Minimalna iloœæ dru¿yn wynosi 3");
				} else {
					LimitDruzynOk = true;
					LimitDruzyn = Integer.valueOf(TFEMaxteams.getText());
				}
			}
			if (Rozp.getValue() == null || Zak.getValue() == null) {
				AlertInformacyjny("B³¹d", "B³¹d", "Nie wybra³eœ Sezonu");
				SezonWybrany = false;
			} else {
				SezonWybrany = true;
			}
			if (TFENazwaL.getText().trim().isEmpty() || TFEObszar.getText().trim().isEmpty()
					|| TFEMaxteams.getText().trim().isEmpty()) {
				AlertInformacyjny("B³¹d", "Brak WYmaganych informacji",
						"Wygl¹da na to ¿e nie wpisa³eœ nazwy, obszaru lub limitu druzyn które s¹ wymagane.");
			} else if (DlugoscNazwyOk == true && ObszarOk == true && SezonWybrany == true && LimitDruzynOk == true) {
				try {
					Statement myStmt = DBC.con.createStatement();
					myStmt.executeUpdate("UPDATE ligi SET NazwaL = '" + TFENazwaL.getText() + "'," + " Obszar ='"
							+ TFEObszar.getText() + "', PoziomL ='" + poziomrozgrywkowy + "', MaksymalnaLiczbaDruzyn = "
							+ Integer.valueOf(TFEMaxteams.getText()) + ","
							+ " id_Sezon = (SELECT sezony.id_Sezon FROM sezony WHERE sezony.Rok_Rozpoczecia = '"
							+ Rozp.getValue() + "'" + " AND sezony.Rok_Zakonczenia = '" + Zak.getValue()
							+ "'), IDDyscyplina = (Select IDDyscyplina from dyscypliny where Nazwa = '"
							+ cbDyscyplinyEdit.getValue().toString() + "') WHERE IDLigi = " + id.getText() + ";");
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

	@FXML
	void Nieklikalny(MouseEvent event) {

	}

	@FXML
	void Powrót(ActionEvent event) {
		Stage stage = (Stage) powrot.getScene().getWindow();
		stage.close();
	}

	public void setText(int id, String nazwaL, String Obszar, int PoziomL, int Limit, int RokRozp, int RokZak,
			String Dyscyplina) {
		this.TFENazwaL.setText(nazwaL);
		this.TFEObszar.setText(Obszar);
		this.TFEPoziomL.setText(String.valueOf(PoziomL));
		this.TFEMaxteams.setText(String.valueOf(Limit));
		this.id.setText(String.valueOf(id));
		this.NazwaLigiLBL.setText(nazwaL);
		this.cbDyscyplinyEdit.setValue(Dyscyplina);
		Rozp.setValue(RokRozp);
		Zak.setValue(RokZak);
		idligi = id;
		try {
			ResultSet rs = DBC.con.createStatement().executeQuery("Select * from mecze where idligi = " + idligi + "");
			if (rs.next()) {
				AlertInformacyjny("Informacja", "Rozgrywki w toku",
						"Wybrane rozgrywki ju¿ trwaj¹ lub maj¹ zatwierdzony terminarz. Nie bêdziesz móg³ edytowaæ niektórych opcji.");
				edycjamozliwa = false;
				System.out.println("dasaddsa");
				TFEMaxteams.setEditable(false);
				TFEMaxteams.setOnMouseClicked(e -> {
					AlertInformacyjny("B³¹d", "B³¹d",
							"Liga jest ju¿ w trakcie lub ma zatwierdzony terminarz wiêc nie mo¿na edytowaæ tego atrybutu");
				});
				Rozp.setOnMouseClicked(e -> {
					AlertInformacyjny("B³¹d", "B³¹d",
							"Liga jest ju¿ w trakcie lub ma zatwierdzony terminarz wiêc nie mo¿na edytowaæ tego atrybutu");
				});
				Zak.setOnMouseClicked(e -> {
					AlertInformacyjny("B³¹d", "B³¹d",
							"Liga jest ju¿ w trakcie lub ma zatwierdzony terminarz wiêc nie mo¿na edytowaæ tego atrybutu");
				});
				cbDyscyplinyEdit.setOnMouseClicked(e -> {
					AlertInformacyjny("B³¹d", "B³¹d",
							"Liga jest ju¿ w trakcie lub ma zatwierdzony terminarz wiêc nie mo¿na edytowaæ tego atrybutu");
				});
			} else {
				edycjamozliwa = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.id.setText(String.valueOf(id));
		// idZawodnika = id;
		System.out.println(String.valueOf(RokRozp) + String.valueOf(RokZak));

	}

	private ObservableList<LeagueDetails> items;

	public void setItems(ObservableList<LeagueDetails> items) {
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
