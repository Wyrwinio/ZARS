package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.IncomingMessageDetails;
import model.OutgoingMessageDetails;

public class ControllerMessage {
	@FXML
	private Label nazwauzytkownikawiadomosci;
	@FXML
	private Button WyslijOdpowiedzBTN;
	private MainController mainController;
	@FXML
	private TextArea TATrescWiadomosci;
	@FXML
	private Label iduzytkownikawiadomosci;
	@FXML
	private Label idwiadomosci;
	@FXML
	private TextArea TAOdpowiedz;
	DBconnect DBC = new DBconnect();
	int iduzytkownika;
	int idadmina;

	public void setText(Integer idwiadomosci, String Uzytkownik, String Tresc, Integer adminid) {
		this.nazwauzytkownikawiadomosci.setText(Uzytkownik);
		this.TATrescWiadomosci.setText(Tresc);
		this.idwiadomosci.setText(String.valueOf(idwiadomosci));
		idadmina = adminid;
		try {
			ResultSet rs = DBC.con.createStatement()
					.executeQuery("Select id from uzytkownicy where username = '" + Uzytkownik + "'");
			if (rs.next()) {
				iduzytkownika = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ResultSet rs = DBC.con.createStatement().executeQuery(
					"SELECT Zwrot from wiadomosci_przychodzace where id_wiadomosc = " + idwiadomosci + "");
			if (rs.next()) {
				if (rs.getString(1).equals("Tak")) {
					TAOdpowiedz.setText("Ju¿ odpowiedzia³eœ na t¹ wiadomoœæ");
					TAOdpowiedz.setEditable(false);
					WyslijOdpowiedzBTN.setDisable(true);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // this.id.setText(String.valueOf(id));
			// idZawodnika = id;
			// Druzyny.setValue(druzyna);
	}

	public void WyslijOdpowiedz(ActionEvent event) {
		if (TAOdpowiedz.getText().isEmpty()) {
			UserpanelController UPC = new UserpanelController();
			UPC.AlertInformacyjny("B³¹d", "Pusta wiadomoœæ", "Nie mo¿esz wys³aæ pustej wiadomoœci");

		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Czy na pewno chcesz odpowiedziec?", ButtonType.OK,
					ButtonType.CANCEL);
			alert.setTitle("Potwierdzenie");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {

				// String sql = ;
				// myStmt.executeUpdate(sql);
				items.clear();
				items2.clear();
				// items2.clear();
				System.out.println(idadmina);
				try {
					Statement myStmt = DBC.con.createStatement();
					String sql = "Insert into wiadomosci_wychodzace (id_autora,id_adresata,Tresc) values (" + idadmina
							+ "," + iduzytkownika + ",'" + TAOdpowiedz.getText() + "');";
					myStmt.executeUpdate(sql);
					myStmt.executeUpdate("UPDATE wiadomosci_przychodzace SET Zwrot = 'Tak' where id_wiadomosc = "
							+ Integer.valueOf(idwiadomosci.getText()));
					ResultSet rs = DBC.con.createStatement()
							.executeQuery("Select wp.id_wiadomosc,u.username,wp.Tresc, wp.Zwrot from "
									+ "wiadomosci_przychodzace wp join uzytkownicy as u on u.id = wp.id_autora;");
					while (rs.next()) {
						items2.add(new IncomingMessageDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getString(4)));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
				Stage stage = (Stage) WyslijOdpowiedzBTN.getScene().getWindow();
				stage.close();

			}
		}
	}

	private ObservableList<OutgoingMessageDetails> items;
	private ObservableList<IncomingMessageDetails> items2;

	public void setItems(ObservableList<OutgoingMessageDetails> items) {
		this.items = items;
	}

	public void setItems2(ObservableList<IncomingMessageDetails> items2) {
		this.items2 = items2;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

}
