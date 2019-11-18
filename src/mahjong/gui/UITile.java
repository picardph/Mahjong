package mahjong.gui;

import javafx.scene.shape.Box;
import mahjong.Tile;

public class UITile extends Box {

	private Tile tile;

	public UITile(Tile tile) {
		this.tile = tile;
	}
}
