package mahjong.gui;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import mahjong.Game;
import mahjong.Tile;
import mahjong.TileClass;
import mahjong.TileType;

import java.io.File;
import java.util.HashMap;

public class BoardMenuController {

	@FXML
	private ResizableCanvas canvas;

	private String saveName;
	private Game game;
	private HashMap<TileClass, Color> tileColors;

	@FXML
	public void initialize() {
		// The save name will be populated when we save for the first time.
		saveName = null;
		// Get the file that was set during the new page process.
		game = new Game(MahjongApplication.getLoadFile());
		canvas.setRenderer((context -> {
			draw(context);
		}));

		// Fill out all the tile colors.
		tileColors = new HashMap<>();
		tileColors.put(TileClass.Character, Color.BLUE);
		tileColors.put(TileClass.Bamboo, Color.WHEAT);
		tileColors.put(TileClass.Circle, Color.YELLOW);
		tileColors.put(TileClass.Dragon, Color.RED);
		tileColors.put(TileClass.Wind, Color.CORNFLOWERBLUE);
		tileColors.put(TileClass.Season, Color.SADDLEBROWN);
		tileColors.put(TileClass.Flower, Color.ORANGE);
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

	private void won() {
	}

	private void lost() {
	}

	private void draw(GraphicsContext context) {
		double width = canvas.getWidth();
		double height = canvas.getHeight();

		// Color in the background.
		context.setFill(Color.LIGHTGRAY);
		context.fillRect(0, 0, width, height);

		// Draw out the grid.
		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 5; z++) {
					// Draw the right background for the tile.
					Tile t = game.getTile(x, y, z);
					if (t != null) {
						context.setFill(tileColors.get(game.getTileClass(t.getType())));
						context.fillRect(x * 32, y * 32, 32, 32);
					}
				}
			}
		}
	}
}
