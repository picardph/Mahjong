package Mahjong.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

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

	public void startClicked(ActionEvent actionEvent) {

	}
}
