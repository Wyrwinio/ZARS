package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import model.GoalScorersDetails;
import model.ScheduleDetails;
import model.TeamDetails;
import model.TeamStatDetails;

public class ControllerLeaguePanel {

	DBconnect DBC = new DBconnect();
	private MainController mainController;

	@FXML
	private Button odswiez;
	@FXML
	private TableView<TeamDetails> SpisDruzyn;

	@FXML
	private TableColumn<TeamDetails, String> SpisNazwa;

	@FXML
	private Button GenerujTerminarz;
	@FXML
	private Label idligi;

	@FXML
	public TableView<ScheduleDetails> Kolejki;
	@FXML
	private Button ZapiszTerminarzBTN;
	@FXML
	private Button refresh;
	@FXML
	private Label NazwaLigiLBL;

	@FXML
	private TableColumn<ScheduleDetails, ?> GodzinaColumn;
	@FXML
	private TableColumn<ScheduleDetails, String> Druzyna1Column;
	@FXML
	private TableColumn<ScheduleDetails, Integer> GoleGospColumn;
	@FXML
	private TableColumn<ScheduleDetails, String> Druzyna2Column;
	@FXML
	private TableColumn<ScheduleDetails, Integer> GoleGoscColumn;
	@FXML
	private TableColumn<ScheduleDetails, Integer> numerkolColumn;
	@FXML
	private TableColumn<ScheduleDetails, String> TerminMeczuColumn;
	@FXML
	private TableView<TeamStatDetails> TabelaLigowa;

	// @FXML
	// private TableColumn<TeamStatDetails, Integer> TabelaLigowaPoz;

	@FXML
	private TableColumn<TeamStatDetails, String> TabelaLigowaDr;
	@FXML
	private TableColumn<TeamStatDetails, Integer> TabelaLigowaPoz;

	@FXML
	private TableColumn<TeamStatDetails, Integer> TabelaLigowaMecze;

	@FXML
	private TableColumn<TeamStatDetails, Integer> TabelaLigowaBrZd;

	@FXML
	private TableColumn<TeamStatDetails, Integer> TabelaLigowaBrSt;

	@FXML
	private TableColumn<TeamStatDetails, Integer> TabelaLigowaPunkty;
	@FXML
	private TableView<GoalScorersDetails> StrzelcyTable;

	@FXML
	private TableColumn<GoalScorersDetails, String> StrzelcyDane;

	@FXML
	private TableColumn<GoalScorersDetails, Integer> StrzelcyTeam;

	@FXML
	private TableColumn<GoalScorersDetails, Integer> StrzelcyGole;
	@FXML
	private RadioButton radiobutton1;

	@FXML
	private ToggleGroup ToggleGroup1;

	@FXML
	private RadioButton Radiobutton2;

	@FXML
	private Label labeltekst3;

	@FXML
	private Label labeltekst2;

	@FXML
	private Label labeltekst1;
	@FXML
	private Label lbldyscyplina;

