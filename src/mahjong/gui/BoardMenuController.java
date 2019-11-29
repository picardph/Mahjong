package mahjong.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;

import mahjong.Game;
import mahjong.Tile;
import mahjong.LeaderBoard;
import mahjong.TimerEntry;
import mahjong.GameState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * The board menu controller acts as the middle man between the actual
 * GUI (handled by BoardMenu.fxml) and the internal game logic/state. In
 * the MVC design pattern, this class acts as a Controller between the
 * GUI board and the game board.
 */
public class BoardMenuController {

    @FXML
    private BorderPane border;
    private SubScene hostScene;
    private Group root;
    private PerspectiveCamera camera;
    private String saveName;
    private UITile selected;

    private Game game;
    private LeaderBoard leaders;

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final int CAM_ROTATE = 30;
    private static final int CAM_TRANSLATE = -500;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 46;
    private static final int TILE_DEPTH = 25;
    private static final int OFFSET_X = 50;
    private static final int OFFSET_Y = 50;
    private static final int TILE_GAP_X = 16;
    private static final int TILE_GAP_Y = 23;
    private static final int SECONDS = 60;

    /**
     * Called by the FXML file when we should put the game into a
     * starting state. It needs to be public so that FXML can call it but it
     * should not be called by other parts of the program under any
     * circumstances.
     * @throws FileNotFoundException Thrown when the game is unable to load
     * textures for the tiles.
     */
    @FXML
    public void initialize() throws FileNotFoundException {
        // Set the size we want the game to be.
        MahjongApplication.getPrimary().setWidth(WIDTH);
        MahjongApplication.getPrimary().setHeight(HEIGHT);

        // The save name will be populated when we save for the first time.
        saveName = null;

        try {
            // Get the file that was set during the new page process.
            game = new Game(MahjongApplication.getLoadFile());
            // Responsible for keeping track of the high scores.
            leaders = new LeaderBoard();
        } catch (RuntimeException e) {
            showError("Failed to load the game file.");
        }

        root = new Group();
        hostScene = new SubScene(root,
                MahjongApplication.getPrimary().getWidth(),
                MahjongApplication.getPrimary().getWidth(), true,
                SceneAntialiasing.BALANCED);
        border.setCenter(hostScene);

        camera = new PerspectiveCamera(false);
        // Rotate the camera a little to make a nicer 3D effect.
        camera.getTransforms().addAll(
                new Translate(),
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(CAM_ROTATE, Rotate.X_AXIS),
                new Translate(0, 0, CAM_TRANSLATE)
        );
        hostScene.setFill(Color.CORNFLOWERBLUE);
        hostScene.setCamera(camera);

        selected = null;

        // Set the initial game board state.
        setBoardTiles();
        // Always reset the timer.
        TimerEntry.reset();
        // Change the title-bar to hold how much time has passed.
        TimerEntry.setObserver((minutes, seconds) -> {
            Platform.runLater(() -> {
                // setObserver gets called on a separate thread. runLater
                // will tell JavaFX to run this bit on the main event
                // thread when possible as GUI changes can only be done
                // there.
                MahjongApplication.getPrimary().setTitle(
                        "Minutes: " + TimerEntry.getMinutes()
                                + " Seconds: " + TimerEntry.getSeconds());
            });
        });
    }

    /**
     * Called by FXML when the new button in the file menu is selected.
     * @param actionEvent Relevant context data about the event.
     * @throws Exception Thrown when the new menu FXML file can't be loaded.
     */
    public void onNewClicked(final ActionEvent actionEvent) throws Exception {
        MahjongApplication.setRoot(FXMLLoader.load(getClass()
                .getResource("NewMenu.fxml")), false);
        TimerEntry.reset();
    }

