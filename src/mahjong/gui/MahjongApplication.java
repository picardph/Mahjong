package mahjong.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MahjongApplication extends Application {

	private static Parent root = null;
	private static Stage primary = null;

	private static String loadFile = null;

	public static void main(String[] args) {
		try {
			launch(args);
		}
		// Catch any exceptions that snuck their way up here.
		catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fatal Exception!");
			alert.setHeaderText("The program encountered an unexpected exception.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public static Parent getRoot() {
		return root;
	}

	public static void setRoot(Parent newRoot, boolean resizable) {
		root = newRoot;
		primary.setScene(new Scene(root, 300, 275));
		primary.setResizable(resizable);
	}

	public static String getLoadFile() {
		return loadFile;
	}

	public static void setLoadFile(String file) {
		loadFile = file;
	}

	public static Stage getPrimary() {
		return primary;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (root != null)
			throw new Exception("Can't run two UI instances at once.");

		primary = primaryStage;
		root = FXMLLoader.load(getClass().getResource("NewMenu.fxml"));
		primaryStage.setTitle("mahjong");
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
	}
}
