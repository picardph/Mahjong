package mahjong.gui;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import mahjong.*;

import java.io.File;
import java.util.HashMap;

public class BoardMenuController {

	@FXML
	private ResizableCanvas canvas;

	private String saveName;
	private Game game;
	private HashMap<TileClass, Color> tileColors;
	private Tile selected;
	private Tile hover;

	@FXML
	public void initialize() {
		// The save name will be populated when we save for the first time.
		saveName = null;
		// Get the file that was set during the new page process.
		game = new Game(MahjongApplication.getLoadFile());
		// Shuffle up the puzzle so it is not the same each time.
		game.shuffle();
		selected = null;
		hover = null;
		canvas.setRenderer((context -> {
			draw(context);
		}));

		// Fill out all the tile colors.
		tileColors = new HashMap<>();
		tileColors.put(TileClass.Character, Color.BLUE);
		tileColors.put(TileClass.Bamboo, Color.WHEAT);
		tileColors.put(TileClass.Circle, Color.YELLOW);
		tileColors.put(TileClass.Dragon, Color.MEDIUMVIOLETRED);
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

	private void draw(GraphicsContext context) {
		double width = canvas.getWidth();
		double height = canvas.getHeight();

		// Color in the background.
		context.setFill(Color.LIGHTGRAY);
		context.fillRect(0, 0, width, height);

		// Draw out the grid.
		for (int z = 0; z < 5; z++) {
			for (int y = 0; y < 16; y++) {
				for (int x = 0; x < 30; x++) {
					// Draw the right background for the tile.
					Tile t = game.getTile(x, y, z);
					if (t != null) {
						// Draw the background of the tile.
						context.setFill(tileColors.get(game.getTileClass(t.getType())));
						if (t == hover)
							context.setFill(Color.WHITE);
						context.fillRect(t.getX() * 32, t.getY() * 32, 64, 64);
						// Fill out the border of the tile. This will get drawn multiple times
						// but it doesn't really matter because it will go right over the old one.
						context.setStroke(Color.WHITE);
						// The selected tile will be highlighted with red.
						if (t == selected)
							context.setStroke(Color.RED);
						context.strokeRect(t.getX() * 32, t.getY() * 32, 64, 64);

						// Now draw the tile number in the middle.
						context.setFill(Color.WHITE);
						int num = getTileNumber(t.getType());
						context.fillText(Integer.toString(num) + " (" + Integer.toString(t.getIdent()) + ")", (t.getX() * 32) + 10, (t.getY() * 32) + 10);
					}
				}
			}
		}

		// Draw the border around all the tiles. This is not done in the same loop
		// as before to prevent other rendering from being done over the layer border.
		for (int z = 0; z < 5; z++) {
			for (int y = 0; y < 16; y++) {
				for (int x = 0; x < 30; x++) {
					// Make sure we are at a tile...
					if (game.getTile(x, y, z) == null)
						continue;

					// Check if there is a valid tile in each direction and draw
					// a black line there if there is not.
					context.setStroke(Color.BLACK);
					context.setLineWidth(3.0);
					// Above
					if (game.getTile(x, y - 1, z) == null)
						context.strokeLine(x * 32, y * 32, (x + 1) * 32, y * 32);
					// Below
					if (game.getTile(x, y + 1, z) == null)
						context.strokeLine(x * 32, (y + 1) * 32, (x + 1) * 32, (y + 1) * 32);
					// Left
					if (game.getTile(x - 1, y, z) == null)
						context.strokeLine(x * 32, y * 32, x * 32, (y + 1) * 32);
					// Right
					if (game.getTile(x + 1, y, z) == null)
						context.strokeLine((x + 1) * 32, y * 32, (x + 1) * 32, (y + 1) * 32);
					context.setLineWidth(1.0);
				}
			}
		}
	}

	public void onCanvasClicked(MouseEvent mouseEvent) {
		if (game.getGameState() == GameState.Lost) {
			selected = null;
			draw(canvas.getContext());
			return;
		}

		// Figure out which tile was clicked on.
		Tile t = null;
		int x = ((int)mouseEvent.getX()) / 32;
		int y = ((int)mouseEvent.getY()) / 32;

		for (int z = 4; z >= 0; z--) {
			if (x >= 30 || x < 0 || y >= 16 || y < 0)
				return;

			t = game.getTile(x, y, z);
			if (t != null)
				break;
		}
		// Empty spot.
		if (t == null)
			return;

		if (game.isValidTile(t)) {
			// Select the first tile.
			if (selected == null)
				selected = t;
			// Two tiles have been selected so try to match them.
			else {
				if (game.isMatch(selected, t)) {
					game.removeTiles(selected, t);
					// Deselect the tiles.
					selected = null;
					if (game.getGameState() == GameState.Won)
						won();
				}
				// Invalid match. Forget the whole thing...
				else
					selected = null;
			}
		}

		// Redraw the graphics.
		draw(canvas.getContext());
	}

	public void onCanvasMove(MouseEvent mouseEvent) {
		hover = null;
		int x = ((int)mouseEvent.getX()) / 32;
		int y = ((int)mouseEvent.getY()) / 32;
		if (x >= 30 || x < 0 || y >= 16 || y < 0)
			return;

		// Figure out which tile the mouse is over.
		int z;
		for (z = 4; z >= 0; z--) {
			hover = game.getTile(x, y, z);
			if (hover != null)
				break;
		}

		// Redraw the graphics.
		draw(canvas.getContext());
	}

	private int getTileNumber(TileType type) {
		switch (type) {
			case Ch1:
			case Bam1:
			case Cir1:
			case Dra1:
			case Wind1:
				return 1;
			case Ch2:
			case Bam2:
			case Cir2:
			case Dra2:
			case Wind2:
				return 2;
			case Ch3:
			case Bam3:
			case Cir3:
			case Dra3:
			case Wind3:
				return 3;
			case Ch4:
			case Bam4:
			case Cir4:
			case Wind4:
				return 4;
			case Ch5:
			case Bam5:
			case Cir5:
				return 5;
			case Ch6:
			case Bam6:
			case Cir6:
				return 6;
			case Ch7:
			case Bam7:
			case Cir7:
				return 7;
			case Ch8:
			case Bam8:
			case Cir8:
				return 8;
			case Ch9:
			case Bam9:
			case Cir9:
				return 9;
			case Sea:
			case Flo:
			default:
				return 0;
		}
	}
}