    /**
     * Called by FXML when we should display a dialog to let the user load a
     * previously saved game from a text file.
     * @param actionEvent Relevant context data about the event.
     * @throws FileNotFoundException Thrown when the file can't be loaded.
     */
    public void onOpenClicked(final ActionEvent actionEvent)
            throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));

        File f = chooser.showOpenDialog(MahjongApplication.getPrimary());
        // Null means the user canceled.
        if (f == null) {
            return;
        }
        // Store the save name.
        saveName = f.getAbsolutePath();
        try {
            game = new Game(saveName);
        } catch (RuntimeException e) {
            showError("Failed to load the game file.");
        }
        setBoardTiles();
    }

    /**
     * Called by FXML when the user wants to save a previously save
     * game again. If save the game has not been saved before, the
     * event will be forwarded to onSaveAsClicked.
     * @param actionEvent Relevant context data about the event.
     * @throws Exception Can only be thrown when saving for the first time.
     */
    public void onSaveClicked(final ActionEvent actionEvent) throws Exception {
        // If the game was not saved before we have to do a save as instead.
        if (saveName == null) {
            onSaveAsClicked(actionEvent);
        } else {
            game.saveGame(saveName);
        }
    }

    /**
     * Called by FXML when the user wants to save a game for the first time
     * and wants to pick the location where the game should be saved.
     * @param actionEvent Relevant context data about the event.
     * @throws Exception Can be thrown when the file can't be saved.
     */
    public void onSaveAsClicked(final ActionEvent actionEvent) throws Exception {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));

        File f = chooser.showSaveDialog(MahjongApplication.getPrimary());
        // Null means the user canceled.
        if (f == null) {
            return;
        }
        // Store where we are saving.
        saveName = f.getAbsolutePath();
        // Saving can be handled by our other save handler.
        onSaveClicked(actionEvent);
    }

    /**
     * Called by FXML when the user selects the exit menu item.
     * @param actionEvent Relevant context data about the event.
     */
    public void onExitClicked(final ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Called by JavaFX when the user selects the undo menu item.
     * @param actionEvent Relevant context data about the event.
     * @throws FileNotFoundException Thrown when unable to set the tile board files.
     */
    public void onUndoClicked(final ActionEvent actionEvent)
            throws FileNotFoundException {
        game.undo();
        setBoardTiles();
    }

    /**
     * Called by FXML when the user wants to get information about the
     * current state of the game.
     * @param actionEvent Relevant context data about the event.
     */
    public void onInfoClicked(final ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Info");
        alert.setContentText("You have " + game.getShufflesLeft()
                + " shuffles left. Be careful!");
        alert.showAndWait();
    }

    /**
     * Called by FXML when the user wants to see a presentation of the
     * top scores. Scores are recorded by the amount of time it took
     * to beat the game.
     * @param actionEvent Relevant context data about the event.
     */
    public void onHighScoresClicked(final ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("High Scores");

        if (leaders.size() == 0) {
            alert.setContentText("There are no winners stored!");
        } else {
            // Build a leader board list.
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < leaders.size(); i++) {
                text.append(leaders.entryAtPosition(i)).append("\n");
            }
            alert.setContentText(text.toString());
        }

        alert.showAndWait();
    }

    /**
     * Called by FXML when the user wants a hint. If there is a matching pair,
     * the pair will be highlighted in a green color until the user selects
     * any tile.
     * @param actionEvent Relevant context data about the event.
     */
    public void onHintClicked(final ActionEvent actionEvent) {
        Tile[] tiles = game.findMatch();
        if (tiles == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No matches!");
            alert.setContentText("There are no more possible matches. "
                    + "Shuffle the board and try again!");
            alert.showAndWait();
        } else {
            // Find the matching tiles and set them to be green colored.
            for (Node n : root.getChildren()) {
                UITile t = (UITile) n;
                // If this tile is one of the two matches then set
                // the tile to be green.
                if (tiles[0] == t.getTile() || tiles[1] == t.getTile()) {
                    ((PhongMaterial) t.getMaterial())
                            .setDiffuseColor(Color.GREEN);
                }
            }
        }
    }

    /**
     * Called by FXML when the user wants the board to be reshuffled. If
     * there are no shuffles left then a game over or a warning message will
     * be shown based on if there are valid tiles left.
     * @param actionEvent Relevant context data about the event.
     * @throws FileNotFoundException Can be thrown when a texture for a tile
     * can't be found.
     */
    public void onShuffleClicked(final ActionEvent actionEvent)
            throws FileNotFoundException {
        if (game.getShufflesLeft() == 0) {
            if (game.getGameState() == GameState.Lost) {
                lost();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No more shuffles!");
                alert.setContentText("You only get 5 shuffles per game.");
                alert.showAndWait();
            }
        }
        game.shuffle();
        setBoardTiles();
    }

    private void setBoardTiles() throws FileNotFoundException {
        // Get rid of any tiles that where on the board beforehand.
        clearBoardTiles();
        // Get every tile in the game and make it visible on the board.
        for (Tile t : game.getAllTiles()) {
            // Create the new visual representation of the tile.
            UITile tile = new UITile(t);
            tile.setWidth(TILE_WIDTH);
            tile.setHeight(TILE_HEIGHT);
            tile.setDepth(TILE_DEPTH);
            tile.setTranslateX(OFFSET_X + t.getX() * TILE_GAP_X);
            tile.setTranslateY(OFFSET_Y + t.getY() * TILE_GAP_Y);
            tile.setTranslateZ(-t.getZ() * TILE_DEPTH);
            // JavaFX will call our closure when the tile is clicked.
            tile.setOnMouseClicked(e -> {
                // Get rid of all green colored hint tiles
                // when we select something.
                for (Node n : root.getChildren()) {
                    UITile uiT = (UITile) n;
                    if (((PhongMaterial) uiT.getMaterial())
                            .getDiffuseColor() == Color.GREEN) {
                        ((PhongMaterial) uiT.getMaterial())
                                .setDiffuseColor(Color.WHITE);
                    }
                }

                // We already have selected something...
                if (selected != null) {
                    // If it is a match then we can go ahead and get rid of it.
                    if (game.isMatch(selected.getTile(), tile.getTile())) {
                        game.removeTiles(selected.getTile(), tile.getTile());
                    }
                    selected = null;
                    // Rerun all the board setting.
                    try {
                        // This might worry some people because it looks like
                        // we could get stuck in an infinite loop. This is not
                        // possible because we are calling this from a closure
                        // that is called later and not in the score
                        // of setBoardTiles anymore.
                        setBoardTiles();
                    } catch (Exception ex) {
                        throw new RuntimeException("File not found.");
                    }

                    // Look at the state of the game now and see
                    // if we won/lost.
                    if (game.getGameState() == GameState.Won) {
                        won();
                    } else if (game.getGameState() == GameState.Lost) {
                        lost();
                    }
                } else {
                    selected = tile;
                    ((PhongMaterial) tile.getMaterial())
                            .setDiffuseColor(Color.BLUE);
                }
            });
            // Add the tile to the visual board.
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

        TextInputDialog textAlert = new TextInputDialog();
        textAlert.setTitle("Congratulations!");
        textAlert.setContentText("You have removed all the tiles from "
                + "the board! Please enter your name:");

        Optional<String> result = textAlert.showAndWait();
        // Only enter a name to the high score leader if a name was entered.
        try {
            result.ifPresent(s ->
                    leaders.updateLeaderBoard(
                            (TimerEntry.getMinutes() * SECONDS)
                                    + TimerEntry.getSeconds(),
                            s, "leaderboard.txt"));
        } catch (RuntimeException e) {
            showError("Failed to load the leader boards file.");
        }
    }

    private void lost() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You lost!");
        alert.setContentText("You can only reshuffle the board 5 "
                + "times in one game! There are no more possible matches.");
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
