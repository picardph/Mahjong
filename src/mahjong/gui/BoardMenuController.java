package mahjong.gui;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import mahjong.Game;

import java.io.File;

public class BoardMenuController {

	private String saveName;
	private Game game;

	private void won() {
	}

	private void lost() {
	}

	@FXML
	public void initialize() {
		// The save name will be populated when we save for the first time.
		saveName = null;
		// Get the file that was set during the new page process.
		game = new Game(MahjongApplication.getLoadFile());
	}

	public void onNewClicked(ActionEvent actionEvent) throws Exception {
		MahjongApplication.setRoot(FXMLLoader.load(getClass().getResource("NewMenu.fxml")), false);
	}

	public void onOpenClicked(ActionEvent actionEvent) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));

		File f = chooser.showOpenDialog(MahjongApplication.getPrimary());
		// Null means the user canceled.
		if (f == null)
			return;
		// Store the save name.
		saveName = f.getAbsolutePath();
		game = new Game(saveName);
	}

	public void onSaveClicked(ActionEvent actionEvent) throws Exception {
		// If the game was not saved before we have to do a save as instead.
		if (saveName == null)
			onSaveAsClicked(actionEvent);
		else
			game.saveGame(saveName);
	}

	public void onSaveAsClicked(ActionEvent actionEvent) throws Exception {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));

		File f = chooser.showSaveDialog(MahjongApplication.getPrimary());
		// Null means the user canceled.
		if (f == null)
			return;
		// Store where we are saving.
		saveName = f.getAbsolutePath();
		// Saving can be handled by our other save handler.
		onSaveClicked(actionEvent);
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
