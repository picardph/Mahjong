package mahjong.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MahjongApplication extends Application {

	private static Parent root;
	private static Stage primary;

	private static String loadFile;

	public static void main(String[] args) {
		launch(args);
	}

	public static Parent getRoot() {
		return root;
	}

	public static void setRoot(Parent newRoot) {
		root = newRoot;
		primary.setScene(new Scene(root, 300, 275));
	}

	public static String getLoadFile() {
		return loadFile;
	}

	public static void setLoadFile(String file) {
		loadFile = file;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (root != null)
			throw new Exception("Can't run two UI instances at once.");

		primary = primaryStage;
		root = FXMLLoader.load(getClass().getResource("NewMenu.fxml"));
		primaryStage.setTitle("mahjong");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
	}
}
