package mahjong.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import mahjong.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A custom JavaFX node that wraps up the underlying game's tile class so
 * that is displayed in 3D with a texture.
 */
public class UITile extends Box {

	private Tile tile;

	/**
	 * Create a new visual tile. It will not be added to the visual board
	 * state or to the underlying representation until manually done so.
	 * @param tile The underlying tile to wrap up.
	 * @throws FileNotFoundException Thrown when the texture can't be loaded.
	 */
	public UITile(Tile tile) throws FileNotFoundException {
		this.tile = tile;

		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseMap(new Image(new FileInputStream(getTileImagePath(tile))));
		setMaterial(mat);
	}

	/**
	 * Get the underlying tile from the game that is being represented
	 * by this element.
	 * @return The underlying tile.
	 */
	public Tile getTile() {
		return tile;
	}

	private static String getTileImagePath(Tile t) {
		// Map each tile to a file path to the image to represent that tile.
		switch (t.getType()) {
			case Ch1:
				return "images/spr_characters_0.png";
			case Ch2:
				return "images/spr_characters_1.png";
			case Ch3:
				return "images/spr_characters_2.png";
			case Ch4:
				return "images/spr_characters_3.png";
			case Ch5:
				return "images/spr_characters_4.png";
			case Ch6:
				return "images/spr_characters_5.png";
			case Ch7:
				return "images/spr_characters_6.png";
			case Ch8:
				return "images/spr_characters_7.png";
			case Ch9:
				return "images/spr_characters_8.png";
			case Bam1:
				return "images/spr_bamboos_0.png";
			case Bam2:
				return "images/spr_bamboos_1.png";
			case Bam3:
				return "images/spr_bamboos_2.png";
			case Bam4:
				return "images/spr_bamboos_3.png";
			case Bam5:
				return "images/spr_bamboos_4.png";
			case Bam6:
				return "images/spr_bamboos_5.png";
			case Bam7:
				return "images/spr_bamboos_6.png";
			case Bam8:
				return "images/spr_bamboos_7.png";
			case Bam9:
				return "images/spr_bamboos_8.png";
			case Cir1:
				return "images/spr_circles_0.png";
			case Cir2:
				return "images/spr_circles_1.png";
			case Cir3:
				return "images/spr_circles_2.png";
			case Cir4:
				return "images/spr_circles_3.png";
			case Cir5:
				return "images/spr_circles_4.png";
			case Cir6:
				return "images/spr_circles_5.png";
			case Cir7:
				return "images/spr_circles_6.png";
			case Cir8:
				return "images/spr_circles_7.png";
			case Cir9:
				return "images/spr_circles_8.png";
			case Dra1:
				return "images/spr_dragons_0.png";
			case Dra2:
				return "images/spr_dragons_1.png";
			case Dra3:
				return "images/spr_dragons_2.png";
			case Wind1:
				return "images/spr_winds_0.png";
			case Wind2:
				return "images/spr_winds_1.png";
			case Wind3:
				return "images/spr_winds_2.png";
			case Wind4:
				return "images/spr_winds_3.png";
			case Sea:
				return "images/spr_seasons_0.png";
			case Flo:
				return "images/spr_flowers_0.png";
			default: // Really not possible but makes the compiler stop complaining.
				return "";
		}
	}
}
