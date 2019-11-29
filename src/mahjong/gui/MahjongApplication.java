package mahjong.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import mahjong.TimerHelper;

import java.util.Timer;

/**
 * The entry point for the application is here. It also holds a reference
 * to the root window that contains our GUI. This class is used to coordinate
 * the opening and closing of different windows and transferring state
 * information between those windows.
 */
public class MahjongApplication extends Application {

	private static Parent root = null;
	private static Stage primary = null;

	private static String loadFile = null;
	private static Timer timer = new Timer();
	private static TimerHelper helper = new TimerHelper();

	private static final int MILLISECONDS = 1000;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 275;

	/**
	 * The main entry point called by the Java VM when
	 * the program is launched.
	 * @param args Arguments passed in from the command line.
	 */
	public static void main(final String[] args) {
		try {
			timer.schedule(helper, MILLISECONDS, MILLISECONDS);
			// The launch method lets JavaFX take control of our
			// core event loop.
			launch(args);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fatal Exception!");
			alert.setHeaderText(
					"The program encountered an unexpected exception.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Get the root window that holds the GUI node-tree.
	 * @return The root window object.
	 */
	public static Parent getRoot() {
		return root;
	}

	/**
	 * Change the root object so that a different node is displayed in the
	 * window.
	 * @param newRoot The new node to put in the window.
	 * @param resizable Should the window be allowed to resize?
	 */
	public static void setRoot(final Parent newRoot,
	                           final boolean resizable) {
		root = newRoot;
		primary.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
		primary.setResizable(resizable);
		primary.setTitle("mahjong");
	}

	/**
	 * Get the path to the file that the user wants to open.
	 * Stored here to be transferred between
	 * NewMenuController and BoardMenuController.
	 * @return A string containing the file path.
	 */
	public static String getLoadFile() {
		return loadFile;
	}

	/**
	 * Set the path to the file that the user has requested to open.
	 * @param file A string containing the file path.
	 */
	public static void setLoadFile(final String file) {
		loadFile = file;
	}

	/**
	 * Get the actual window and not the root element in that window.
	 * @return The window/stage object.
	 */
	public static Stage getPrimary() {
		return primary;
	}

	/**
	 * Called by JavaFX when we are to construct our initial starting GUI
	 * and put the game into a starting state.
	 * @param primaryStage The window that wil host our game.
	 * @throws Exception Thrown when two games are launched.
	 */
	@Override
	public void start(final Stage primaryStage) throws Exception {
		if (root != null) {
			throw new Exception("Can't run two UI instances at once.");
		}

		primary = primaryStage;
		root = FXMLLoader.load(getClass().getResource("NewMenu.fxml"));
		primaryStage.setTitle("mahjong");
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
		// JavaFX can be funny about window closing.
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.show();
	}
}
