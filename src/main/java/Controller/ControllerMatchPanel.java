package Controller;

import java.io.IOException;
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.GoalScorersDetails;
import model.ScheduleDetails;
import model.TeamStatDetails;

public class ControllerMatchPanel {
	DBconnect DBC = new DBconnect();
	private MainController mainController;

	@FXML
	private Pane pejn;
	@FXML
	private Label Gosplbl;

	@FXML
	private Label Gosclbl;

	@FXML
	private Label kolejka;
	@FXML
	private Label TrybLBL;

	@FXML
	private Button TrybPodglBTN;
	@FXML
	private VBox Bramkigoscivbox;
	@FXML
	private VBox BramkiGosciVbox2;
	@FXML
	private VBox BramkiGospVbox2;
	@FXML
	private VBox Bramkigospvbox;
	@FXML
	private Button TrybEdycjiBTN;
	@FXML
	private Label WynikGospodarzyLBL;
	@FXML
	private Label WynikGosciLBL;
	@FXML
	private ChoiceBox<Integer> ChoiceGoleGosp;

	@FXML
	private ChoiceBox<Integer> ChoiceGoleGosc;
	@FXML
	private Button ZatwierdzWynikBTN;

	@FXML
	private Label MeczSieNieOdbyl;
	@FXML
	private ListView<String> StrzelcyGoscLV;

	@FXML
	private ListView<String> StrzelcyGospLV;
	@FXML
	private Label lblData;

	@FXML
	private Label lblMiejsce;
	@FXML
	private Label LabelNajlepsiGospodarze;
	@FXML
	private Label LabelNajlepsiGoscie;
	@FXML
	private Label lblGodzina;
	// public ControllerLeaguePanel clp2 = new ControllerLeaguePanel();
	public int numerkolejki;
	public int idligi2;
	public int numermeczu;
	private final Group groupGospodarze = new Group();

	private final Group groupGoscie = new Group();
	public ObservableList<Integer> GoledoChoice = FXCollections.observableArrayList();

	public ObservableList<String> ZawodnicyGospodarz = FXCollections.observableArrayList();
	public ObservableList<String> ZawodnicyGosci = FXCollections.observableArrayList();
	public ObservableList<String> StrzelcyBramekDlaGospodarzy = FXCollections.observableArrayList();
	public ObservableList<String> StrzelcyBramekDlaGosci = FXCollections.observableArrayList();
	boolean gospodarzeWolnyLos = false;
	boolean goscieWolnyLos = false;
	String dyscyplina;
	int idmeczu = 0;
	int ilosczawodnikowgospodarzy;
	int ilosczawodnikowgosci;

