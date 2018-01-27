package Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javafx.beans.binding.Bindings;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.IncomingMessageDetails;
import model.LeagueDetails;
import model.ObjectDetails;
import model.OutgoingMessageDetails;
import model.PlayerDetails;
import model.TeamDetails;
import model.UserDetails;

public class UserpanelController {

	Controller ctrl2 = new Controller();
	@FXML
	public Pane userpanelpane;
	public PlayerDetails wybranygracz;
	@FXML
	public Label loginuzytkownika;
	@FXML
	public Label lblid;
	// tu s¹ komponenty ze starego controllera
	@FXML
	private Button Rejestracja;
	@FXML
	private TextField TFEname;
	@FXML
	private TextField TFEsurname;
	@FXML
	private TextField TFEdataurodzenia;
	@FXML
	private TextField TFEwaga;
	@FXML
	private TextField TFEwzrost;
	@FXML
	private TextField TFEpozycja;
	// tu s¹ komponenty ze starego controllera
	@FXML
	// public TextArea adresy;
	public Label Loginlbl;

	@FXML
	public Button pokazwnioski;
	@FXML
	public TabPane tabpane;
	@FXML
	public Button pokazwnioski2;
	@FXML
	public Button WyswietlDrButton;
	@FXML
	public Button editPlayerBTN;
	@FXML
	public Button UsunZawpdmolaButton;
	@FXML
	public Button BTNaddplayer;
	@FXML
	public Label aaa;
	@FXML
	public Button editTeamBTN;
	@FXML
	public Button DodajDButton1;
	@FXML
	private Button WyswietlStButton;
	@FXML
	private Button EdytujStButton;
	@FXML
	private Button UsunObiektButton;
	@FXML
	private Button DodajStButton;
	@FXML
	private Button WyswietlLButton;
	@FXML
	private Button EdytujLButton;
	@FXML
	private Button DodajLButton;
	@FXML
	private TableView<UserDetails> TableUzytkownicy;

	@FXML
	private TableColumn<UserDetails, Integer> ColumnIDUz;

	@FXML
	private TableColumn<UserDetails, String> ColumnUsername;

	@FXML
	private TableColumn<UserDetails, String> ColumnPasswordUz;

	@FXML
	private TableColumn<UserDetails, String> ColumnPoziom;

	@FXML
	private Button WyswietlUzBTN;

	@FXML
	private Button EdytujUzBTN;

	@FXML
	private Button UsunUzBTN;
	@FXML
	private TableView<IncomingMessageDetails> TableWiadomosci;

	@FXML
	private TableColumn<IncomingMessageDetails, ?> ColumnIDWiadomosci;

	@FXML
	private TableColumn<IncomingMessageDetails, ?> ColumnAutorW;

	@FXML
	private TableColumn<IncomingMessageDetails, ?> ColumnTrescW;
	@FXML
	private TableColumn<IncomingMessageDetails, ?> ColumnZwrotW;

	@FXML
	private Button WyswietlWBTN;

	@FXML
	private Button UsunWBTN;

	@FXML
	private Button BTNodpowiedz;
	@FXML
	private TableView<OutgoingMessageDetails> TableWiadomosciWych;

	@FXML
	private TableColumn<OutgoingMessageDetails, ?> ColumnIDWiadomosciW;

	@FXML
	private TableColumn<OutgoingMessageDetails, ?> ColumnAutorWW;
	@FXML
	private TableColumn<OutgoingMessageDetails, ?> ColumnAdresatWW;

	@FXML
	private TableColumn<OutgoingMessageDetails, ?> ColumnTrescWW;

	@FXML
	private Button CreateMessageBTN;
	@FXML
	private Button WyswietlWWBTN;

	@FXML
	private Button UsunWBTN1;

	@FXML
	public ChoiceBox<String> teamChoice;
	@FXML
	public ChoiceBox<String> teamChoice2;
	@FXML
	private ChoiceBox<String> LigiSportyCB;
	@FXML
	public TableColumn<UserDetails, String> columnID1;
	@FXML
	public TableColumn<UserDetails, String> columnname1;
	@FXML
	public TableColumn<UserDetails, String> columnsurname1;
	@FXML
	public TableColumn<UserDetails, String> columnage1;
	@FXML
	public TableColumn<UserDetails, String> columnusername1;
	@FXML
	public TableColumn<UserDetails, String> columnpassword1;
	@FXML
	public TableView<UserDetails> tableUser;
	@FXML
	public TableColumn<UserDetails, String> columnName;
	@FXML
	public TableColumn<UserDetails, String> columnSurname;
	@FXML
	public TableColumn<UserDetails, String> columnAge;
	@FXML
	public TableColumn<UserDetails, String> columnPassword;
	@FXML
	public TableView<TeamDetails> tableTeams;
	@FXML
	public TableColumn<TeamDetails, String> columnIDDruzyny;
	@FXML
	public TableColumn<TeamDetails, String> columnNazwaD;
	@FXML
	public TableColumn<TeamDetails, String> columnRokZalozenia;
	@FXML
	public TableColumn<TeamDetails, String> columnMiastoD;
	@FXML
	public TableColumn<TeamDetails, String> columnIDStadionu;
	@FXML
	public TableColumn<TeamDetails, String> columnIDLigiD;
	@FXML
	public TableView<PlayerDetails> tablePlayers;
	@FXML
	public TableColumn<PlayerDetails, Integer> columnIDzaw;
	@FXML
	public TableColumn<PlayerDetails, String> columnImie;
	@FXML
	public TableColumn<PlayerDetails, String> columnNazwisko;
	@FXML
	public TableColumn<PlayerDetails, String> columnWaga;
	@FXML
	public TableColumn<PlayerDetails, Date> columnDataur;
	@FXML
	public TableColumn<PlayerDetails, Integer> columnWzrost;
	@FXML
	public TableColumn<PlayerDetails, String> columnPozycja;
	@FXML
	public TableColumn<PlayerDetails, String> columnDruzyna;
	@FXML
	public TableView<ObjectDetails> tableObjects;
	@FXML
	private TableColumn<ObjectDetails, Integer> columnIDstad;
	@FXML
	private TableColumn<ObjectDetails, String> columnNazwaS;
	@FXML
	private TableColumn<ObjectDetails, String> columnMiastoS;
	@FXML
	private TableColumn<ObjectDetails, Integer> columnRokBudS;
	@FXML
	private TableColumn<ObjectDetails, Integer> columnPojemnoscS;
	@FXML
	private TableView<LeagueDetails> tableLeagues;
	@FXML
	private TableColumn<LeagueDetails, Integer> columnIDLigi;
	@FXML
	private TableColumn<LeagueDetails, String> columnNazwaL;
	@FXML
	private TableColumn<LeagueDetails, String> columnObszarL;
	@FXML
	private TableColumn<LeagueDetails, Integer> columnPoziomL;
	@FXML
	private TableColumn<LeagueDetails, Integer> columnMaxD;
	@FXML
	private TableColumn<LeagueDetails, String> columnRokRoz;
	@FXML
	private TableColumn<LeagueDetails, String> columnRokZak;
	@FXML
	private TableColumn<LeagueDetails, String> columnDyscyplina;
	@FXML
	private Button UsunLButton;
	@FXML
	private TableView<TeamDetails> TableZgloszeniaDruzyn;
	@FXML
	private TableColumn<TeamDetails, ?> ColIDZgloszeniaDr;
	@FXML
	private TableColumn<TeamDetails, ?> ColNazwaZglDruzyny;

	@FXML
	private TableColumn<TeamDetails, ?> ColRokZalZglDruzyny;

	@FXML
	private TableColumn<TeamDetails, ?> ColMiastoZglDruzyny;

	@FXML
	private TableColumn<TeamDetails, ?> ColObiektZglDruzyny;

	@FXML
	private TableColumn<TeamDetails, ?> ColLigaZglDruzyny;

	@FXML
	private Button UsunDruzyneBTN;
	@FXML
	private Button BTNWyswietlZglDr;

