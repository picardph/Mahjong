package mahjong.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import mahjong.gui.MahjongApplication;

import java.io.File;
import java.util.Objects;

/**
 * Gives the user a list of starting puzzles to choose from and then
 * starts the actual game. A fairly simple class that only stores
 * some information passed in by the FXML file.
 */
public class NewMenuController {
	@FXML
	private ListView list;

	/**
	 * Called by the FXML file when we should put the GUI into the starting state. This
	 * should not be called manually. It is only public because the FXML file needs access
	 * to it.
	 */
	@FXML
	public void initialize() {
		MahjongApplication.getPrimary().setWidth(300);
		MahjongApplication.getPrimary().setHeight(275);

		// Find all the puzzle files to read in.
		for (File f : Objects.requireNonNull((new File("Puzzles")).listFiles()))
			list.getItems().add(f.getName());
	}

	/**
	 * Called by FXML when the user selects the start button. The code will look at
	 * which puzzle was selected, pass that information on, and the load the actual
	 * game board class.
	 * @param actionEvent Relevant context data about the event.
	 * @throws Exception Thrown when the loading fails.
	 */
	public void startClicked(ActionEvent actionEvent) throws Exception {
		String path = "Puzzles/" + (String)list.getSelectionModel().getSelectedItem();
		if (list.getSelectionModel().getSelectedItem() != null && (new File(path)).exists()) {
			MahjongApplication.setLoadFile(path);
			MahjongApplication.setRoot(FXMLLoader.load(getClass().getResource("BoardMenu.fxml")), false);
		}
	}
}
