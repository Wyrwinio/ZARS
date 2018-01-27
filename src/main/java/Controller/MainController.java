package Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController {
	@FXML
	private StackPane mainPane;

	@FXML
	public void initialize() {
		loadScreen();
	}

	public void loadScreen() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Log.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller Controller = loader.getController();
		Controller.setMainController(this);
		setScreen(pane);
	}

	public void setScreen(Pane pane) {
		mainPane.getChildren().clear();
		mainPane.getChildren().add(pane);
	}

}