	@FXML
	private JFXTextField OdstepMiedzyMeczamiTF;
	@FXML
	private JFXTimePicker timepicker;
	@FXML
	private JFXDatePicker datepicker;
	StringConverter<LocalTime> converter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.getDefault());
	public ObservableList<ScheduleDetails> matcheslist = FXCollections.observableArrayList();
	private ObservableList<TeamDetails> TeamsList = FXCollections.observableArrayList();
	public ObservableList<TeamStatDetails> LeagueTable = FXCollections.observableArrayList();
	public ObservableList<GoalScorersDetails> ScorersList = FXCollections.observableArrayList();
	public String xxxxx = "2";
	public int idd;
	public String Dyscyplina;
	Integer RokRozpoczecia, RokZakonczenia, PrzewidywanyRokZakonczenia;

	public void initialize() {
		// datepicker.setConverter(value);
		timepicker.setConverter(converter);
		timepicker.setIs24HourView(true);

		OdstepMiedzyMeczamiTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					OdstepMiedzyMeczamiTF.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		ToggleGroup1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

				if (radiobutton1.isSelected()) {

					OdstepMiedzyMeczamiTF.setVisible(false);
					datepicker.setVisible(false);
					labeltekst2.setVisible(false);
					labeltekst3.setVisible(false);
					timepicker.setVisible(false);

				} else {
					OdstepMiedzyMeczamiTF.setVisible(true);
					datepicker.setVisible(true);
					labeltekst2.setVisible(true);
					labeltekst3.setVisible(true);
					timepicker.setVisible(true);
				}

			}

		});
		Kolejki.setRowFactory(new Callback<TableView<ScheduleDetails>, TableRow<ScheduleDetails>>() {
			@Override
			public TableRow<ScheduleDetails> call(TableView<ScheduleDetails> tableView) {
				final TableRow<ScheduleDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem DoMeczu = new MenuItem("Przejdz do meczu");

				DoMeczu.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						doPaneluMeczowego(event);
					}
				});
				contextMenu.getItems().addAll(DoMeczu);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
		// ShowTeams();
		// System.out.println(idd);
	}

	public void setparams(int id, String dyscyplina, String Nazwa) {
		this.NazwaLigiLBL.setText(Nazwa);
		this.idligi.setText(String.valueOf(id));
		this.idd = id;
		this.lbldyscyplina.setText(dyscyplina);
		ShowTeams(id);
		Dyscyplina = dyscyplina;
		System.out.println(Dyscyplina);
		if (dyscyplina.equals("Siatkówka")) {
			GoleGospColumn.setText("Sety");
			GoleGoscColumn.setText("Sety");
			StrzelcyGole.setText("Punkty");
			TabelaLigowaBrZd.setText("SetyZD");
			TabelaLigowaBrSt.setText("SetyST");

		}
		if (dyscyplina.equals("Koszykówka")) {
			GoleGospColumn.setText("Punkty");
			GoleGoscColumn.setText("Punkty");
			StrzelcyGole.setText("Punkty");
			TabelaLigowaBrZd.setText("PktZD");
			TabelaLigowaBrSt.setText("PktST");

		}

		// System.out.println(String.valueOf(id));

	}

	public void ShowTeams(int a) {

		try {

			TeamsList = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("Select * from druzyny where IDLigi = " + a);
			while (rs.next()) {
				// get string from db,whichever way
				TeamsList.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6)));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		try {
			matcheslist = FXCollections.observableArrayList();
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"SELECT m.Kolejka, m.DataMeczu, m.godzinameczu, d1.NazwaD, m.BramkiGospodarzy, d2.NazwaD, m.BramkiGosci from mecze m join druzyny d1 on "
							+ "d1.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 where m.idligi = "
							+ a + " order by m.kolejka");
			if (rs.next()) {
				do {

					matcheslist.add(new ScheduleDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7)));
				} while (rs.next());

				numerkolColumn.setCellValueFactory(new PropertyValueFactory<>("kolejka"));
				TerminMeczuColumn.setCellValueFactory(new PropertyValueFactory<>("terminmeczu"));
				GodzinaColumn.setCellValueFactory(new PropertyValueFactory<>("godzina"));
				Druzyna1Column.setCellValueFactory(new PropertyValueFactory<>("Team1"));
				GoleGospColumn.setCellValueFactory(new PropertyValueFactory<>("GoleGosp"));
				Druzyna2Column.setCellValueFactory(new PropertyValueFactory<>("Team2"));
				GoleGoscColumn.setCellValueFactory(new PropertyValueFactory<>("GoleGosc"));
				Kolejki.setItems(null);
				Kolejki.setItems(matcheslist);
				GenerujTerminarz.setVisible(false);
				Radiobutton2.setVisible(false);
				radiobutton1.setVisible(false);
				OdstepMiedzyMeczamiTF.setVisible(false);
				datepicker.setVisible(false);
				labeltekst1.setVisible(false);
				labeltekst2.setVisible(false);
				labeltekst3.setVisible(false);
				timepicker.setVisible(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in
		// model class.

		SpisNazwa.setCellValueFactory(new PropertyValueFactory<>("NazwaD"));
		SpisDruzyn.setItems(null);
		SpisDruzyn.setItems(TeamsList);
		int pozycja = 0;
		try {

			LeagueTable = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("Select d.NazwaD,sk.Mecze, sk.BramkiZD, "
					+ "sk.BramkiST,sk.Punkty from statystykiklubu as sk JOIN statystyki_druzyny as sd on "
					+ "sd.ID_Statystyki_Druzyny = sk.ID_Statystyki_Druzyny join druzyny as d on d.IDDruzyny = sd.ID_Druzyny where "
					+ "sd.ID_Ligi = " + a + " and d.NazwaD != 'wolny los' order by sk.Punkty desc, sk.BramkiZD desc");
			while (rs.next()) {
				pozycja = pozycja + 1;
				System.out.println(pozycja);
				// get string from db,whichever way
				LeagueTable.add(new TeamStatDetails(pozycja, rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
						rs.getInt(5)));

			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in
		// model class.
		// TabelaLigowaPoz.setCellValueFactory(new
		// PropertyValueFactory<>("sss"));
		TabelaLigowaPoz.setCellValueFactory(new PropertyValueFactory<>("pozycja"));
		TabelaLigowaDr.setCellValueFactory(new PropertyValueFactory<>("druzyna"));
		TabelaLigowaMecze.setCellValueFactory(new PropertyValueFactory<>("rozegranemecze"));
		TabelaLigowaBrZd.setCellValueFactory(new PropertyValueFactory<>("BramkiZdobyte"));
		TabelaLigowaBrSt.setCellValueFactory(new PropertyValueFactory<>("BramkiStracone"));
		TabelaLigowaPunkty.setCellValueFactory(new PropertyValueFactory<>("Punkty"));
		TabelaLigowa.setItems(null);
		TabelaLigowa.setItems(LeagueTable);
		try {

			ScorersList = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"Select Concat (z.Imie , ' ' , z.Nazwisko) as Zawodnik, d.NazwaD, sum(sz.Bramka)  from statystykizawodnikow sz join mecze m "
							+ "on m.idmeczu = sz.IdMeczu join zawodnicy z on z.IDZawodnik = sz.IdZawodnika join druzyny d on z.ID_Druzyny = d.IDDruzyny where m.idligi ="
							+ a + " GROUP BY sz.IdZawodnika  Order By sz.Bramka desc");

			while (rs.next()) {
				// get string from db,whichever way
				ScorersList.add(new GoalScorersDetails(rs.getString(1), rs.getString(2), rs.getInt(3)));

			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in
		// model class.
		// TabelaLigowaPoz.setCellValueFactory(new
		// PropertyValueFactory<>("sss"));
		StrzelcyDane.setCellValueFactory(new PropertyValueFactory<>("Strzelec"));
		StrzelcyTeam.setCellValueFactory(new PropertyValueFactory<>("Klub"));
		StrzelcyGole.setCellValueFactory(new PropertyValueFactory<>("Gole"));
		StrzelcyTable.setItems(null);
		StrzelcyTable.setItems(ScorersList);

	}

	@FXML
	public void GenerujT(ActionEvent event) {
		int liczbakolejek = 0;
		UserpanelController UPC = new UserpanelController();
		if (TeamsList.size() % 2 == 0) {
			liczbakolejek = (TeamsList.size() - 1) * 2;
		}
		if (TeamsList.size() % 2 == 1) {
			liczbakolejek = TeamsList.size() * 2;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		LocalDate temp = null;
		if (datepicker.getValue() != null && !OdstepMiedzyMeczamiTF.getText().trim().isEmpty()) {
			try {
				dt = format.parse(datepicker.getValue().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < liczbakolejek; i++) {
				if (i > 0) {
					temp = new DateTime(dt).plusDays(Integer.valueOf(OdstepMiedzyMeczamiTF.getText())).toLocalDate();
					dt = temp.toDate();
				}
				if (i == 0) {
					temp = new DateTime(dt).plusDays(0).toLocalDate();
					dt = temp.toDate();
				}
				if (i == liczbakolejek - 1) {
					temp = new DateTime(dt).plusDays(Integer.valueOf(OdstepMiedzyMeczamiTF.getText())).toLocalDate();
					dt = temp.toDate();
					PrzewidywanyRokZakonczenia = temp.getYear();
					System.out.println("Przewidywany rok zakonczenia to " + PrzewidywanyRokZakonczenia);
				}
				System.out.println(temp);
				System.out.println("Liczbakolejek to " + liczbakolejek);
			}
		} else {
			UPC.AlertInformacyjny("B³¹d", "B³¹d", "Pole z dat¹ jest puste");
		}

		java.time.LocalDate LD = datepicker.getValue();
		try {
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"Select s.Rok_Rozpoczecia, s.Rok_Zakonczenia from ligi l join sezony as s on s.id_Sezon = l.id_Sezon WHERE  IDLigi ="
							+ idd);
			if (rs.next()) {
				RokRozpoczecia = rs.getInt(1);
				RokZakonczenia = rs.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (radiobutton1.isSelected()) {
			GenerowanieBezTerminow();
		}
		if (Radiobutton2.isSelected()) {

			if (datepicker.getValue() == null || OdstepMiedzyMeczamiTF.getText().trim().isEmpty()) {

				UPC.AlertInformacyjny("B³¹d", "B³¹d", "Jeden z atrybutów (data lub odstêp) nie zosta³y wybrane");
			} else if (LD.getYear() != RokRozpoczecia) {

				UPC.AlertInformacyjny("B³¹d", "B³¹d", "Data rozpoczêcia jest niezgodna z przypisanym sezonem");

			} else if (RokZakonczenia < PrzewidywanyRokZakonczenia) {
				System.out.println("PRZ = " + PrzewidywanyRokZakonczenia + " RZ = " + RokZakonczenia);
				UPC.AlertInformacyjny("B³¹d", "B³¹d terminarza",
						"Wed³ug wygenerowanego terminarza Rozgrywki zakoñcz¹ siê po roku zadeklarowanym jako rok zakoñczenia w sezonie. Zmieñ datê rozpoczêcia lub odstêpy miedzy meczami");
			} else {
				java.time.LocalDate xyz = datepicker.getValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String formattedString = xyz.format(formatter);
				System.out.println(formattedString);
				GenerowanieZTerminami(formattedString, Integer.valueOf(OdstepMiedzyMeczamiTF.getText()),
						timepicker.getValue().toString());
			}

		}

	}

	public void GenerowanieBezTerminow() {
		ObservableList<ScheduleDetails> listameczowstr = FXCollections.observableArrayList();
		if (matcheslist.isEmpty()) {
			List<String> druzynyStr = new ArrayList<>();
			List<Integer> druzyny = new ArrayList<>();
			try {
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT IDDruzyny,NazwaD FROM druzyny WHERE IDLigi =" + Integer.valueOf(idligi.getText()));
				while (rs.next()) {
					// get string from db,whichever way
					druzyny.add(rs.getInt(1));
					druzynyStr.add(rs.getString(2));
				}
			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}
			int iloscdruzyn = druzyny.size();
			// If odd number of teams add a "ghost".
			boolean ghost = false;
			if (iloscdruzyn % 2 == 1) {
				iloscdruzyn++;
				ghost = true;
			}
			int ilosckolejek = iloscdruzyn - 1;
			int meczenakolejke = iloscdruzyn / 2;
			String[][] kolejki = new String[ilosckolejek][meczenakolejke];
			String[][] kolejkiStr = new String[ilosckolejek][meczenakolejke];
			for (int kolejka = 0; kolejka < ilosckolejek; kolejka++) {
				for (int mecz = 0; mecz < meczenakolejke; mecz++) {
					int home = (kolejka + mecz) % (iloscdruzyn - 1);
					String homestring = druzynyStr.get(home);
					int homeint = druzyny.get(home);
					int away = (iloscdruzyn - 1 - mecz + kolejka) % (iloscdruzyn - 1);
					int awayint = druzyny.get(away);
					String awaystring = druzynyStr.get(away);
					if (mecz == 0) {
						away = iloscdruzyn - 1;
						if (away == druzyny.size()) {
							// awaystring = "wolny los";
							druzyny.add(0);
							druzynyStr.add("wolny los");
							awayint = druzyny.get(away);
							awaystring = druzynyStr.get(away);
						} else {
							awayint = druzyny.get(away);
						}
						awaystring = druzynyStr.get(away);
					}
					kolejki[kolejka][mecz] = (homeint) + " v " + (awayint);

					kolejkiStr[kolejka][mecz] = (homestring) + " v " + (awaystring);
				}
			}
			String[][] interleaved = new String[ilosckolejek][meczenakolejke];
			String[][] interleavedStr = new String[ilosckolejek][meczenakolejke];
			int evn = 0;
			int evnStr = 0;
			int odd = (iloscdruzyn / 2);
			int oddStr = (iloscdruzyn / 2);
			for (int i = 0; i < kolejki.length; i++) {
				if (i % 2 == 0) {
					interleaved[i] = kolejki[evn++];

					interleavedStr[i] = kolejkiStr[evnStr++];
				} else {
					interleaved[i] = kolejki[odd++];
					interleavedStr[i] = kolejkiStr[oddStr++];
				}
			}
			kolejki = interleaved;
			kolejkiStr = interleavedStr;
			for (int kolejka = 0; kolejka < kolejki.length; kolejka++) {
				if (kolejka % 2 == 1) {
					kolejki[kolejka][0] = flip(kolejki[kolejka][0]);
					kolejkiStr[kolejka][0] = flip(kolejkiStr[kolejka][0]);
				}
			}
			String newLine = System.getProperty("line.separator");
			for (int i = 0; i < kolejki.length; i++) {
				System.out.println("Kolejka " + (i + 1));
				for (int j = 0; j < meczenakolejke; j++) {
					String[] slowa = kolejki[i][j].split("v");
					String[] slowaStr = kolejkiStr[i][j].split("v");
					System.out.println(slowa[0]);
					matcheslist.add(new ScheduleDetails(i + 1, null, null, slowa[0], null, slowa[1], null));
					listameczowstr.add(new ScheduleDetails(i + 1, null, null, slowaStr[0], null, slowaStr[1], null));

					System.out.println(Arrays.asList(kolejki[i][j]));
					System.out.println(Arrays.asList(kolejkiStr[i][j]));
					System.out.println();
				}
			}

			// System.out.println("Second half is mirror of first half") ;
			for (int kolejka = 0; kolejka < kolejki.length; kolejka++) {

				for (int j = 0; j < meczenakolejke; j++) {
					kolejki[kolejka][j] = flip(kolejki[kolejka][j]);

					kolejkiStr[kolejka][j] = flip(kolejkiStr[kolejka][j]);

				}
				System.out.println("Kolejka " + (iloscdruzyn + kolejka));

				for (int j = 0; j < meczenakolejke; j++) {
					String[] slowa = kolejki[kolejka][j].split("v");

					String[] slowaStr = kolejkiStr[kolejka][j].split("v");
					matcheslist.add(
							new ScheduleDetails(iloscdruzyn + kolejka, null, null, slowa[0], null, slowa[1], null));
					listameczowstr.add(new ScheduleDetails(iloscdruzyn + kolejka, null, null, slowaStr[0], null,
							slowaStr[1], null));
					System.out.println(Arrays.asList(kolejki[kolejka][j]));
					System.out.println(Arrays.asList(kolejkiStr[kolejka][j]));
					System.out.println();
				}
			}
			for (int i = 0; i < matcheslist.size(); i++) {
				System.out.println(matcheslist.get(i).getTeam1());
				System.out.println(matcheslist.get(i).getTeam2());
				if (Integer.parseInt(matcheslist.get(i).getTeam1().trim()) == 0
						|| Integer.parseInt(matcheslist.get(i).getTeam2().trim()) == 0) {
					System.out.println("ok");
				} // SPRAWDZANIE
			}

			if (ghost) {
				System.out.println("Matches against team " + iloscdruzyn + " are byes.");
			}

			System.out.println("Use mirror image of these kolejki for " + "return fixtures.");
			numerkolColumn.setCellValueFactory(new PropertyValueFactory<>("kolejka"));
			TerminMeczuColumn.setCellValueFactory(new PropertyValueFactory<>("terminmeczu"));
			Druzyna1Column.setCellValueFactory(new PropertyValueFactory<>("Team1"));
			Druzyna2Column.setCellValueFactory(new PropertyValueFactory<>("Team2"));

			Kolejki.setItems(null);
			Kolejki.setItems(listameczowstr);
			ZapiszTerminarzBTN.setVisible(true);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setContentText("Terminarz zosta³ ju¿ wygenerowany");
			alert.showAndWait();
		}
	}

	public void GenerowanieZTerminami(String DataStartowa, Integer Odstepy, String Godzina) {

		ObservableList<ScheduleDetails> matcheslistStr = FXCollections.observableArrayList();
		if (matcheslist.isEmpty()) {
			long Start = System.nanoTime();
			List<String> druzynyStr = new ArrayList<>();
			List<Integer> druzyny = new ArrayList<>();
			try {
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT IDDruzyny,NazwaD FROM druzyny WHERE IDLigi =" + Integer.valueOf(idligi.getText()));
				while (rs.next()) {
					// get string from db,whichever way
					druzyny.add(rs.getInt(1));
					druzynyStr.add(rs.getString(2));
				}

			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}

			int iloscdruzyn = druzyny.size();
			boolean ghost = false;
			if (iloscdruzyn % 2 == 1) {
				iloscdruzyn++;

				ghost = true;
			}

			int ilosckolejek = iloscdruzyn - 1;
			int meczenakolejke = iloscdruzyn / 2;
			String[][] kolejki = new String[ilosckolejek][meczenakolejke];
			String[][] kolejkiStr = new String[ilosckolejek][meczenakolejke];
			for (int kolejka = 0; kolejka < ilosckolejek; kolejka++) {
				for (int mecz = 0; mecz < meczenakolejke; mecz++) {
					int home = (kolejka + mecz) % (iloscdruzyn - 1);
					String homestring = druzynyStr.get(home);
					int homeint = druzyny.get(home);
					int away = (iloscdruzyn - 1 - mecz + kolejka) % (iloscdruzyn - 1);
					int awayint = druzyny.get(away);
					String awaystring = druzynyStr.get(away);
					if (mecz == 0) {
						away = iloscdruzyn - 1;
						if (away == druzyny.size()) {
							// awaystring = "wolny los";
							druzyny.add(0);
							druzynyStr.add("wolny los");
							awayint = druzyny.get(away);
							awaystring = druzynyStr.get(away);
						} else {
							awayint = druzyny.get(away);
						}
						awaystring = druzynyStr.get(away);

					}
					kolejki[kolejka][mecz] = (homeint) + " v " + (awayint);
					kolejkiStr[kolejka][mecz] = (homestring) + " v " + (awaystring);

				}
			}
			String[][] interleaved = new String[ilosckolejek][meczenakolejke];
			String[][] interleavedStr = new String[ilosckolejek][meczenakolejke];
			int evn = 0;
			int evnStr = 0;
			int odd = (iloscdruzyn / 2);
			int oddStr = (iloscdruzyn / 2);
			for (int i = 0; i < kolejki.length; i++) {
				if (i % 2 == 0) {
					interleaved[i] = kolejki[evn++];
					interleavedStr[i] = kolejkiStr[evnStr++];
				} else {
					interleaved[i] = kolejki[odd++];
					interleavedStr[i] = kolejkiStr[oddStr++];
				}
			}

			kolejki = interleaved;

			kolejkiStr = interleavedStr;

			for (int kolejka = 0; kolejka < kolejki.length; kolejka++) {
				if (kolejka % 2 == 1) {
					kolejki[kolejka][0] = flip(kolejki[kolejka][0]);
					kolejkiStr[kolejka][0] = flip(kolejkiStr[kolejka][0]);

				}
			}
			String newLine = System.getProperty("line.separator");
			// Display the fixtures
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = null;
			LocalDate temp = null;
			try {
				dt = format.parse(DataStartowa);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < kolejki.length; i++) {
				if (i > 0) {
					temp = new DateTime(dt).plusDays(Odstepy).toLocalDate();
					dt = temp.toDate();
				}
				if (i == 0) {
					temp = new DateTime(dt).plusDays(0).toLocalDate();
					dt = temp.toDate();
				}

				System.out.println(temp);
				System.out.println("Kolejka " + (i + 1));
				for (int j = 0; j < meczenakolejke; j++) {
					String[] slowa = kolejki[i][j].split("v");
					String[] slowaStr = kolejkiStr[i][j].split("v");
					System.out.println(slowa[0]);
					matcheslist.add(
							new ScheduleDetails(i + 1, String.valueOf(temp), Godzina, slowa[0], null, slowa[1], null));
					matcheslistStr.add(new ScheduleDetails(i + 1, String.valueOf(temp), Godzina, slowaStr[0], null,
							slowaStr[1], null));
					System.out.println(Arrays.asList(kolejki[i][j]));
					System.out.println(Arrays.asList(kolejkiStr[i][j]));
					System.out.println();
				}
			}
			for (int kolejka = 0; kolejka < kolejki.length; kolejka++) {
				temp = new DateTime(dt).plusDays(Odstepy).toLocalDate();
				dt = temp.toDate();
				for (int j = 0; j < meczenakolejke; j++) {
					kolejki[kolejka][j] = flip(kolejki[kolejka][j]);
					kolejkiStr[kolejka][j] = flip(kolejkiStr[kolejka][j]);
				}
				System.out.println("Kolejka " + (iloscdruzyn + kolejka));
				for (int j = 0; j < meczenakolejke; j++) {
					String[] slowa = kolejki[kolejka][j].split("v");
					String[] slowaStr = kolejkiStr[kolejka][j].split("v");
					matcheslist.add(new ScheduleDetails(iloscdruzyn + kolejka, String.valueOf(temp), Godzina, slowa[0],
							null, slowa[1], null));
					matcheslistStr.add(new ScheduleDetails(iloscdruzyn + kolejka, String.valueOf(temp), Godzina,
							slowaStr[0], null, slowaStr[1], null));
					System.out.println(Arrays.asList(kolejki[kolejka][j]));
					System.out.println(Arrays.asList(kolejkiStr[kolejka][j]));
					System.out.println();
				}
			}
			for (int i = 0; i < matcheslist.size(); i++) {
				System.out.println(matcheslist.get(i).getTeam1());
				System.out.println(matcheslist.get(i).getTeam2());
				if (Integer.parseInt(matcheslist.get(i).getTeam1().trim()) == 0
						|| Integer.parseInt(matcheslist.get(i).getTeam2().trim()) == 0) {
					System.out.println("ok");
				}
			}

			if (ghost) {
				System.out.println("Matches against team " + iloscdruzyn + " are byes.");
			}

			System.out.println("Use mirror image of these kolejki for " + "return fixtures.");
			numerkolColumn.setCellValueFactory(new PropertyValueFactory<>("kolejka"));
			TerminMeczuColumn.setCellValueFactory(new PropertyValueFactory<>("terminmeczu"));
			GodzinaColumn.setCellValueFactory(new PropertyValueFactory<>("godzina"));
			Druzyna1Column.setCellValueFactory(new PropertyValueFactory<>("Team1"));
			Druzyna2Column.setCellValueFactory(new PropertyValueFactory<>("Team2"));

			Kolejki.setItems(null);
			Kolejki.setItems(matcheslistStr);
			ZapiszTerminarzBTN.setVisible(true);
			long stop = System.nanoTime();
			System.out.println(Start);
			System.out.println(stop);
			System.out.println("Generowanie terminarza");
			System.out.println("Czas wykonania w milisekundach: " + (stop - Start) / 1000000);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setContentText("Terminarz zosta³ ju¿ wygenerowany");
			alert.showAndWait();
		}
	}

	@FXML
	public void ZapisTerminarza(ActionEvent event) {
		long start = System.nanoTime();
		System.out.print(matcheslist.get(0).getTeam1().toString());
		int idwolnegolosu = 0;
		try {
			Statement myStmt = DBC.con.createStatement();
			String sql = "INSERT INTO druzyny (NazwaD,IDLigi) SELECT * FROM (SELECT 'Wolny Los', null) AS tmp WHERE NOT EXISTS ("
					+ "SELECT NazwaD FROM druzyny WHERE NazwaD = 'Wolny Los' and IDLigi is null) ";
			myStmt.executeUpdate(sql);
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"Select IDDruzyny from druzyny where NazwaD = 'Wolny Los' and IDLigi is null limit 1");
			if (rs.next()) {
				idwolnegolosu = rs.getInt(1);
			}
			for (int i = 0; i < matcheslist.size(); i++) {
				if (Integer.parseInt(matcheslist.get(i).getTeam1().trim()) == 0
						|| Integer.parseInt(matcheslist.get(i).getTeam2().trim()) == 0) {
					if (Integer.parseInt(matcheslist.get(i).getTeam1().trim()) == 0) {
						matcheslist.get(i).setTeam1(String.valueOf(idwolnegolosu));
					}
					if (Integer.parseInt(matcheslist.get(i).getTeam2().trim()) == 0) {
						matcheslist.get(i).setTeam2(String.valueOf(idwolnegolosu));
					}

				}
				myStmt = DBC.con.createStatement();
				sql = "Insert INTO mecze (idligi, Kolejka,DataMeczu,godzinameczu,druzyna1,druzyna2) VALUES" + "("
						+ Integer.valueOf(idligi.getText()) + "," + matcheslist.get(i).getKolejka() + ",'"
						+ matcheslist.get(i).getterminmeczu() + "','" + matcheslist.get(i).getgodzina() + "','"
						+ matcheslist.get(i).getTeam1() + "','" + matcheslist.get(i).getTeam2() + "')";

				myStmt.executeUpdate(sql);

			}
			long stop = System.nanoTime();
			System.out.println(start);
			System.out.println(stop);
			System.out.println("Zapis terminarza do bazy danych");
			System.out.println("Czas wykonania w milisekundach: " + (stop - start) / 1000000);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("");
			alert2.setHeaderText("Uda³o siê");
			alert2.setContentText("Pomyœlnie zapisany terminarz");
			alert2.showAndWait();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private static String flip(String mecz) {
		String[] components = mecz.split(" v ");
		return components[1] + " v " + components[0];
	}

	private static String wypiszteamy(String mecz) {
		String[] components = mecz.split(" v ");
		System.out.println(components[1] + " v " + components[0]);

		return components[1];
	}

	public TableView<ScheduleDetails> getTabela() {
		return Kolejki;
	}

	public TableView<TeamStatDetails> getTabela2() {
		return TabelaLigowa;
	}

	public TableView<GoalScorersDetails> getTabela3() {
		return StrzelcyTable;
	}

	public void doPaneluMeczowego(ActionEvent event) {

		try {
			String Gospodarze = Kolejki.getSelectionModel().getSelectedItem().getTeam1();
			String Goscie = Kolejki.getSelectionModel().getSelectedItem().getTeam2();
			String GoleGospodarzy = Kolejki.getSelectionModel().getSelectedItem().getGoleGosp();
			String GoleGosci = Kolejki.getSelectionModel().getSelectedItem().getGoleGosc();
			String terminmeczu = Kolejki.getSelectionModel().getSelectedItem().getterminmeczu();
			String Godzina = Kolejki.getSelectionModel().getSelectedItem().getgodzina();
			// int GoleGospodarzy =
			// Integer.valueOf(Kolejki.getSelectionModel().getSelectedItem().getGoleGosp());
			// int GoleGosci =
			// Integer.valueOf(Kolejki.getSelectionModel().getSelectedItem().getGoleGosc());
			// int PoziomL =
			// tableLeagues.getSelectionModel().getSelectedItem().getPoziomL();
			// int LimitDruzyn =
			// tableLeagues.getSelectionModel().getSelectedItem().getMaksymalnaLiczbaDruzyn();
			// int RokRoz =
			// tableLeagues.getSelectionModel().getSelectedItem().getRokRozpoczecia();
			// int RokZak =
			// tableLeagues.getSelectionModel().getSelectedItem().getRokZakonczenia();
			int Kolejka = Kolejki.getSelectionModel().getSelectedItem().getKolejka();
			int idmeczu = 0;
			String obiektgospodarza = null;
			try {
				ResultSet rs = DBC.con.createStatement()
						.executeQuery("Select idmeczu from mecze where idligi = " + idd + " and Kolejka = " + Kolejka
								+ " and " + "druzyna1 =(Select iddruzyny from druzyny where NazwaD = '" + Gospodarze
								+ "') and " + "druzyna2 = (Select iddruzyny from druzyny where NazwaD = '" + Goscie
								+ "')");
				if (rs.next()) {
					idmeczu = rs.getInt(1);
				}
				rs = DBC.con.createStatement()
						.executeQuery("Select NazwaS from stadiony as s where "
								+ "s.IDStadionu =(Select IDStadionu from druzyny where druzyny.IDDruzyny = (Select druzyna1 from mecze where idmeczu = "
								+ idmeczu + ") )");
				if (rs.next()) {
					obiektgospodarza = rs.getString(1);
				} else {
					obiektgospodarza = "Nieokreœlone";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MatchPanel.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerMatchPanel CEMP = (ControllerMatchPanel) fxmlLoader.getController();
			CEMP.setItems(Kolejki.getItems());
			CEMP.setItems2(TabelaLigowa.getItems());
			CEMP.setItems3(StrzelcyTable.getItems());
			CEMP.setall(idd, Dyscyplina, terminmeczu, Godzina, obiektgospodarza, idmeczu, Kolejka, Gospodarze, Goscie,
					GoleGospodarzy, GoleGosci);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

		} catch (Exception e) {

		}
	}

	public void odswiez() {
		System.out.println(timepicker.getValue());
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
