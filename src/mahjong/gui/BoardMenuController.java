package mahjong.gui;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import mahjong.*;

import java.io.File;
import java.util.HashMap;

public class BoardMenuController {

	@FXML
	private BorderPane border;
	private SubScene hostScene;
	private Group root;
	private PerspectiveCamera camera;
	private String saveName;
	private Game game;

	@FXML
	public void initialize() {
		// Set the size we want the game to be.
		MahjongApplication.getPrimary().setWidth(900);
		MahjongApplication.getPrimary().setHeight(600);

		// The save name will be populated when we save for the first time.
		saveName = null;
		// Get the file that was set during the new page process.
		game = new Game(MahjongApplication.getLoadFile());
		// Shuffle up the puzzle so it is not the same each time.
		game.shuffle();

		UITile tile = new UITile(game.getAllTiles()[0]);
		tile.setWidth(100);
		tile.setHeight(100);
		tile.setDepth(50);
		tile.setTranslateX(100);
		tile.setTranslateY(100);

		root = new Group(tile);
		hostScene = new SubScene(root, MahjongApplication.getPrimary().getWidth(), MahjongApplication.getPrimary().getWidth());
		border.setCenter(hostScene);

		camera = new PerspectiveCamera(false);
		hostScene.setFill(Color.CORNFLOWERBLUE);
		hostScene.setCamera(camera);
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

	public void onShuffleClicked(ActionEvent actionEvent) {
		if (game.getShufflesLeft() == 0) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No more shuffles!");
			alert.setContentText("You only get 5 shuffles per game.");
			alert.showAndWait();
		}
		game.shuffle();
	}

	private void won() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Congratulations!");
		alert.setContentText("You have removed all the tiles from the board!");
		alert.showAndWait();
	}

	private void lost() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("You lost!");
		alert.setContentText("You can only reshuffle the board 5 times in one game!");
		alert.showAndWait();
	}
}