	@FXML
	private Button BTNPrzyjmijZglDr;
	@FXML
	private TableView<PlayerDetails> TableZgloszeniaZawodnikow;

	@FXML
	private TableColumn<PlayerDetails, ?> ColIDZgloszeniaZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColImieZglZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColNazwZglZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColDataUrZglZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColWagaZglZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColWzrZglZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColPozZglZaw;

	@FXML
	private TableColumn<PlayerDetails, ?> ColDrZglZaw;
	@FXML
	private Button BTNOdrzZglZaw;
	@FXML
	private Button WyswietlZgloszeniaZawBTN;
	@FXML
	private Button BTNOdrzZglDr;
	@FXML
	private Button BTNPrzyZglZaw;
	public ObservableList<UserDetails> data;
	public ObservableList<UserDetails> data2;
	public ObservableList<TeamDetails> data3 = FXCollections.observableArrayList();
	public ObservableList<PlayerDetails> data4 = FXCollections.observableArrayList();
	public ObservableList<ObjectDetails> dataSt = FXCollections.observableArrayList();
	public ObservableList<LeagueDetails> dataL = FXCollections.observableArrayList();
	public ObservableList<UserDetails> dataUz = FXCollections.observableArrayList();
	public ObservableList<String> dataTeams = FXCollections.observableArrayList();
	public ObservableList<String> dataSports = FXCollections.observableArrayList();
	public ObservableList<IncomingMessageDetails> dataWiad = FXCollections.observableArrayList();
	public ObservableList<OutgoingMessageDetails> dataWiadW = FXCollections.observableArrayList();
	public ObservableList<TeamDetails> dataZgloszeniaDruzyn = FXCollections.observableArrayList();
	public ObservableList<PlayerDetails> dataZgloszeniaZawodnikow = FXCollections.observableArrayList();

	public TeamDetails aaateam;
	public String nazwa;
	public String imie;
	public int przekazanie;
	public int idadmina;
	DBconnect DBC = new DBconnect();
	// Controller contr1 = new Controller();
	// private Controller Ctrl = new Controller();
	private MainController mainController;

