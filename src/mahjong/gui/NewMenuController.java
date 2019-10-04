package mahjong.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import mahjong.gui.MahjongApplication;

import java.io.File;

public class NewMenuController {
	@FXML
	private ListView list;

	@FXML
	public void initialize() {
		// Find all the puzzle files to read in.
		for (File f : (new File("Puzzles")).listFiles())
			list.getItems().add(f.getName());
	}

	public void startClicked(ActionEvent actionEvent) throws Exception {
		if (list.getSelectionModel().getSelectedItem() != null && (new File("Puzzles/" + (String)list.getSelectionModel().getSelectedItem())).exists()) {
			MahjongApplication.setLoadFile((String)list.getSelectionModel().getSelectedItem());
			MahjongApplication.setRoot(FXMLLoader.load(getClass().getResource("BoardMenu.fxml")));
		}
	}
}
