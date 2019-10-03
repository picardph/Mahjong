package Mahjong.GUI;

import Mahjong.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BoardMenuController {

	private Game game;

	private void won() {
	}

	private void lost() {
	}

	@FXML
	public void initialize() {
	}

	public void onNewClicked(ActionEvent actionEvent) {
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