	// String loginn = Ctrl.loginek;
	public void initialize() {

		/*
		 * 
		 * WA¯NE
		 * 
		 */
		TableZgloszeniaDruzyn.setPlaceholder(new Label("Nie masz nowych zg³oszeñ"));
		tableLeagues.setRowFactory(new Callback<TableView<LeagueDetails>, TableRow<LeagueDetails>>() {
			@Override
			public TableRow<LeagueDetails> call(TableView<LeagueDetails> tableView) {
				final TableRow<LeagueDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem EdytujLMenuItem = new MenuItem("Edytuj Ligê");
				final MenuItem UsunLMenuItem = new MenuItem("Usuñ Ligê");
				final MenuItem DoPaneluLigowego = new MenuItem("Do panelu Ligowego");
				UsunLMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						UsunLige(event);
					}
				});
				EdytujLMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						doEdycjiLigi(event);

					}
				});
				DoPaneluLigowego.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						doPaneluLigowego(event);

					}
				});
				contextMenu.getItems().addAll(EdytujLMenuItem, UsunLMenuItem, DoPaneluLigowego);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
		TableWiadomosciWych
				.setRowFactory(new Callback<TableView<OutgoingMessageDetails>, TableRow<OutgoingMessageDetails>>() {
					@Override
					public TableRow<OutgoingMessageDetails> call(TableView<OutgoingMessageDetails> tableView) {
						final TableRow<OutgoingMessageDetails> row = new TableRow<>();
						final ContextMenu contextMenu = new ContextMenu();
						final MenuItem UsunWWMenuItem = new MenuItem("Usuñ Wiadomoœæ");
						UsunWWMenuItem.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {

								UsunWiadomoscWychodzaca(event);
							}
						});

						contextMenu.getItems().addAll(UsunWWMenuItem);
						// Set context menu on row, but use a binding to make it
						// only
						// show for non-empty rows:
						row.contextMenuProperty().bind(
								Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
						return row;
					}
				});
		TableWiadomosci
				.setRowFactory(new Callback<TableView<IncomingMessageDetails>, TableRow<IncomingMessageDetails>>() {
					@Override
					public TableRow<IncomingMessageDetails> call(TableView<IncomingMessageDetails> tableView) {
						final TableRow<IncomingMessageDetails> row = new TableRow<>();
						final ContextMenu contextMenu = new ContextMenu();
						final MenuItem OdpowiedzNaWiadomosc = new MenuItem("Odpowiedz na wiadomosc");
						final MenuItem UsunWMenuItem = new MenuItem("Usuñ Wiadomoœæ");
						UsunWMenuItem.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {

								UsunWiadomosc(event);
							}
						});
						OdpowiedzNaWiadomosc.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {

								Odpowiedznawiadomosc(event);
							}
						});

						contextMenu.getItems().addAll(UsunWMenuItem, OdpowiedzNaWiadomosc);
						// Set context menu on row, but use a binding to make it
						// only
						// show for non-empty rows:
						row.contextMenuProperty().bind(
								Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
						return row;
					}
				});
		TableUzytkownicy.setRowFactory(new Callback<TableView<UserDetails>, TableRow<UserDetails>>() {
			@Override
			public TableRow<UserDetails> call(TableView<UserDetails> tableView) {
				final TableRow<UserDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem EdytujUMenuItem = new MenuItem("Edytuj u¿ytkownika");
				final MenuItem UsunUMenuItem = new MenuItem("Usuñ U¿ytkownika");
				UsunUMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						// tu trzeba zrobiæ
					}
				});
				EdytujUMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						doEdycjiUzytkownika(event);

					}
				});
				contextMenu.getItems().addAll(UsunUMenuItem, EdytujUMenuItem);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
		tableObjects.setRowFactory(new Callback<TableView<ObjectDetails>, TableRow<ObjectDetails>>() {
			@Override
			public TableRow<ObjectDetails> call(TableView<ObjectDetails> tableView) {
				final TableRow<ObjectDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem EdytujOMenuItem = new MenuItem("Edytuj Obiekt");
				final MenuItem UsunOMenuItem = new MenuItem("Usuñ Obiekt");
				UsunOMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						UsunObiekt(event);
					}
				});
				EdytujOMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						doEdycjiObiektu(event);

					}
				});
				contextMenu.getItems().addAll(EdytujOMenuItem, UsunOMenuItem);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
		tablePlayers.setRowFactory(new Callback<TableView<PlayerDetails>, TableRow<PlayerDetails>>() {
			@Override
			public TableRow<PlayerDetails> call(TableView<PlayerDetails> tableView) {
				final TableRow<PlayerDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem EdytujPMenuItem = new MenuItem("Edytuj zawodnika");
				final MenuItem UsunPMenuItem = new MenuItem("Usuñ zawodnika");
				UsunPMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						UsunZawodnika(event);
					}
				});
				EdytujPMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						doEdycjizawodnika(event);

					}
				});
				contextMenu.getItems().addAll(EdytujPMenuItem, UsunPMenuItem);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});

		TableZgloszeniaZawodnikow.setRowFactory(new Callback<TableView<PlayerDetails>, TableRow<PlayerDetails>>() {
			@Override
			public TableRow<PlayerDetails> call(TableView<PlayerDetails> tableView) {
				final TableRow<PlayerDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem PrzyjmijMenuItem = new MenuItem("Przyjmij Zg³oszenie");
				final MenuItem OdrzucMenuItem = new MenuItem("Odrzuæ Zg³oszenie");
				PrzyjmijMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						PrzyjmijZgloszenieZawodnika(event);
					}
				});
				OdrzucMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						OdrzucZgloszenieZawodnika(event);

					}
				});
				contextMenu.getItems().addAll(PrzyjmijMenuItem, OdrzucMenuItem);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
		tableTeams.setRowFactory(new Callback<TableView<TeamDetails>, TableRow<TeamDetails>>() {
			@Override
			public TableRow<TeamDetails> call(TableView<TeamDetails> tableView) {
				final TableRow<TeamDetails> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				final MenuItem editMenuItem = new MenuItem("Edit");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						UsunDruzyne(event);
					}
				});
				editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						EditTeam();

					}
				});
				contextMenu.getItems().addAll(removeMenuItem, editMenuItem);
				// Set context menu on row, but use a binding to make it only
				// show for non-empty rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
		try {

			dataTeams = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select nazwaL from ligi");
			while (rs.next()) {
				// get string from db,whichever way
				dataTeams.add(rs.getString(1));
			}
			dataTeams.add("Wszystko");
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		try {

			dataSports = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("select Nazwa from dyscypliny");
			while (rs.next()) {
				// get string from db,whichever way
				dataSports.add(rs.getString(1));
			}
			dataSports.add("Wszystko");
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		teamChoice.setItems(dataTeams);
		teamChoice.setValue("Wszystko");
		teamChoice2.setItems(dataTeams);
		teamChoice2.setValue("Wszystko");
		LigiSportyCB.setItems(dataSports);
		LigiSportyCB.setValue("Wszystko");
	}

	// loginuzytkownika.setText(Ctrl.loginek);

	public void GetUser(String user) {
		loginuzytkownika.setText("Witaj " + user);
		Loginlbl.setText("Jesteœ zalogowany jako: \n" + user);

	}

	public void pokazWnioski() throws SQLException {
		// data = FXCollections.observableArrayList();
		// String query23 = "select * from employee_data where
		// username='"+contr1.login.getText()+"' and
		// password='"+contr1.haslo.getText()+"'";
		// String query = "select * from employee_data where
		// username='"+login.getText()+"' and password='"+haslo.getText()+"'";
		// String query2 = "SELECT * FROM `employee_info` JOIN employee_data ON
		// employee_info.id=employee_data.id WHERE employee_data.id=5";
		// DBC.rs = DBC.st.executeQuery(query);
		// System.out.println("Records from database");
		// loginek = login.getText();
		// String query2 = "SELECT * FROM `employee_info` JOIN `employee_data`
		// ON employee_info.id=employee_data.id WHERE"
		// + " employee_data.id="+id+"";
		// DBC.rs = DBC.st.executeQuery(query2);
		// if(DBC.rs.next())
		// {

		// String adres = DBC.rs.getString("adres");
		// System.out.println(adres);
		// GetAddress(adres);
		// }
		// else {
		// GetAddress("Niestety nie masz przypisanych ¿adnych adresów");
		// }
	}

	public void GetId(int id) {
		lblid.setText("Twoje id to: " + id);
		idadmina = id;
	}

	public void GetAddress(String address) {
		// adresy.appendText("Twoje adresy to: "+ "\n" +address);
	}

	@FXML
	public void WyswietlDruzyny() {

		try {
			String wybrane = teamChoice.getValue();
			System.out.println(wybrane);
			Controller contr1 = new Controller();
			System.out.println(przekazanie);
			if (wybrane == "Wszystko") {
				data3.clear();
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT druzyny.IDDruzyny, druzyny.NazwaD, druzyny.RokZalozenia, druzyny.MiastoD, s.NazwaS, l.NazwaL from druzyny "
								+ "LEFT JOIN stadiony as s on druzyny.IDStadionu = s.IDStadionu "
								+ "LEFT JOIN ligi as l on druzyny.IDLigi = l.IDLigi");
				while (rs.next()) {
					// get string from db,whichever way
					data3.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs.getString(5), rs.getString(6)));
				}
			} else {
				data3.clear();
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT druzyny.IDDruzyny, druzyny.NazwaD, druzyny.RokZalozenia, druzyny.MiastoD, s.NazwaS, l.NazwaL from druzyny "
								+ "LEFT JOIN stadiony as s on druzyny.IDStadionu = s.IDStadionu "
								+ "LEFT JOIN ligi as l on druzyny.IDLigi = l.IDLigi where l.NazwaL = '" + wybrane
								+ "'");
				while (rs.next()) {
					// get string from db,whichever way
					data3.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
							rs.getString(5), rs.getString(6)));
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in
		// model class.
		columnIDDruzyny.setCellValueFactory(new PropertyValueFactory<>("IDDruzyny"));
		columnNazwaD.setCellValueFactory(new PropertyValueFactory<>("NazwaD"));
		columnRokZalozenia.setCellValueFactory(new PropertyValueFactory<>("RokZalozenia"));
		columnMiastoD.setCellValueFactory(new PropertyValueFactory<>("MiastoD"));
		columnIDStadionu.setCellValueFactory(new PropertyValueFactory<>("IDStadionu"));
		columnIDLigiD.setCellValueFactory(new PropertyValueFactory<>("Liga"));
		tableTeams.setItems(null);
		tableTeams.setItems(data3);

	}

	public void UsunDruzyne(ActionEvent event) {
		try {
			Integer id = tableTeams.getSelectionModel().getSelectedItem().getIDDruzyny();
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Czy na pewno chcesz usun¹æ druzyne o id: " + id
							+ " ? Usuniête zostan¹ tak¿e wszystkie statystyki tego gracza!",
					ButtonType.OK, ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				try {
					ResultSet rs = DBC.st
							.executeQuery("Select * from mecze where druzyna1 = " + id + " or druzyna2 = " + id + "");
					if (rs.next()) {
						AlertInformacyjny("B³¹d", "B³¹d usuwania",
								"Druzyna któr¹ próbujesz usun¹æ uczestniczy w trwaj¹cych rozgrywkach. Usuniêcie jej  jest niemo¿liwe");
					} else {
						DBC.st.executeUpdate("Delete from druzyny where IDDruzyny = " + id + ";");
						AlertInformacyjny("Informacja", "Operacja wykonana", "Uda³o siê usun¹æ Dru¿ynê");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					// Execute query and store result in a resultset
					data3.clear();
					// Execute query and store result in a resultset
					ResultSet rs = DBC.con.createStatement().executeQuery(
							"SELECT druzyny.IDDruzyny, druzyny.NazwaD, druzyny.RokZalozenia, druzyny.MiastoD, s.NazwaS, l.NazwaL from druzyny "
									+ "LEFT JOIN stadiony as s on druzyny.IDStadionu = s.IDStadionu "
									+ "LEFT JOIN ligi as l on druzyny.IDLigi = l.IDLigi");
					while (rs.next()) {
						// get string from db,whichever way
						data3.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
								rs.getString(5), rs.getString(6)));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (NullPointerException e) {
			e.printStackTrace();

		}
	}

	@FXML
	public void DoDodawaniaDruzyny() {
		try {
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/ADDTeam.fxml"));
			Parent root2 = (Parent) fxmlLoader2.load();
			ControllerAddTeam CAT = fxmlLoader2.getController();
			CAT.setItems(data3);

			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void WyswietlUzytkownikow() {
		try {

			dataUz.clear();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("SELECT * from uzytkownicy");
			while (rs.next()) {
				// get string from db,whichever way
				String poziom = rs.getString(7);
				if (poziom.equals("1")) {
					poziom = "Uzytkownik";
					// System.out.println(poziom);
					dataUz.add(new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), poziom));
				} else {
					poziom = "Administrator";
					// System.out.println(poziom);
					dataUz.add(new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), poziom));
				}

			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in
		// model class.
		ColumnIDUz.setCellValueFactory(new PropertyValueFactory<>("id"));
		ColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		ColumnPasswordUz.setCellValueFactory(new PropertyValueFactory<>("password"));
		ColumnPoziom.setCellValueFactory(new PropertyValueFactory<>("poziomkonta"));

		TableUzytkownicy.setItems(null);
		TableUzytkownicy.setItems(dataUz);

	}

	public void DoDodaniaUzytkownika(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/ADDUser.fxml"));
			Parent root2 = (Parent) fxmlLoader2.load();
			ControllerAddUser CAU = fxmlLoader2.getController();
			CAU.setItems(dataUz);
			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doEdycjiUzytkownika(ActionEvent event) {

		try {
			Integer id = TableUzytkownicy.getSelectionModel().getSelectedItem().getid();
			String nameUser = TableUzytkownicy.getSelectionModel().getSelectedItem().getName();
			String surnameUser = TableUzytkownicy.getSelectionModel().getSelectedItem().getSurname();
			String LoginUser = TableUzytkownicy.getSelectionModel().getSelectedItem().getusername();
			String emailUser = TableUzytkownicy.getSelectionModel().getSelectedItem().getemail();
			String passwordUser = TableUzytkownicy.getSelectionModel().getSelectedItem().getpassword();
			String accountLevel = TableUzytkownicy.getSelectionModel().getSelectedItem().getpoziomkonta();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditUser.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerEditUser CEU = fxmlLoader.getController();
			CEU.setText(id, nameUser, surnameUser, LoginUser, emailUser, passwordUser, accountLevel);
			CEU.setItems(dataUz);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void UsunUzytkownika() {
		{
			try {
				Integer id = TableUzytkownicy.getSelectionModel().getSelectedItem().getid();
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"Czy na pewno chcesz usun¹æ Uzytkownika o id: " + id + " ?", ButtonType.OK, ButtonType.CANCEL);
				alert.setTitle("Potwierdzenie");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						DBC.st.executeUpdate("Delete from uzytkownicy where id = " + id + ";");
						AlertInformacyjny("Informacja", "Operacja wykonana", "Uda³o siê usun¹æ Uzytkownika");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					dataUz.clear();
					try {
						// Execute query and store result in a resultset
						ResultSet rs = DBC.con.createStatement().executeQuery("SELECT * from uzytkownicy");
						while (rs.next()) {
							// get string from db,whichever way
							String poziom = rs.getString(7);
							if (poziom.equals("1")) {
								poziom = "Uzytkownik";
								// System.out.println(poziom);
								dataUz.add(new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5), rs.getString(6), poziom));
							} else {
								poziom = "Administrator";
								// System.out.println(poziom);
								dataUz.add(new UserDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5), rs.getString(6), poziom));
							}

						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			} catch (NullPointerException e) {
				e.printStackTrace();

			}

		}

	}

	@FXML
	public void WyswietlZaw() {
		{

			try {
				String wybrane2 = teamChoice2.getValue();
				System.out.println(wybrane2);
				Controller contr1 = new Controller();
				System.out.println(przekazanie);
				if (wybrane2 == "Wszystko") {

					data4.clear();
					// Execute query and store result in a resultset
					ResultSet rs = DBC.con.createStatement().executeQuery(
							"SELECT zawodnicy.IDZawodnik, zawodnicy.Imie, zawodnicy.Nazwisko, zawodnicy.DataUrodzenia, zawodnicy.Waga, zawodnicy.Wzrost, zawodnicy.Pozycja, d.NazwaD from zawodnicy LEFT JOIN druzyny as d on d.IDDruzyny = zawodnicy.ID_Druzyny");
					while (rs.next()) {
						// get string from db,whichever way
						data4.add(new PlayerDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
					}
				} else {
					data4.clear();
					// Execute query and store result in a resultset
					ResultSet rs = DBC.con.createStatement().executeQuery(
							"SELECT zawodnicy.IDZawodnik, zawodnicy.Imie, zawodnicy.Nazwisko, zawodnicy.DataUrodzenia, zawodnicy.Waga, zawodnicy.Wzrost, zawodnicy.Pozycja, d.NazwaD, l.NazwaL from zawodnicy LEFT JOIN druzyny as d on d.IDDruzyny = zawodnicy.ID_Druzyny"
									+ " left join ligi as l on l.IDLigi = d.IDLigi where l.NazwaL = '" + wybrane2
									+ "'");
					while (rs.next()) {
						// get string from db,whichever way
						data4.add(new PlayerDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
					}
				}

			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}

			// Set cell value factory to tableview.
			// NB.PropertyValue Factory must be the same with the one set in
			// model class.
			columnIDzaw.setCellValueFactory(new PropertyValueFactory<>("IDZawodnik"));
			columnImie.setCellValueFactory(new PropertyValueFactory<>("Imie"));
			columnNazwisko.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
			columnDataur.setCellValueFactory(new PropertyValueFactory<>("DataUrodzenia"));
			columnWaga.setCellValueFactory(new PropertyValueFactory<>("Waga"));
			columnWzrost.setCellValueFactory(new PropertyValueFactory<>("Wzrost"));
			columnPozycja.setCellValueFactory(new PropertyValueFactory<>("Pozycja"));
			columnDruzyna.setCellValueFactory(new PropertyValueFactory<>("Druzyna"));
			tablePlayers.setItems(null);
			tablePlayers.setItems(data4);

		}

	}

	public void OdswiezZaw() {

	}

	public void UsunZawodnika(ActionEvent event) {
		try {
			Integer id = tablePlayers.getSelectionModel().getSelectedItem().getIDZawodnik();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz usun¹æ zawodnika o id: " + id + " ?",
					ButtonType.OK, ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					DBC.st.executeUpdate("Delete from zawodnicy where IDZawodnik = " + id + ";");
					AlertInformacyjny("Informacja", "Operacja wykonana", "Uda³o siê usun¹æ Zawodnika");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				data4.clear();
				WyswietlZaw();

			}
		} catch (NullPointerException e) {
			e.printStackTrace();

		}

	}

	public void DoDodawaniaZawodnika(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/Addplayer.fxml"));
			Parent root2 = (Parent) fxmlLoader2.load();
			ControllerAddPlayer CAP = fxmlLoader2.getController();
			CAP.setItems(data4);
			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doEdycjizawodnika(ActionEvent event) {
		// PlayerDetails aaa =
		// tablePlayers.getSelectionModel().getSelectedItem();
		// System.out.println(aaa.getImie() + ' ' + aaa.getNazwisko());

		// System.out.println(wybranygracz.getImie());

		try {
			String imie = tablePlayers.getSelectionModel().getSelectedItem().getImie();
			String nazwisko = tablePlayers.getSelectionModel().getSelectedItem().getNazwisko();
			String Dataurodzenia = tablePlayers.getSelectionModel().getSelectedItem().getDataUrodzenia();
			int waga = tablePlayers.getSelectionModel().getSelectedItem().getWaga();
			int wzrost = tablePlayers.getSelectionModel().getSelectedItem().getWzrost();
			String pozycja = tablePlayers.getSelectionModel().getSelectedItem().getPozycja();
			int id = tablePlayers.getSelectionModel().getSelectedItem().getIDZawodnik();
			String druzyna = tablePlayers.getSelectionModel().getSelectedItem().getDruzyna();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editplayer.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerEditPlayer CEP = fxmlLoader.getController();
			CEP.setText(imie, nazwisko, Dataurodzenia, waga, wzrost, pozycja, id, druzyna);
			CEP.setItems(data4);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void EditTeam() {

		try {
			String nazwa = tableTeams.getSelectionModel().getSelectedItem().getNazwaD();
			int RokZalozenia = tableTeams.getSelectionModel().getSelectedItem().getRokZalozenia();
			String Miasto = tableTeams.getSelectionModel().getSelectedItem().getMiastoD();
			// int waga =
			// tablePlayers.getSelectionModel().getSelectedItem().getWaga();
			/* TYMCZASOWO - TUTAJ BÊDZIE WYBÓR Z LISTY */
			String Stadion = tableTeams.getSelectionModel().getSelectedItem().getIDStadionu();
			String Liga = tableTeams.getSelectionModel().getSelectedItem().getLiga();
			int id = tableTeams.getSelectionModel().getSelectedItem().getIDDruzyny();
			// String druzyna =
			// tablePlayers.getSelectionModel().getSelectedItem().getDruzyna();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditTeam.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerEditTeam CET = fxmlLoader.getController();
			CET.setItems(data3);
			CET.setText(nazwa, RokZalozenia, Miasto, Stadion, Liga, id);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (NullPointerException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void WyswietlObiekty(ActionEvent event) {
		try {

			dataSt.clear();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery("SELECT * from stadiony");
			while (rs.next()) {
				// get string from db,whichever way
				dataSt.add(
						new ObjectDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in model
		// class.
		columnIDstad.setCellValueFactory(new PropertyValueFactory<>("IDStadionu"));
		columnNazwaS.setCellValueFactory(new PropertyValueFactory<>("NazwaS"));
		columnMiastoS.setCellValueFactory(new PropertyValueFactory<>("MiastoS"));
		columnRokBudS.setCellValueFactory(new PropertyValueFactory<>("RokBudowyS"));
		columnPojemnoscS.setCellValueFactory(new PropertyValueFactory<>("PojemnoscS"));
		tableObjects.setItems(null);
		tableObjects.setItems(dataSt);
	}

	public void doEdycjiObiektu(ActionEvent event) {
		try {
			String NazwaObiektu = tableObjects.getSelectionModel().getSelectedItem().getNazwaS();
			String MiastoObiektu = tableObjects.getSelectionModel().getSelectedItem().getMiastoS();
			int RokBudowyObiektu = tableObjects.getSelectionModel().getSelectedItem().getRokBudowyS();
			int PojemnoscObiektu = tableObjects.getSelectionModel().getSelectedItem().getPojemnoscS();
			int idobiektu = tableObjects.getSelectionModel().getSelectedItem().getIDStadionu();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditObject.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerEditObject CEO = fxmlLoader.getController();
			CEO.setText(idobiektu, NazwaObiektu, MiastoObiektu, RokBudowyObiektu, PojemnoscObiektu);
			CEO.setItems(dataSt);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void DoDodawaniaObiektu(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/AddObject.fxml"));
			Parent root2 = (Parent) fxmlLoader2.load();
			ControllerAddObject CAO = fxmlLoader2.getController();
			CAO.setItems(dataSt);
			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void WyswietlLigi(ActionEvent event) {
		try {
			String wybrane3 = LigiSportyCB.getValue();
			System.out.println(wybrane3);
			System.out.println(przekazanie);
			if (wybrane3 == "Wszystko") {

				dataL.clear();
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT ligi.IDLigi,ligi.NazwaL,ligi.obszar,ligi.PoziomL,ligi.MaksymalnaLiczbaDruzyn,s.Rok_Rozpoczecia,s.Rok_Zakonczenia, d.Nazwa FROM ligi left join sezony as s on s.id_Sezon = ligi.id_Sezon join dyscypliny as d on d.IDDyscyplina = ligi.IDDyscyplina ");
				while (rs.next()) {
					// get string from db,whichever way
					dataL.add(new LeagueDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8)));
				}
			} else {

				dataL.clear();
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT ligi.IDLigi,ligi.NazwaL,ligi.obszar,ligi.PoziomL,ligi.MaksymalnaLiczbaDruzyn,s.Rok_Rozpoczecia,s.Rok_Zakonczenia, d.Nazwa FROM ligi left join sezony as s on s.id_Sezon = ligi.id_Sezon join dyscypliny as d on d.IDDyscyplina = ligi.IDDyscyplina where d.Nazwa = '"
								+ wybrane3 + "'");
				while (rs.next()) {
					// get string from db,whichever way
					dataL.add(new LeagueDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8)));

				}

			}
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in model
		// class.
		columnIDLigi.setCellValueFactory(new PropertyValueFactory<>("IDLigi"));
		columnNazwaL.setCellValueFactory(new PropertyValueFactory<>("NazwaL"));
		columnObszarL.setCellValueFactory(new PropertyValueFactory<>("Obszar"));
		columnPoziomL.setCellValueFactory(new PropertyValueFactory<>("PoziomL"));
		columnMaxD.setCellValueFactory(new PropertyValueFactory<>("MaksymalnaLiczbaDruzyn"));
		columnRokRoz.setCellValueFactory(new PropertyValueFactory<>("RokRozpoczecia"));
		columnRokZak.setCellValueFactory(new PropertyValueFactory<>("RokZakonczenia"));
		columnDyscyplina.setCellValueFactory(new PropertyValueFactory<>("Dyscyplina"));

		tableLeagues.setItems(null);
		tableLeagues.setItems(dataL);
	}

	public void doEdycjiLigi(ActionEvent event) {
		try {
			String NazwaL = tableLeagues.getSelectionModel().getSelectedItem().getNazwaL();
			String ObszarL = tableLeagues.getSelectionModel().getSelectedItem().getObszar();
			int PoziomL = tableLeagues.getSelectionModel().getSelectedItem().getPoziomL();
			int LimitDruzyn = tableLeagues.getSelectionModel().getSelectedItem().getMaksymalnaLiczbaDruzyn();
			int RokRoz = tableLeagues.getSelectionModel().getSelectedItem().getRokRozpoczecia();
			int RokZak = tableLeagues.getSelectionModel().getSelectedItem().getRokZakonczenia();
			int id = tableLeagues.getSelectionModel().getSelectedItem().getIDLigi();
			String Dyscyplina = tableLeagues.getSelectionModel().getSelectedItem().getDyscyplina();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditLeague.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerEditLeague CEL = fxmlLoader.getController();
			CEL.setItems(dataL);
			CEL.setText(id, NazwaL, ObszarL, PoziomL, LimitDruzyn, RokRoz, RokZak, Dyscyplina);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void DoDodawaniaLigi(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/AddLeague.fxml"));
			Parent root2 = (Parent) fxmlLoader2.load();
			ControllerAddLeague CAL = fxmlLoader2.getController();
			CAL.setItems(dataL);
			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPaneluLigowego(ActionEvent event) {
		try {

			String NazwaL = tableLeagues.getSelectionModel().getSelectedItem().getNazwaL();
			String ObszarL = tableLeagues.getSelectionModel().getSelectedItem().getObszar();
			String Dyscyplina = tableLeagues.getSelectionModel().getSelectedItem().getDyscyplina();
			int PoziomL = tableLeagues.getSelectionModel().getSelectedItem().getPoziomL();
			int LimitDruzyn = tableLeagues.getSelectionModel().getSelectedItem().getMaksymalnaLiczbaDruzyn();
			int RokRoz = tableLeagues.getSelectionModel().getSelectedItem().getRokRozpoczecia();
			int RokZak = tableLeagues.getSelectionModel().getSelectedItem().getRokZakonczenia();
			int id = tableLeagues.getSelectionModel().getSelectedItem().getIDLigi();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LeaguePanel.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerLeaguePanel CELP = fxmlLoader.getController();
			CELP.setparams(id, Dyscyplina, NazwaL);

			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			stage.setAlwaysOnTop(true);

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void WyswietlWiadomosci(ActionEvent event) {
		try {

			dataWiad.clear();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"Select w.id_wiadomosc, u.username, w.Tresc, w.Zwrot from wiadomosci_przychodzace w join uzytkownicy as u on u.id = w.id_autora");
			while (rs.next()) {
				// get string from db,whichever way
				dataWiad.add(
						new IncomingMessageDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));

			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in model
		// class.
		ColumnIDWiadomosci.setCellValueFactory(new PropertyValueFactory<>("idwiadomosci"));
		ColumnAutorW.setCellValueFactory(new PropertyValueFactory<>("autor"));
		ColumnTrescW.setCellValueFactory(new PropertyValueFactory<>("tresc"));
		ColumnZwrotW.setCellValueFactory(new PropertyValueFactory<>("zwrot"));
		TableWiadomosci.setItems(null);
		TableWiadomosci.setItems(dataWiad);
	}

	public void UsunWiadomosc(ActionEvent event) {

		Integer idwiadomosc = TableWiadomosci.getSelectionModel().getSelectedItem().getidwiadomosci();
		String Zwrot = TableWiadomosci.getSelectionModel().getSelectedItem().getzwrot();
		if (Zwrot.equals("Tak")) {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Czy na pewno chcesz usun¹æ wiadomoœæ o id: " + idwiadomosc + "?", ButtonType.OK,
					ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				try {
					DBC.con.createStatement().executeUpdate(
							"DELETE FROM `wiadomosci_przychodzace` WHERE id_wiadomosc = " + idwiadomosc + "");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dataWiad.clear();
				try {

					// Execute query and store result in a resultset

					ResultSet rs = DBC.con.createStatement().executeQuery(
							"Select w.id_wiadomosc, u.username, w.Tresc, w.Zwrot from wiadomosci_przychodzace w join uzytkownicy as u on u.id = w.id_autora");
					while (rs.next()) {
						// get string from db,whichever way
						dataWiad.add(new IncomingMessageDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getString(4)));

					}

				} catch (SQLException ex) {
					System.err.println("Error" + ex);
				}
			}
		} else {
			AlertInformacyjny("B³¹d", "Brak odpowiedzi", "Nie mo¿esz usun¹æ wiadomoœci na któr¹ nie odpowiedzia³eœ.");

		}
	}

	public void Odpowiedznawiadomosc(ActionEvent event) {
		try {
			Integer idwiadomosc = TableWiadomosci.getSelectionModel().getSelectedItem().getidwiadomosci();
			String autor = TableWiadomosci.getSelectionModel().getSelectedItem().getautor();
			String tresc = TableWiadomosci.getSelectionModel().getSelectedItem().gettresc();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MessagePanel.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerMessage CM = fxmlLoader.getController();
			// CM.setItems(dataL);
			CM.setText(idwiadomosc, autor, tresc, idadmina);
			CM.setItems(dataWiadW);
			CM.setItems2(dataWiad);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			stage.setAlwaysOnTop(true);

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void WyswietlWiadomosciWychodzace(ActionEvent event) {
		try {

			dataWiadW.clear();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"SELECT ww.id, uau.username, uad.username, ww.Tresc from wiadomosci_wychodzace ww join uzytkownicy as uau on "
							+ "uau.id = ww.id_autora join uzytkownicy as uad on uad.id = ww.id_adresata");
			while (rs.next()) {
				// get string from db,whichever way
				dataWiadW.add(
						new OutgoingMessageDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));

			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in model
		// class.
		ColumnIDWiadomosciW.setCellValueFactory(new PropertyValueFactory<>("idwiadomosci"));
		ColumnAutorWW.setCellValueFactory(new PropertyValueFactory<>("autor"));
		ColumnAdresatWW.setCellValueFactory(new PropertyValueFactory<>("adresat"));
		ColumnTrescWW.setCellValueFactory(new PropertyValueFactory<>("tresc"));
		TableWiadomosciWych.setItems(null);
		TableWiadomosciWych.setItems(dataWiadW);
	}

	public void UsunWiadomoscWychodzaca(ActionEvent event) {

		int id = TableWiadomosciWych.getSelectionModel().getSelectedItem().getidwiadomosci();
		Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz usun¹æ wiadomoœæ o id: " + id + "?",
				ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Potwierdzenie");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
				DBC.con.createStatement().executeUpdate(
						"DELETE FROM `wiadomosci_wychodzace` WHERE `wiadomosci_wychodzace`.`id` = " + id + "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dataWiadW.clear();
			try {

				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT ww.id, uau.username, uad.username, ww.Tresc from wiadomosci_wychodzace ww join uzytkownicy as uau on "
								+ "uau.id = ww.id_autora join uzytkownicy as uad on uad.id = ww.id_adresata");
				while (rs.next()) {
					// get string from db,whichever way
					dataWiadW.add(new OutgoingMessageDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getString(4)));

				}

			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}

			// Set cell value factory to tableview.
			// NB.PropertyValue Factory must be the same with the one set in
			// model
			// class.
			ColumnIDWiadomosciW.setCellValueFactory(new PropertyValueFactory<>("idwiadomosci"));
			ColumnAutorWW.setCellValueFactory(new PropertyValueFactory<>("autor"));
			ColumnAdresatWW.setCellValueFactory(new PropertyValueFactory<>("adresat"));
			ColumnTrescWW.setCellValueFactory(new PropertyValueFactory<>("tresc"));
			TableWiadomosciWych.setItems(null);
			TableWiadomosciWych.setItems(dataWiadW);
		}

	}

	public void doTworzeniaWiadomosci(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SendMessagePanel.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerSendMessage CSM = fxmlLoader.getController();
			// CM.setItems(dataL);
			CSM.setItems(dataWiadW);
			CSM.setidadministrator(idadmina);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			stage.setAlwaysOnTop(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void WyswietlZgloszeniaDruzyn(ActionEvent event) {
		{
			try {

				dataZgloszeniaDruzyn.clear();
				// Execute query and store result in a resultset
				ResultSet rs = DBC.con.createStatement().executeQuery(
						"SELECT zd.idZgloszeniaDruzyny,zd.NazwaDruzyny,zd.RokZalozenia,zd.Miasto,s.NazwaS,l.NazwaL FROM "
								+ "zgloszenia_druzyn zd join stadiony as s on s.IDStadionu = zd.Obiekt join ligi as l on l.IDLigi = zd.Liga");
				while (rs.next()) {
					// get string from db,whichever way
					dataZgloszeniaDruzyn.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3),
							rs.getString(4), rs.getString(5), rs.getString(6)));

				}

			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}

			// Set cell value factory to tableview.
			// NB.PropertyValue Factory must be the same with the one set in
			// model
			// class.
			ColIDZgloszeniaDr.setCellValueFactory(new PropertyValueFactory<>("IDDruzyny"));
			ColNazwaZglDruzyny.setCellValueFactory(new PropertyValueFactory<>("NazwaD"));
			ColRokZalZglDruzyny.setCellValueFactory(new PropertyValueFactory<>("RokZalozenia"));
			ColMiastoZglDruzyny.setCellValueFactory(new PropertyValueFactory<>("MiastoD"));
			ColObiektZglDruzyny.setCellValueFactory(new PropertyValueFactory<>("IDStadionu"));
			ColLigaZglDruzyny.setCellValueFactory(new PropertyValueFactory<>("Liga"));
			TableZgloszeniaDruzyn.setItems(null);
			TableZgloszeniaDruzyn.setItems(dataZgloszeniaDruzyn);
		}
	}

	public void OdrzucZgloszenieDruzyny(ActionEvent event) {
		String nazwauzytkownika = null;
		try {
			Integer id = TableZgloszeniaDruzyn.getSelectionModel().getSelectedItem().getIDDruzyny();
			ResultSet rs = DBC.con.createStatement().executeQuery("Select u.username from uzytkownicy u where "
					+ "u.id = (Select Zglaszajacy from zgloszenia_druzyn where idZgloszeniaDruzyny= " + id + " ) ");
			if (rs.next()) {
				nazwauzytkownika = rs.getString(1);
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SendMessagePanel.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			ControllerSendMessage CSM = fxmlLoader.getController();
			// CM.setItems(dataL);
			CSM.setItems(dataWiadW);
			CSM.setItems2(dataZgloszeniaDruzyn);
			CSM.setidzgloszenia(id);
			CSM.setidadministrator(idadmina);
			CSM.WiadomoscZwrotnanazgloszenieDruzyny(nazwauzytkownika);
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			stage.setAlwaysOnTop(true);

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void PrzyjmijZgloszenieDruzyny(ActionEvent event) {
		boolean dostepnemiejsce = true;
		boolean liganierozpoczeta = true;
		boolean dostepnanazwadruzyny = true;
		try {
			int idligi = 0;
			int iloscdruzynwlidze = 0;
			int maksymalnailoscdruzynwlidze = 0;

			Integer id = TableZgloszeniaDruzyn.getSelectionModel().getSelectedItem().getIDDruzyny();
			String nazwaDruzyny = TableZgloszeniaDruzyn.getSelectionModel().getSelectedItem().getNazwaD();
			Integer rok = TableZgloszeniaDruzyn.getSelectionModel().getSelectedItem().getRokZalozenia();
			String miasto = TableZgloszeniaDruzyn.getSelectionModel().getSelectedItem().getMiastoD();

			try {

				Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz przyj¹æ zg³oszenie  ?",
						ButtonType.OK, ButtonType.CANCEL);
				alert.setTitle("Potwierdzenie");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					ResultSet rs = DBC.con.createStatement().executeQuery(
							"Select Liga from zgloszenia_druzyn where" + " idZgloszeniaDruzyny = " + id + "");
					if (rs.next()) {
						idligi = rs.getInt(1);
					}
					rs = DBC.con.createStatement().executeQuery("Select * from mecze where idligi = " + idligi + "");
					if (rs.next()) {
						liganierozpoczeta = false;
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("B³¹d");
						alert2.setHeaderText("B³¹d");
						alert2.setContentText(
								"Nie mo¿esz dodaæ dru¿yny do ligi poniewa¿ jest ona ju¿ rozpoczêta lub ma ju¿ zatwierdzony terminarz. Powinieneœ odrzuciæ te zg³oszenie.");
						alert2.showAndWait();
					} else {
						liganierozpoczeta = true;
					}
					rs = DBC.con.createStatement()
							.executeQuery("Select count(iddruzyny) from druzyny where idligi = " + idligi + "");
					if (rs.next()) {
						iloscdruzynwlidze = rs.getInt(1);
					}
					System.out.println(iloscdruzynwlidze);
					rs = DBC.con.createStatement()
							.executeQuery("Select maksymalnaliczbadruzyn from ligi where idligi = " + idligi + "");
					if (rs.next()) {
						maksymalnailoscdruzynwlidze = rs.getInt(1);
					}
					if (iloscdruzynwlidze < maksymalnailoscdruzynwlidze) {
						dostepnemiejsce = true;
					} else {
						dostepnemiejsce = false;
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("B³¹d");
						alert2.setHeaderText("B³¹d");
						alert2.setContentText(
								"Ta liga ma ju¿ maksymaln¹ iloœæ dru¿yn. Powinieneœ odrzuciæ te zg³oszenie.");
						alert2.showAndWait();
					}
					rs = DBC.con.createStatement()
							.executeQuery("Select maksymalnaliczbadruzyn from ligi where idligi = " + idligi + "");
					if (rs.next()) {
						maksymalnailoscdruzynwlidze = rs.getInt(1);
					}
					if (iloscdruzynwlidze < maksymalnailoscdruzynwlidze) {
						dostepnemiejsce = true;
					} else {
						dostepnemiejsce = false;
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("B³¹d");
						alert2.setHeaderText("B³¹d");
						alert2.setContentText(
								"Ta liga ma ju¿ maksymaln¹ iloœæ dru¿yn. Powinieneœ odrzuciæ te zg³oszenie.");
						alert2.showAndWait();
					}
					rs = DBC.con.createStatement().executeQuery(
							"SELECT * FROM Druzyny WHERE idligi = " + idligi + " and NazwaD = '" + nazwaDruzyny + "'");
					if (rs.next()) {
						dostepnanazwadruzyny = false;
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("B³¹d");
						alert2.setHeaderText("B³¹d");
						alert2.setContentText(
								"W tej lidze istnieje ju¿ dru¿yna o takiej nazwie. Powinieneœ odrzuciæ te zg³oszenie.");
						alert2.showAndWait();
					} else {
						dostepnanazwadruzyny = true;
					}

					if (liganierozpoczeta == true && dostepnemiejsce == true && dostepnanazwadruzyny == true) {
						DBC.st.executeUpdate(
								"INSERT into druzyny (NazwaD, Rokzalozenia, MiastoD, IDStadionu, IDLigi) VALUES ('"
										+ nazwaDruzyny + "', " + rok + ", " + "'" + miasto
										+ "', (Select Obiekt from zgloszenia_druzyn where idZgloszeniaDruzyny = " + id
										+ ")," + " (Select Liga from zgloszenia_druzyn where idZgloszeniaDruzyny = "
										+ id + "))");
						DBC.st.executeUpdate(
								"INSERT into wiadomosci_wychodzace (id_autora, id_adresata, Tresc) VALUES (" + idadmina
										+ ", (Select Zglaszajacy from zgloszenia_druzyn where idZgloszeniaDruzyny = "
										+ id + "), " + "'Zg³oszenie druzyny " + nazwaDruzyny + " do ligi"
										+ " Zosta³o przyjête')");
						DBC.st.executeUpdate("DELETE FROM zgloszenia_druzyn where idZgloszeniaDruzyny = " + id + " ");

						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Operacja wykonana");
						alert2.setHeaderText("Uda³o siê");
						alert2.setContentText("Wygl¹da na to ¿e liga jest dostêpna. Dru¿yna zosta³a dodana");
						alert2.showAndWait();
						dataZgloszeniaDruzyn.clear();
						rs = DBC.con.createStatement().executeQuery(
								"SELECT zd.idZgloszeniaDruzyny,zd.NazwaDruzyny,zd.RokZalozenia,zd.Miasto,s.NazwaS,l.NazwaL FROM "
										+ "zgloszenia_druzyn zd join stadiony as s on s.IDStadionu = zd.Obiekt join ligi as l on l.IDLigi = zd.Liga");
						while (rs.next()) {
							// get string from db,whichever way
							dataZgloszeniaDruzyn.add(new TeamDetails(rs.getInt(1), rs.getString(2), rs.getInt(3),
									rs.getString(4), rs.getString(5), rs.getString(6)));

						}
					} else {

					}
					System.out.println(idligi);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		}
	}

	public void WyswietlZgloszeniaZawodnikow(ActionEvent event) {
		try {

			dataZgloszeniaZawodnikow.clear();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement()
					.executeQuery("Select zz.idZgloszeniaZawodnika, zz.Imie, zz.Nazwisko, zz.DataUrodzenia, "
							+ "zz.Waga,zz.Wzrost,zz.Pozycja, d.NazwaD from zgloszenia_zawodnikow as zz join druzyny as d on d.IDDruzyny = zz.Druzyna");
			while (rs.next()) {
				// get string from db,whichever way
				dataZgloszeniaZawodnikow.add(new PlayerDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));

			}

		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}

		// Set cell value factory to tableview.
		// NB.PropertyValue Factory must be the same with the one set in
		// model
		// class.
		ColIDZgloszeniaZaw.setCellValueFactory(new PropertyValueFactory<>("IDZawodnik"));
		ColImieZglZaw.setCellValueFactory(new PropertyValueFactory<>("Imie"));
		ColNazwZglZaw.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
		ColDataUrZglZaw.setCellValueFactory(new PropertyValueFactory<>("DataUrodzenia"));
		ColWagaZglZaw.setCellValueFactory(new PropertyValueFactory<>("Waga"));
		ColWzrZglZaw.setCellValueFactory(new PropertyValueFactory<>("Wzrost"));
		ColPozZglZaw.setCellValueFactory(new PropertyValueFactory<>("Pozycja"));
		ColDrZglZaw.setCellValueFactory(new PropertyValueFactory<>("Druzyna"));
		TableZgloszeniaZawodnikow.setItems(null);
		TableZgloszeniaZawodnikow.setItems(dataZgloszeniaZawodnikow);
	}

	public void OdrzucZgloszenieZawodnika(ActionEvent event) {
		{
			String nazwauzytkownika = null;
			try {
				Integer id = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getIDZawodnik();
				ResultSet rs = DBC.con.createStatement()
						.executeQuery("Select u.username from uzytkownicy u where "
								+ "u.id = (Select Zglaszajacy from zgloszenia_zawodnikow where idZgloszeniaZawodnika= "
								+ id + " ) ");
				if (rs.next()) {
					nazwauzytkownika = rs.getString(1);
				}
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SendMessagePanel.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				ControllerSendMessage CSM = fxmlLoader.getController();
				// CM.setItems(dataL);
				CSM.setItems(dataWiadW);
				CSM.setItems3(dataZgloszeniaZawodnikow);
				CSM.setidzgloszenia(id);
				CSM.setidadministrator(idadmina);
				CSM.WiadomoscZwrotnanazgloszenieZawodnika(nazwauzytkownika);
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
				stage.setAlwaysOnTop(true);

			} catch (NullPointerException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("B³¹d");
				alert.setHeaderText("Brak zaznaczenia");
				alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
				alert.showAndWait();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void PrzyjmijZgloszenieZawodnika(ActionEvent event) {
		boolean wiekok = false;
		try {
			int idligi = 0;
			int wyliczonywiek = 0;
			Integer id = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getIDZawodnik();
			String Imie = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getImie();
			String Nazwisko = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getNazwisko();
			String dataurodzenia = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getDataUrodzenia();
			Integer Waga = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getWaga();
			Integer wzrost = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getWzrost();
			String pozycja = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getPozycja();
			String druzyna = TableZgloszeniaZawodnikow.getSelectionModel().getSelectedItem().getDruzyna();
			try {

				Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz przyj¹æ zg³oszenie  ?",
						ButtonType.OK, ButtonType.CANCEL);
				alert.setTitle("Potwierdzenie");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {

					DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
					Date date;
					try {
						date = format.parse(dataurodzenia);
						System.out.println(date);
						wyliczonywiek = obliczwiek(date);
						System.out.println(wyliczonywiek);

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (wyliczonywiek >= 16) {
						wiekok = true;
					} else {
						wiekok = false;
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("B³¹d");
						alert2.setHeaderText("B³¹d");
						alert2.setContentText(
								"Wygl¹da na to ¿e zg³oszony zawodnik ma poni¿ej 16 lat. Powinieneœ odrzuciæ to zg³oszenie");
						alert2.showAndWait();

					}
					if (wiekok == true) {
						DBC.st.executeUpdate(
								"INSERT into zawodnicy (Imie, Nazwisko, DataUrodzenia, Waga, Wzrost,Pozycja,ID_Druzyny) VALUES ('"
										+ Imie + "', '" + Nazwisko + "', " + "'" + dataurodzenia + "'," + Waga + ","
										+ wzrost + ", '" + pozycja
										+ "', (Select Druzyna from zgloszenia_zawodnikow where idZgloszeniaZawodnika = "
										+ id + "))");
						DBC.st.executeUpdate(
								"INSERT into wiadomosci_wychodzace (id_autora, id_adresata, Tresc) VALUES (" + idadmina
										+ ", (Select Zglaszajacy from zgloszenia_zawodnikow where idZgloszeniaZawodnika = "
										+ id + "), " + "'Zg³oszenie zawodnika " + Imie + ' ' + Nazwisko
										+ " do druzyny  " + druzyna + " Zosta³o przyjête')");
						DBC.st.executeUpdate(
								"DELETE FROM zgloszenia_zawodnikow where idZgloszeniaZawodnika = " + id + " ");
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Operacja wykonana");
						alert2.setHeaderText("Uda³o siê");
						alert2.setContentText(
								"Wygl¹da na to ¿e Zawodnik mo¿e zostaæ zg³oszony. Zawodnik zosta³ dodany");
						alert2.showAndWait();
						dataZgloszeniaZawodnikow.clear();
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"Select zz.idZgloszeniaZawodnika, zz.Imie, zz.Nazwisko, zz.DataUrodzenia, "
										+ "zz.Waga,zz.Wzrost,zz.Pozycja, d.NazwaD from zgloszenia_zawodnikow as zz join druzyny as d on d.IDDruzyny = zz.Druzyna");
						while (rs.next()) {
							// get string from db,whichever way
							dataZgloszeniaZawodnikow.add(
									new PlayerDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
											rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
						}
					} else {

					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B³¹d");
			alert.setHeaderText("Brak zaznaczenia");
			alert.setContentText("NIe zaznaczy³eœ ¿adnego rekordu z tabeli");
			alert.showAndWait();

		}
	}

	@FXML
	private void UsunObiekt(ActionEvent event) {
		try {
			Integer id = tableObjects.getSelectionModel().getSelectedItem().getIDStadionu();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz usun¹æ obiekt o id: " + id + " ?",
					ButtonType.OK, ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					DBC.st.executeUpdate("Delete from stadiony where IDStadionu = " + id + ";");
					AlertInformacyjny("Informacja", "Operacja wykonana", "Uda³o siê usun¹æ obiekt");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dataSt.clear();
				try {
					ResultSet rs = DBC.con.createStatement()
							.executeQuery("Select IDStadionu, NazwaS, MiastoS,RokBudowyS,PojemnoscS from stadiony");
					while (rs.next()) {
						dataSt.add(new ObjectDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getInt(5)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (NullPointerException e) {
			e.printStackTrace();

		}

	}

	@FXML
	private void UsunLige(ActionEvent event) {
		boolean ligarozpoczeta = false;

		try {
			Integer id = tableLeagues.getSelectionModel().getSelectedItem().getIDLigi();
			ResultSet rs = DBC.con.createStatement().executeQuery("Select * from mecze where idligi =" + id);
			if (rs.next()) {
				ligarozpoczeta = true;

			}
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Czy na pewno chcesz usun¹æ ligê o id: " + id + " wraz ze wszystkimi odwo³aniami do niej??",
					ButtonType.OK, ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				if (ligarozpoczeta == true) {
					Alert alert2 = new Alert(AlertType.CONFIRMATION);
					alert2.setTitle("Potwierdzenie");
					alert2.setHeaderText("Liga w trakcie");
					alert2.setContentText(
							"Wybrana liga jest ju¿ w trakcie lub posiada zatwierdzony terminarz. Usuniêcie ligi usunie tak¿e terminarz wraz z ju¿ rozegranymi meczami. Czy na pewno chcesz usun¹æ?");

					ButtonType buttonTypeOne = new ButtonType("Tak");
					ButtonType buttonTypeCancel = new ButtonType("Wróæ", ButtonData.CANCEL_CLOSE);

					alert2.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
					Optional<ButtonType> result2 = alert2.showAndWait();
					if (result2.get() == buttonTypeOne) {
						DBC.st.executeUpdate("Delete from mecze where idligi = " + id);
						DBC.st.executeUpdate("Delete from ligi where idligi = " + id);
					} else {

					}
				} else {
					DBC.st.executeUpdate("Delete from ligi where idligi = " + id);
				}
				dataL.clear();
				rs = DBC.con.createStatement().executeQuery(
						"SELECT ligi.IDLigi,ligi.NazwaL,ligi.obszar,ligi.PoziomL,ligi.MaksymalnaLiczbaDruzyn,s.Rok_Rozpoczecia,s.Rok_Zakonczenia, d.Nazwa FROM ligi left join sezony as s on s.id_Sezon = ligi.id_Sezon join dyscypliny as d on d.IDDyscyplina = ligi.IDDyscyplina");
				while (rs.next()) {
					// get string from db,whichever way
					dataL.add(new LeagueDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8)));

				}
			} else {

			}
		} catch (NullPointerException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int obliczwiek(Date dataurodzenia) {
		Calendar dataurodzeniagracza = Calendar.getInstance();
		dataurodzeniagracza.setTime(dataurodzenia);
		Calendar obecnadata = Calendar.getInstance();
		obecnadata.setTime(new Date());
		return obecnadata.get(Calendar.YEAR) - dataurodzeniagracza.get(Calendar.YEAR);
	}

	public TableView<TeamDetails> getTabelaDruzyny() {
		return tableTeams;
	}

	public TableView<PlayerDetails> getTabelaZawodnicy() {
		return tablePlayers;
	}

	public TableView<ObjectDetails> getTabelaObiekty() {
		return tableObjects;
	}

	public TableView<LeagueDetails> getTabelaLigi() {
		return tableLeagues;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void AlertInformacyjny(String title, String HeaderText, String Contenttext) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(HeaderText);
		alert.setContentText(Contenttext);
		alert.showAndWait();
	}

}