	public void initialize() {

		/*
		 * for (int i = 0; i <= 20; i++) {
		 * 
		 * GoledoChoice.add(i);
		 * 
		 * }
		 * 
		 * ChoiceGoleGosc.setItems(null); ChoiceGoleGosc.setItems(GoledoChoice);
		 * ChoiceGoleGosp.setItems(null); ChoiceGoleGosp.setItems(GoledoChoice);
		 */
		ChoiceGoleGosp.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Bramkigospvbox.setVisible(true);
				Bramkigospvbox.getChildren().clear();
				BramkiGospVbox2.getChildren().clear();
				// System.out.println(ChoiceGoleGosp.getItems().get((Integer)
				// newValue));
				System.out.println(dyscyplina);
				if (dyscyplina.equals("Siatkówka") || dyscyplina.equals("Koszykówka")) {
					try {
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"Select Count(*) from  zawodnicy where ID_Druzyny = (select druzyna1 from mecze where idmeczu = "
										+ idmeczu + ")");
						if (rs.next()) {
							ilosczawodnikowgospodarzy = rs.getInt(1);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if ((Integer) newValue == 3) {
						ObservableList<Integer> Sety = FXCollections.observableArrayList();
						Sety.addAll(0, 1, 2);
						System.out.println(Sety);
						ChoiceGoleGosc.setItems(Sety);
						ChoiceGoleGosc.setValue(0);
					} else {
						Integer starawartosc = ChoiceGoleGosc.getValue();
						ChoiceGoleGosc.setItems(GoledoChoice);
						ChoiceGoleGosc.setValue(starawartosc);
					}

					for (int xx = 0; xx < ilosczawodnikowgospodarzy; xx++) {

						ChoiceBox<String> cb = new ChoiceBox<>();
						TextField tf = new TextField();
						tf.setId("tf" + xx);
						System.out.println(tf.getId());
						cb.setId("bbb");
						cb.setItems(ZawodnicyGospodarz);
						Bramkigospvbox.getChildren().add(cb);
						BramkiGospVbox2.getChildren().add(tf);

					}
				} else {
					for (int xx = 0; xx < (Integer) newValue; xx++) {

						ChoiceBox<String> cb = new ChoiceBox<>();
						cb.setId("bbb");
						cb.setItems(ZawodnicyGospodarz);
						Bramkigospvbox.getChildren().add(cb);

					}
					;
				}
			}
		});

		ChoiceGoleGosc.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Bramkigoscivbox.setVisible(true);
				Bramkigoscivbox.getChildren().clear();
				BramkiGosciVbox2.getChildren().clear();
				if (dyscyplina.equals("Siatkówka") || dyscyplina.equals("Koszykówka")) {
					try {
						ResultSet rs = DBC.con.createStatement().executeQuery(
								"Select Count(*) from  zawodnicy where ID_Druzyny = (select druzyna2 from mecze where idmeczu = "
										+ idmeczu + ")");
						if (rs.next()) {
							ilosczawodnikowgosci = rs.getInt(1);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if ((Integer) newValue == 3) {
						ObservableList<Integer> Sety = FXCollections.observableArrayList();
						Sety.addAll(0, 1, 2);
						System.out.println(Sety);
						ChoiceGoleGosp.setItems(Sety);
						ChoiceGoleGosp.setValue(0);
					} else {
						Integer starawartosc = ChoiceGoleGosp.getValue();
						ChoiceGoleGosp.setItems(GoledoChoice);
						ChoiceGoleGosp.setValue(starawartosc);
					}

					for (int xx = 0; xx < ilosczawodnikowgosci; xx++) {
						TextField tf = new TextField();
						ChoiceBox<String> cb = new ChoiceBox<>();
						cb.setId("bbb");
						cb.setItems(ZawodnicyGosci);
						Bramkigoscivbox.getChildren().add(cb);
						BramkiGosciVbox2.getChildren().add(tf);
					}
				} else {
					for (int xx = 0; xx < (Integer) newValue; xx++) {
						ChoiceBox<String> cb = new ChoiceBox<>();
						cb.setId("bbb");
						cb.setItems(ZawodnicyGosci);
						Bramkigoscivbox.getChildren().add(cb);

					}
					;
				}
			}
		});

	}

	private ObservableList<ScheduleDetails> items;

	public void setItems(ObservableList<ScheduleDetails> items) {
		this.items = items;
	}

	private ObservableList<TeamStatDetails> items2;

	public void setItems2(ObservableList<TeamStatDetails> items2) {
		this.items2 = items2;
	}

	private ObservableList<GoalScorersDetails> items3;

	public void setItems3(ObservableList<GoalScorersDetails> items3) {
		this.items3 = items3;
	}

	public void setall(int idligi, String dyscyplina, String terminmeczu, String godzina, String obiektGospodarza,
			int idmeczu, int Kolejka, String gosp, String gosc, String golegosp, String golegosc) {
		idligi2 = idligi;
		numerkolejki = Kolejka;
		this.idmeczu = idmeczu;
		this.dyscyplina = dyscyplina;
		System.out.print(dyscyplina);
		kolejka.setText(kolejka.getText() + Kolejka + " Kolejki");
		Gosplbl.setText(gosp);
		Gosclbl.setText(gosc);
		WynikGosciLBL.setText(golegosc);
		WynikGospodarzyLBL.setText(golegosp);

		lblMiejsce.setText(lblMiejsce.getText() + obiektGospodarza);
		lblData.setText(lblData.getText() + terminmeczu);
		lblGodzina.setText(lblGodzina.getText() + godzina);
		System.out.println(Kolejka);
		if (this.dyscyplina.equals("Siatkówka")) {
			for (int i = 0; i <= 3; i++) {

				GoledoChoice.add(i);

			}

		} else if (this.dyscyplina.equals("Koszykówka")) {
			for (int i = 0; i <= 200; i++) {

				GoledoChoice.add(i);

			}

		} else {
			for (int i = 0; i <= 20; i++) {

				GoledoChoice.add(i);

			}
		}

		ChoiceGoleGosc.setItems(null);
		ChoiceGoleGosc.setItems(GoledoChoice);
		ChoiceGoleGosp.setItems(null);
		ChoiceGoleGosp.setItems(GoledoChoice);
		if (gosp.equals("Wolny Los")) {
			gospodarzeWolnyLos = true;
		} else if (gosc.equals("Wolny Los")) {
			goscieWolnyLos = true;
		} else {

		}
		if (dyscyplina.equals("Siatkówka") || dyscyplina.equals("Koszykówka")) {
			try {
				StrzelcyBramekDlaGospodarzy = FXCollections.observableArrayList();
				ResultSet rs = DBC.con.createStatement()
						.executeQuery("Select CONCAT(z.Imie , ' ' , z.Nazwisko) as Strzelec, sz.Bramka "
								+ "from mecze m join statystykizawodnikow as sz on sz.IdMeczu = m.idmeczu join zawodnicy as z on z.IDZawodnik = sz.IdZawodnika "
								+ "where m.idmeczu = " + idmeczu
								+ " AND z.ID_Druzyny = (Select druzyna1 from mecze where idmeczu = " + idmeczu + ")");
				while (rs.next()) {
					StrzelcyBramekDlaGospodarzy.add((rs.getString(1) + "   " + rs.getString(2) + " pkt"));
					System.out.println(rs.getString(1));

				}
				rs = DBC.con.createStatement()
						.executeQuery("Select CONCAT(z.Imie , ' ' , z.Nazwisko) as Strzelec, sz.Bramka "
								+ "from mecze m join statystykizawodnikow as sz on sz.IdMeczu = m.idmeczu join zawodnicy as z on z.IDZawodnik = sz.IdZawodnika "
								+ "where m.idmeczu = " + idmeczu
								+ " AND z.ID_Druzyny = (Select druzyna2 from mecze where idmeczu = " + idmeczu + ")");
				while (rs.next()) {
					StrzelcyBramekDlaGosci.add((rs.getString(1) + "   " + rs.getString(2) + " pkt"));
					System.out.println(rs.getString(1));
				}
				StrzelcyGospLV.setItems(StrzelcyBramekDlaGospodarzy);
				StrzelcyGoscLV.setItems(StrzelcyBramekDlaGosci);

			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}
		} else {
			try {
				StrzelcyBramekDlaGospodarzy = FXCollections.observableArrayList();
				ResultSet rs = DBC.con.createStatement()
						.executeQuery("Select CONCAT(z.Imie , ' ' , z.Nazwisko) as Strzelec "
								+ "from mecze m join statystykizawodnikow as sz on sz.IdMeczu = m.idmeczu join zawodnicy as z on z.IDZawodnik = sz.IdZawodnika "
								+ "where m.idmeczu = " + idmeczu
								+ " AND z.ID_Druzyny = (Select druzyna1 from mecze where idmeczu = " + idmeczu + ")");
				while (rs.next()) {
					StrzelcyBramekDlaGospodarzy.add(rs.getString(1));
					System.out.println(rs.getString(1));

				}
				rs = DBC.con.createStatement()
						.executeQuery("Select CONCAT(z.Imie , ' ' , z.Nazwisko) as Strzelec "
								+ "from mecze m join statystykizawodnikow as sz on sz.IdMeczu = m.idmeczu join zawodnicy as z on z.IDZawodnik = sz.IdZawodnika "
								+ "where m.idmeczu = " + idmeczu
								+ " AND z.ID_Druzyny = (Select druzyna2 from mecze where idmeczu = " + idmeczu + ")");
				while (rs.next()) {
					StrzelcyBramekDlaGosci.add(rs.getString(1));
					System.out.println(rs.getString(1));
				}
				StrzelcyGospLV.setItems(StrzelcyBramekDlaGospodarzy);
				StrzelcyGoscLV.setItems(StrzelcyBramekDlaGosci);

			} catch (SQLException ex) {
				System.err.println("Error" + ex);
			}
		}

		try {
			ZawodnicyGospodarz = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"Select imie,nazwisko from  zawodnicy where ID_Druzyny = (select druzyna1 from mecze where idmeczu = "
							+ idmeczu + ")");
			while (rs.next()) {
				// get string from db,whichever way
				ZawodnicyGospodarz.add(rs.getString(1) + " " + rs.getString(2));
			}
			ZawodnicyGospodarz.add("Gol Samobójczy");
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		try {

			ZawodnicyGosci = FXCollections.observableArrayList();
			// Execute query and store result in a resultset
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"Select imie,nazwisko from  zawodnicy where ID_Druzyny = (select druzyna2 from mecze where idmeczu = "
							+ idmeczu + ")");
			while (rs.next()) {
				// get string from db,whichever way
				ZawodnicyGosci.add(rs.getString(1) + " " + rs.getString(2));
			}
			ZawodnicyGosci.add("Gol Samobójczy");
		} catch (SQLException ex) {
			System.err.println("Error" + ex);
		}
		if (golegosp == null || golegosc == null) {
			MeczSieNieOdbyl.setVisible(true);
			ChoiceGoleGosc.setValue(null);
			ChoiceGoleGosc.setVisible(false);
			ChoiceGoleGosp.setValue(null);
			ChoiceGoleGosp.setVisible(false);
			WynikGospodarzyLBL.setVisible(false);
			WynikGosciLBL.setVisible(false);
			Bramkigospvbox.setVisible(false);
			Bramkigoscivbox.setVisible(false);
			StrzelcyGoscLV.setVisible(false);
			StrzelcyGospLV.setVisible(false);
			LabelNajlepsiGospodarze.setVisible(false);
			LabelNajlepsiGoscie.setVisible(false);

		} else {
			MeczSieNieOdbyl.setVisible(false);
			ChoiceGoleGosp.setValue(Integer.valueOf(golegosp));
			ChoiceGoleGosc.setValue(Integer.valueOf(golegosc));
			ChoiceGoleGosp.setVisible(false);
			ChoiceGoleGosc.setVisible(false);
			Bramkigospvbox.setVisible(false);
			BramkiGospVbox2.setVisible(false);
			Bramkigoscivbox.setVisible(false);
			BramkiGosciVbox2.setVisible(false);
			StrzelcyGoscLV.setVisible(true);
			StrzelcyGospLV.setVisible(true);
			LabelNajlepsiGospodarze.setVisible(true);
			LabelNajlepsiGoscie.setVisible(true);
			if (dyscyplina.equals("Siatkówka") || dyscyplina.equals("Koszykówka")) {
				LabelNajlepsiGospodarze.setText("Punktuj¹cy");
				LabelNajlepsiGoscie.setText("Punktuj¹cy");
			} else {
				LabelNajlepsiGospodarze.setText("Strzelcy Bramek");
				LabelNajlepsiGoscie.setText("Strzelcy Bramek");
			}
		}
	}

	@FXML
	public void Doedycji(ActionEvent event) {
		TrybLBL.setText("Tryb Edycji");
		TrybPodglBTN.setVisible(true);
		TrybEdycjiBTN.setVisible(false);
		MeczSieNieOdbyl.setVisible(false);
		ZatwierdzWynikBTN.setVisible(true);
		ChoiceGoleGosp.setVisible(true);
		ChoiceGoleGosc.setVisible(true);
		WynikGospodarzyLBL.setVisible(false);
		WynikGosciLBL.setVisible(false);
		Bramkigospvbox.setVisible(true);
		Bramkigoscivbox.setVisible(true);
		if (dyscyplina.equals("Siatkówka") || dyscyplina.equals("Koszykówka")) {
			BramkiGospVbox2.setVisible(true);
			BramkiGosciVbox2.setVisible(true);
		}
		StrzelcyGoscLV.setVisible(false);
		StrzelcyGospLV.setVisible(false);
		if (gospodarzeWolnyLos == true || goscieWolnyLos == true) {
			Bramkigospvbox.setVisible(false);
			groupGospodarze.setVisible(false);
			BramkiGospVbox2.setVisible(false);
			BramkiGosciVbox2.setVisible(false);
			Bramkigoscivbox.setVisible(false);
			groupGoscie.setVisible(false);
			StrzelcyGoscLV.setVisible(false);
			StrzelcyGospLV.setVisible(false);
			if (dyscyplina.equals("Koszykówka")) {
				AlertInformacyjny("Wolny los", "Wyst¹pi³ wolny los",
						"Jako ¿e jedn¹ z 'dru¿yn' jest Wolny los jedynym mo¿liwym wynikiem jest 20:0 dla dru¿yny nie bêd¹cej nim.");
			} else {
				AlertInformacyjny("Wolny los", "Wyst¹pi³ wolny los",
						"Jako ¿e jedn¹ z 'dru¿yn' jest Wolny los jedynym mo¿liwym wynikiem jest 3:0 dla dru¿yny nie bêd¹cej nim.");
			}
			ChoiceGoleGosc.setOnMouseClicked(e -> {
				AlertInformacyjny("B³¹d", "B³¹d", "Nie mo¿esz edytowaæ tego atrubutu");
			});
			ChoiceGoleGosp.setOnMouseClicked(e -> {
				AlertInformacyjny("B³¹d", "B³¹d", "Nie mo¿esz edytowaæ tego atrubutu");
			});

			if (gospodarzeWolnyLos == true) {
				if (dyscyplina.equals("Koszykówka")) {
					ChoiceGoleGosc.setValue(20);
				} else {
					ChoiceGoleGosc.setValue(3);
				}
				Bramkigospvbox.setVisible(false);
				groupGospodarze.setVisible(false);
				Bramkigoscivbox.setVisible(false);
				BramkiGospVbox2.setVisible(false);
				BramkiGosciVbox2.setVisible(false);
				groupGoscie.setVisible(false);
				StrzelcyGoscLV.setVisible(false);
				StrzelcyGospLV.setVisible(false);
			} else {
				ChoiceGoleGosc.setValue(0);
				Bramkigospvbox.setVisible(false);
				groupGospodarze.setVisible(false);
				Bramkigoscivbox.setVisible(false);
				BramkiGospVbox2.setVisible(false);
				BramkiGosciVbox2.setVisible(false);
				groupGoscie.setVisible(false);
				StrzelcyGoscLV.setVisible(false);
				StrzelcyGospLV.setVisible(false);
			}

			if (goscieWolnyLos == true) {
				if (dyscyplina.equals("Koszykówka")) {
					ChoiceGoleGosp.setValue(20);
				} else {
					ChoiceGoleGosp.setValue(3);
				}
				Bramkigospvbox.setVisible(false);
				groupGospodarze.setVisible(false);
				Bramkigoscivbox.setVisible(false);
				BramkiGospVbox2.setVisible(false);
				BramkiGosciVbox2.setVisible(false);
				groupGoscie.setVisible(false);
				StrzelcyGoscLV.setVisible(false);
				StrzelcyGospLV.setVisible(false);

			} else {
				ChoiceGoleGosp.setValue(0);
				Bramkigospvbox.setVisible(false);
				groupGospodarze.setVisible(false);
				Bramkigoscivbox.setVisible(false);
				BramkiGospVbox2.setVisible(false);
				BramkiGosciVbox2.setVisible(false);
				groupGoscie.setVisible(false);
				StrzelcyGoscLV.setVisible(false);
				StrzelcyGospLV.setVisible(false);
			}
		}
	}

	@FXML
	public void doPodgladu(ActionEvent event) {
		TrybLBL.setText("Tryb Podgl¹du");
		TrybPodglBTN.setVisible(false);
		TrybEdycjiBTN.setVisible(true);
		ZatwierdzWynikBTN.setVisible(false);
		ChoiceGoleGosp.setVisible(false);
		ChoiceGoleGosc.setVisible(false);
		BramkiGospVbox2.setVisible(false);
		BramkiGosciVbox2.setVisible(false);
		WynikGospodarzyLBL.setVisible(true);
		WynikGosciLBL.setVisible(true);
		Bramkigospvbox.setVisible(false);
		Bramkigoscivbox.setVisible(false);
		StrzelcyGoscLV.setVisible(true);
		StrzelcyGospLV.setVisible(true);

	}

	@FXML
	public void ZatwierdzWynik(ActionEvent event) throws IOException {
		if (dyscyplina.equals("Siatkówka") && ChoiceGoleGosc.getValue() != 3 && ChoiceGoleGosp.getValue() != 3) {
			AlertInformacyjny("B³¹d", "3 sety", "W siatkówce gra siê do 3 wygranych setów! Popraw wynik");
		} else {
			if (ChoiceGoleGosc.getValue() == null || ChoiceGoleGosp.getValue() == null) {
				AlertInformacyjny("B³¹d", "B³¹d", "Okreœl dok³adnie wynik");
			}

			else {
				boolean odbylsie = false;
				int golegospodarzyprzedzmiana = 0, golegosciprzedzmiana = 0;
				if (WynikGospodarzyLBL.getText() != null && WynikGosciLBL != null) {
					odbylsie = true;
					golegospodarzyprzedzmiana = Integer.valueOf(WynikGospodarzyLBL.getText());
					golegosciprzedzmiana = Integer.valueOf(WynikGosciLBL.getText());
				} else {
					odbylsie = false;
				}

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Potwierdzenie");
				alert.setHeaderText("Potwierdzenie");
				alert.setContentText("Na pewno chcesz Edytowaæ Wynik tego meczu?");
				ButtonType tak = new ButtonType("Tak");
				ButtonType wroc = new ButtonType("Wróæ", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(tak, wroc);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == tak) {
					System.out.println(numerkolejki);
					/*
					 * UPDATE mecze SET BramkiGospodarzy= 2, BramkiGosci = 3
					 * WHERE druzyna1 = (Select druzyny.IDDruzyny from druzyny
					 * where druzyny.NazwaD = "Termalica Bruk-Bet Nieciecza")
					 * and druzyna2 = (Select druzyny.IDDruzyny from druzyny
					 * where druzyny.NazwaD = "Jagiellonia Bia?ystok") and
					 * Kolejka = 5
					 */

					System.out.println(numerkolejki);
					System.out.println(Gosplbl.getText());
					System.out.println(ChoiceGoleGosp.getValue());
					System.out.println(Gosclbl.getText());
					System.out.println(ChoiceGoleGosc.getValue());
					try {
						Statement myStmt = DBC.con.createStatement();
						myStmt.executeUpdate(
								"Delete from statystykizawodnikow where statystykizawodnikow.IdMeczu = ((Select m.idmeczu from mecze m join druzyny d on d.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 "
										+ "Where d.NazwaD = '" + Gosplbl.getText() + "' and d2.NazwaD = '"
										+ Gosclbl.getText() + "'))" + "");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (dyscyplina.equals("Siatkówka") || dyscyplina.equals("Koszykówka")) {
						groupGospodarze.getChildren().clear();
						groupGoscie.getChildren().clear();
						groupGospodarze.getChildren().add(Bramkigospvbox);
						groupGospodarze.getChildren().add(BramkiGospVbox2);
						groupGoscie.getChildren().add(Bramkigoscivbox);
						groupGoscie.getChildren().add(BramkiGosciVbox2);

						Node nodeOut = groupGospodarze.getChildren().get(0);
						Node nodeOut2 = groupGoscie.getChildren().get(0);
						Integer i = 0;
						Integer i2 = 0;
						if (nodeOut instanceof VBox) {
							for (Node nodeIn : ((VBox) nodeOut).getChildren()) {
								if (nodeIn instanceof ChoiceBox) {
									if (((ChoiceBox) nodeIn).getValue() == null) {
										i++;
									} else if (((ChoiceBox) nodeIn).getValue() == "Gol Samobójczy") {
										i++;
									} else {

										System.out.println(((ChoiceBox) nodeIn).getValue());
										Node sss = BramkiGospVbox2.getChildren().get(i);
										System.out.println(((TextField) sss).getText());
										// Node nodeOut3 =
										// groupGospodarze.getChildren().get(1);
										// System.out.println(((TextField)

										// nodeIn.).getId());
										try {
											Statement myStmt = DBC.con.createStatement();
											myStmt.executeUpdate(
													"INSERT INTO statystykizawodnikow (IdZawodnika, IdMeczu, Bramka) "
															+ "VALUES ((Select zawodnicy.IDZawodnik from zawodnicy where CONCAT(zawodnicy.Imie, ' ', zawodnicy.Nazwisko) = '"
															+ (((ChoiceBox) nodeIn).getValue()) + "'),"
															+ " (Select m.idmeczu from mecze m join druzyny d on d.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 "
															+ "Where d.NazwaD = '" + Gosplbl.getText()
															+ "' and d2.NazwaD = '" + Gosclbl.getText() + "'), "
															+ Integer.valueOf(((TextField) sss).getText()) + ")");
											i++;
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							}
						}
						if (nodeOut2 instanceof VBox) {
							for (Node nodeIn : ((VBox) nodeOut2).getChildren()) {
								if (nodeIn instanceof ChoiceBox) {
									if (((ChoiceBox) nodeIn).getValue() == null) {
										i2++;
									} else if (((ChoiceBox) nodeIn).getValue() == "Gol Samobójczy") {
										i2++;
									} else {
										System.out.println(((ChoiceBox) nodeIn).getValue());
										Node sss2 = BramkiGosciVbox2.getChildren().get(i2);
										System.out.println(((TextField) sss2).getText());
										try {
											Statement myStmt = DBC.con.createStatement();
											myStmt.executeUpdate(
													"INSERT INTO statystykizawodnikow (IdZawodnika, IdMeczu, Bramka) "
															+ "VALUES ((Select zawodnicy.IDZawodnik from zawodnicy where CONCAT(zawodnicy.Imie, ' ', zawodnicy.Nazwisko) = '"
															+ (((ChoiceBox) nodeIn).getValue()) + "'),"
															+ " (Select m.idmeczu from mecze m join druzyny d on d.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 "
															+ "Where d.NazwaD = '" + Gosplbl.getText()
															+ "' and d2.NazwaD = '" + Gosclbl.getText() + "'), "
															+ Integer.valueOf(((TextField) sss2).getText()) + ")");
											i2++;
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							}
						}
					} else {
						groupGospodarze.getChildren().clear();
						groupGoscie.getChildren().clear();
						groupGospodarze.getChildren().add(Bramkigospvbox);
						groupGoscie.getChildren().add(Bramkigoscivbox);

						Node nodeOut = groupGospodarze.getChildren().get(0);
						Node nodeOut2 = groupGoscie.getChildren().get(0);

						if (nodeOut instanceof VBox) {
							for (Node nodeIn : ((VBox) nodeOut).getChildren()) {
								if (nodeIn instanceof ChoiceBox) {
									if (((ChoiceBox) nodeIn).getValue() == null) {

									} else if (((ChoiceBox) nodeIn).getValue() == "Gol Samobójczy") {

									} else {
										System.out.println(((ChoiceBox) nodeIn).getValue());
										// Node nodeOut3 =
										// groupGospodarze.getChildren().get(1);
										// System.out.println(((TextField)

										// nodeIn.).getId());
										try {
											Statement myStmt = DBC.con.createStatement();
											myStmt.executeUpdate(
													"INSERT INTO statystykizawodnikow (IdZawodnika, IdMeczu, Bramka) "
															+ "VALUES ((Select zawodnicy.IDZawodnik from zawodnicy where CONCAT(zawodnicy.Imie, ' ', zawodnicy.Nazwisko) = '"
															+ (((ChoiceBox) nodeIn).getValue()) + "'),"
															+ " (Select m.idmeczu from mecze m join druzyny d on d.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 "
															+ "Where d.NazwaD = '" + Gosplbl.getText()
															+ "' and d2.NazwaD = '" + Gosclbl.getText() + "'), " + 1
															+ ")");

										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							}
						}
						if (nodeOut2 instanceof VBox) {
							for (Node nodeIn : ((VBox) nodeOut2).getChildren()) {
								if (nodeIn instanceof ChoiceBox) {
									if (((ChoiceBox) nodeIn).getValue() == null) {

									} else if (((ChoiceBox) nodeIn).getValue() == "Gol Samobójczy") {

									} else {
										System.out.println(((ChoiceBox) nodeIn).getValue());

										try {
											Statement myStmt = DBC.con.createStatement();
											myStmt.executeUpdate(
													"INSERT INTO statystykizawodnikow (IdZawodnika, IdMeczu, Bramka) "
															+ "VALUES ((Select zawodnicy.IDZawodnik from zawodnicy where CONCAT(zawodnicy.Imie, ' ', zawodnicy.Nazwisko) = '"
															+ (((ChoiceBox) nodeIn).getValue()) + "'),"
															+ " (Select m.idmeczu from mecze m join druzyny d on d.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 "
															+ "Where d.NazwaD = '" + Gosplbl.getText()
															+ "' and d2.NazwaD = '" + Gosclbl.getText() + "'), " + 1
															+ ")");
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							}
						}

					}
					// !!
					try {

						Statement myStmt = DBC.con.createStatement();
						myStmt.executeUpdate("UPDATE mecze SET BramkiGospodarzy= " + ChoiceGoleGosp.getValue()
								+ ", BramkiGosci = " + ChoiceGoleGosc.getValue()
								+ " WHERE druzyna1 = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosplbl.getText()
								+ "') and druzyna2 = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosclbl.getText() + "') and Kolejka =" + numerkolejki);
					} catch (SQLException e) {

						e.printStackTrace();
					}
					try {

						Statement myStmt = DBC.con.createStatement();
						myStmt.executeUpdate("INSERT INTO statystyki_druzyny (ID_Druzyny, ID_Ligi)"
								+ "SELECT * FROM (SELECT (SELECT druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosplbl.getText() + "') AS ID_Druzyny, " + idligi2
								+ " AS ID_Ligi) AS tmp WHERE NOT EXISTS ("
								+ "SELECT ID_Druzyny FROM statystyki_druzyny WHERE ID_Druzyny = (SELECT druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosplbl.getText() + "') " + "AND ID_Ligi = " + idligi2 + ") LIMIT 1");
						myStmt.executeUpdate("INSERT INTO statystyki_druzyny (ID_Druzyny, ID_Ligi)"
								+ "SELECT * FROM (SELECT (SELECT druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosclbl.getText() + "') AS ID_Druzyny, " + idligi2
								+ " AS ID_Ligi) AS tmp WHERE NOT EXISTS ("
								+ "SELECT ID_Druzyny FROM statystyki_druzyny WHERE ID_Druzyny = (SELECT druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosclbl.getText() + "') " + "AND ID_Ligi = " + idligi2 + ") LIMIT 1");

					} catch (SQLException e) {

						e.printStackTrace();
					}
					try {

						Statement myStmt = DBC.con.createStatement();
						myStmt.executeUpdate(
								"Insert INTO statystykiklubu (ID_Statystyki_Druzyny,BramkiST, BramkiZD, Mecze, Punkty) "
										+ "SELECT * FROM (SELECT (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosplbl.getText() + "')) AS ID_Statystyki_Druzyny,"
										+ " 0 AS BramkiST,0 AS BramkiZD, 0 AS Mecze, 0 AS Punkty ) AS tmp WHERE NOT EXISTS "
										+ "(SELECT ID_Statystyki_Druzyny FROM statystykiklubu WHERE "
										+ "ID_Statystyki_Druzyny = (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosplbl.getText() + "') AND statystyki_druzyny.ID_Ligi = " + idligi2
										+ ")) LIMIT 1");
						myStmt.executeUpdate(
								"Insert INTO statystykiklubu (ID_Statystyki_Druzyny,BramkiST, BramkiZD, Mecze, Punkty) "
										+ "SELECT * FROM (SELECT (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosclbl.getText() + "')) AS ID_Statystyki_Druzyny,"
										+ " 0 AS BramkiST,0 AS BramkiZD, 0 AS Mecze, 0 AS Punkty ) AS tmp WHERE NOT EXISTS "
										+ "(SELECT ID_Statystyki_Druzyny FROM statystykiklubu WHERE "
										+ "ID_Statystyki_Druzyny = (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosclbl.getText() + "') AND statystyki_druzyny.ID_Ligi = " + idligi2
										+ ")) LIMIT 1");

					} catch (SQLException e) {

						e.printStackTrace();
					}
					if (odbylsie == true) {
						try {
							int doodjeciaugospodarzy;
							int doodjeciaugosci;
							if (golegospodarzyprzedzmiana > golegosciprzedzmiana) {
								doodjeciaugospodarzy = 3;
								doodjeciaugosci = 0;
							} else if (golegospodarzyprzedzmiana == golegosciprzedzmiana) {
								doodjeciaugospodarzy = 1;
								doodjeciaugosci = 1;
							} else {
								doodjeciaugosci = 3;
								doodjeciaugospodarzy = 0;
							}
							Statement myStmt = DBC.con.createStatement();
							myStmt.executeUpdate("UPDATE statystykiklubu SET BramkiZD = BramkiZD -"
									+ golegospodarzyprzedzmiana + ", BramkiST = BramkiST -" + golegosciprzedzmiana
									+ ", Mecze = Mecze - 1, " + "Punkty = Punkty - " + doodjeciaugospodarzy
									+ " WHERE ID_Statystyki_Druzyny = (Select statystyki_druzyny.ID_Statystyki_Druzyny from statystyki_druzyny Where "
									+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
									+ Gosplbl.getText() + "') " + "and statystyki_druzyny.ID_Ligi = " + idligi2 + ")");
							myStmt.executeUpdate("UPDATE statystykiklubu SET BramkiZD = BramkiZD -"
									+ golegosciprzedzmiana + ", BramkiST = BramkiST -" + golegospodarzyprzedzmiana
									+ ", Mecze = Mecze - 1, " + "Punkty = Punkty- " + doodjeciaugosci
									+ " WHERE ID_Statystyki_Druzyny = (Select statystyki_druzyny.ID_Statystyki_Druzyny from statystyki_druzyny Where "
									+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
									+ Gosclbl.getText() + "') " + "and statystyki_druzyny.ID_Ligi = " + idligi2 + ")");

						} catch (SQLException e) {

							e.printStackTrace();
						}
					} else {

					}
					try {

						Statement myStmt = DBC.con.createStatement();
						myStmt.executeUpdate(
								"Insert INTO statystykiklubu (ID_Statystyki_Druzyny,BramkiST, BramkiZD, Mecze, Punkty) "
										+ "SELECT * FROM (SELECT (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosplbl.getText() + "')) AS ID_Statystyki_Druzyny,"
										+ " 0 AS BramkiST,0 AS BramkiZD, 0 AS Mecze, 0 AS Punkty ) AS tmp WHERE NOT EXISTS "
										+ "(SELECT ID_Statystyki_Druzyny FROM statystykiklubu WHERE "
										+ "ID_Statystyki_Druzyny = (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosplbl.getText() + "') AND statystyki_druzyny.ID_Ligi = " + idligi2
										+ ")) LIMIT 1");
						myStmt.executeUpdate(
								"Insert INTO statystykiklubu (ID_Statystyki_Druzyny,BramkiST, BramkiZD, Mecze, Punkty) "
										+ "SELECT * FROM (SELECT (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosclbl.getText() + "')) AS ID_Statystyki_Druzyny,"
										+ " 0 AS BramkiST,0 AS BramkiZD, 0 AS Mecze, 0 AS Punkty ) AS tmp WHERE NOT EXISTS "
										+ "(SELECT ID_Statystyki_Druzyny FROM statystykiklubu WHERE "
										+ "ID_Statystyki_Druzyny = (SELECT ID_Statystyki_Druzyny from statystyki_druzyny where "
										+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
										+ Gosclbl.getText() + "') AND statystyki_druzyny.ID_Ligi = " + idligi2
										+ ")) LIMIT 1");
						int dlagospodarzy;
						int dlagosci;
						if (ChoiceGoleGosp.getValue() > ChoiceGoleGosc.getValue()) {
							dlagospodarzy = 3;
							dlagosci = 0;
						} else if (ChoiceGoleGosc.getValue() > ChoiceGoleGosp.getValue()) {
							dlagosci = 3;
							dlagospodarzy = 0;
						} else {
							dlagospodarzy = 1;
							dlagosci = 1;
						}
						myStmt.executeUpdate("UPDATE statystykiklubu SET BramkiZD = BramkiZD +"
								+ ChoiceGoleGosp.getValue() + ", BramkiST = BramkiST +" + ChoiceGoleGosc.getValue()
								+ ", Mecze = Mecze + 1, " + "Punkty = Punkty + " + dlagospodarzy
								+ " WHERE ID_Statystyki_Druzyny = (Select statystyki_druzyny.ID_Statystyki_Druzyny from statystyki_druzyny Where "
								+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosplbl.getText() + "') " + "and statystyki_druzyny.ID_Ligi = " + idligi2 + ")");
						myStmt.executeUpdate("UPDATE statystykiklubu SET BramkiZD = BramkiZD +"
								+ ChoiceGoleGosc.getValue() + ", BramkiST = BramkiST +" + ChoiceGoleGosp.getValue()
								+ ", Mecze = Mecze + 1, " + "Punkty = Punkty + " + dlagosci
								+ " WHERE ID_Statystyki_Druzyny = (Select statystyki_druzyny.ID_Statystyki_Druzyny from statystyki_druzyny Where "
								+ "statystyki_druzyny.ID_Druzyny = (Select druzyny.IDDruzyny from druzyny where druzyny.NazwaD = '"
								+ Gosclbl.getText() + "') " + "and statystyki_druzyny.ID_Ligi = " + idligi2 + ")");
						WynikGospodarzyLBL.setText(String.valueOf(ChoiceGoleGosp.getValue()));
						WynikGosciLBL.setText(String.valueOf(ChoiceGoleGosc.getValue()));
						items.clear();
						try {
							ResultSet rs = DBC.con.createStatement().executeQuery(
									"SELECT m.Kolejka,m.DataMeczu,m.godzinameczu, d1.NazwaD, m.BramkiGospodarzy, d2.NazwaD, m.BramkiGosci from mecze m join druzyny d1 on "
											+ "d1.IDDruzyny = m.druzyna1 join druzyny d2 on d2.IDDruzyny = m.druzyna2 where m.idligi = "
											+ idligi2 + " order by m.kolejka");
							if (rs.next()) {
								do {
									items.add(new ScheduleDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
											rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
								} while (rs.next());

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						items2.clear();
						int pozycja = 0;
						try {

							// Execute query and store result in a resultset
							ResultSet rs = DBC.con.createStatement()
									.executeQuery("Select d.NazwaD,sk.Mecze, sk.BramkiZD, "
											+ "sk.BramkiST,sk.Punkty from statystykiklubu as sk JOIN statystyki_druzyny as sd on "
											+ "sd.ID_Statystyki_Druzyny = sk.ID_Statystyki_Druzyny join druzyny as d on d.IDDruzyny = sd.ID_Druzyny where "
											+ "sd.ID_Ligi = " + idligi2
											+ " and d.NazwaD != 'wolny los' order by sk.Punkty desc, sk.BramkiZD desc ");
							while (rs.next()) {
								// get string from db,whichever way
								pozycja = pozycja + 1;
								items2.add(new TeamStatDetails(pozycja, rs.getString(1), rs.getInt(2), rs.getInt(3),
										rs.getInt(4), rs.getInt(5)));

							}

						} catch (SQLException ex) {
							System.err.println("Error" + ex);

						}
						items3.clear();
						try {

							// Execute query and store result in a resultset
							ResultSet rs = DBC.con.createStatement().executeQuery(
									"Select Concat (z.Imie , ' ' , z.Nazwisko) as Zawodnik, d.NazwaD, SUM(sz.Bramka)  from statystykizawodnikow sz join mecze m "
											+ "on m.idmeczu = sz.IdMeczu join zawodnicy z on z.IDZawodnik = sz.IdZawodnika join druzyny d on z.ID_Druzyny = d.IDDruzyny where m.idligi ="
											+ idligi2 + " GROUP BY sz.IdZawodnika Order By sz.Bramka desc");

							while (rs.next()) {
								// get string from db,whichever way
								items3.add(new GoalScorersDetails(rs.getString(1), rs.getString(2), rs.getInt(3)));

							}

						} catch (SQLException ex) {
							System.err.println("Error" + ex);
						}

					} catch (SQLException e) {

						e.printStackTrace();
					}
					StrzelcyBramekDlaGospodarzy.clear();
					StrzelcyBramekDlaGosci.clear();
					try {
						ResultSet rs = DBC.con.createStatement()
								.executeQuery("Select CONCAT(z.Imie , ' ' , z.Nazwisko) as Strzelec "
										+ "from mecze m join statystykizawodnikow as sz on sz.IdMeczu = m.idmeczu join zawodnicy as z on z.IDZawodnik = sz.IdZawodnika "
										+ "where m.idmeczu = " + idmeczu
										+ " AND z.ID_Druzyny = (Select druzyna1 from mecze where idmeczu = " + idmeczu
										+ ")");
						while (rs.next()) {
							StrzelcyBramekDlaGospodarzy.add(rs.getString(1));
							System.out.println(rs.getString(1));

						}
						rs = DBC.con.createStatement()
								.executeQuery("Select CONCAT(z.Imie , ' ' , z.Nazwisko) as Strzelec "
										+ "from mecze m join statystykizawodnikow as sz on sz.IdMeczu = m.idmeczu join zawodnicy as z on z.IDZawodnik = sz.IdZawodnika "
										+ "where m.idmeczu = " + idmeczu
										+ " AND z.ID_Druzyny = (Select druzyna2 from mecze where idmeczu = " + idmeczu
										+ ")");
						while (rs.next()) {
							StrzelcyBramekDlaGosci.add(rs.getString(1));
							System.out.println(rs.getString(1));
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				else {

				}
			}
		}

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
