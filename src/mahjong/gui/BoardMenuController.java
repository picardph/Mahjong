package mahjong.gui;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import mahjong.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class BoardMenuController {

	@FXML
	private BorderPane border;
	private SubScene hostScene;
	private Group root;
	private PerspectiveCamera camera;
	private String saveName;
	private Game game;
	private UITile selected;

	@FXML
	public void initialize() throws FileNotFoundException {
		// Set the size we want the game to be.
		MahjongApplication.getPrimary().setWidth(900);
		MahjongApplication.getPrimary().setHeight(600);

		// The save name will be populated when we save for the first time.
		saveName = null;
		// Get the file that was set during the new page process.
		game = new Game(MahjongApplication.getLoadFile());
		// Shuffle up the puzzle so it is not the same each time.
		game.shuffle();

		root = new Group();
		hostScene = new SubScene(root, MahjongApplication.getPrimary().getWidth(), MahjongApplication.getPrimary().getWidth(), true, SceneAntialiasing.BALANCED);
		border.setCenter(hostScene);

		camera = new PerspectiveCamera(false);
		// Rotate the camera a little to make a nicer 3D effect.
		camera.getTransforms().addAll(
				new Translate(),
				new Rotate(0, Rotate.Y_AXIS),
				new Rotate(30, Rotate.X_AXIS),
				new Translate(0, 0, -500)
		);
		hostScene.setFill(Color.CORNFLOWERBLUE);
		hostScene.setCamera(camera);

		selected = null;

		// Set the initial game board state.
		setBoardTiles();
	}

	public void onNewClicked(ActionEvent actionEvent) throws Exception {
		MahjongApplication.setRoot(FXMLLoader.load(getClass().getResource("NewMenu.fxml")), false);
	}

	public void onOpenClicked(ActionEvent actionEvent) throws FileNotFoundException {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));

		File f = chooser.showOpenDialog(MahjongApplication.getPrimary());
		// Null means the user canceled.
		if (f == null)
			return;
		// Store the save name.
		saveName = f.getAbsolutePath();
		game = new Game(saveName);
		setBoardTiles();
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

	public void onInfoClicked(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Game Info");
		alert.setContentText("You have " + game.getShufflesLeft() + " shuffles left. Be careful!");
	}

	public void onShuffleClicked(ActionEvent actionEvent) throws FileNotFoundException {
		if (game.getShufflesLeft() == 0) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No more shuffles!");
			alert.setContentText("You only get 5 shuffles per game.");
			alert.showAndWait();
		}
		game.shuffle();
		setBoardTiles();
	}

	private void setBoardTiles() throws FileNotFoundException {
		clearBoardTiles();
		// Get every tile in the game and make it visible on the board.
		for (Tile t : game.getAllTiles()) {
			UITile tile = new UITile(t);
			tile.setWidth(32);
			tile.setHeight(46);
			tile.setDepth(25);
			tile.setTranslateX(50 + t.getX() * 16);
			tile.setTranslateY(50 + t.getY() * 23);
			tile.setTranslateZ(-t.getZ() * 25);
			tile.setOnMouseClicked(e -> {
				// We already have selected something...
				if (selected != null) {
					if (game.isMatch(selected.getTile(), tile.getTile()))
						game.removeTiles(selected.getTile(), tile.getTile());
					selected = null;
					// Rerun all the board setting.
					try {
						setBoardTiles();
					}
					catch (Exception ex) {
						throw new RuntimeException("File not found.");
					}

					// Look at the state of the game now and see if we won/lost.
					if (game.getGameState() == GameState.Won)
						won();
					else if (game.getGameState() == GameState.Lost)
						lost();
				}
				else {
					selected = tile;
					((PhongMaterial) tile.getMaterial()).setDiffuseColor(Color.BLUE);
				}
			});

			root.getChildren().add(tile);
		}
	}

	private void clearBoardTiles() {
		root.getChildren().clear();
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
