package mahjong.gui;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mahjong.Game;

public class BoardMenuController {

	private Game game;

	private void won() {
	}

	private void lost() {
	}

	@FXML
	public void initialize() {
	}

	public void onNewClicked(ActionEvent actionEvent) throws Exception {
		MahjongApplication.setRoot(FXMLLoader.load(getClass().getResource("NewMenu.fxml")));
	}

	public void onOpenClicked(ActionEvent actionEvent) {
	}

	public void onSaveClicked(ActionEvent actionEvent) {
	}

	public void onSaveAsClicked(ActionEvent actionEvent) {
	}

	public void onExitClicked(ActionEvent actionEvent) {
		System.exit(0);
	}

	public void onHintClicked(ActionEvent actionEvent) {
	}

	public void onShuffleClicked(ActionEvent actionEvent) {
		game.shuffle();
	}
}
