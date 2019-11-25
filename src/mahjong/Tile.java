package mahjong;

/**
 * Represents a single Mahjong tile. Each tile is of a class (wind, flower, character, etc.)
 * and of a certain type (the specific kind of tile it is). Both must be matching for a tile
 * to be a match. Each tile is 2x2 in size.
 */
public class Tile {

	// Instance variables
	private int x; // x coordinate of top left square that the tile occupies
	private int y; // y coordinate of top left square that the tile occupies
	private int z; // z coordinate of top left square that the tile occupies
	private int ident; // each tile has a specific number
	private TileType type;

	/**
	 * Create a new tile and fill it with some starting information.
	 * @param xcoord The top-left x coordinate.
	 * @param ycoord The top-left y coordinate.
	 * @param zcoord The z coordinate (tile is only one unit high).
	 * @param identifier A unique identifying number for the tile.
	 * @param tileType The specific type of tile that this is.
	 */
	public Tile(int xcoord, int ycoord, int zcoord, int identifier, TileType tileType) {
		x    = xcoord;
		y    = ycoord;
		z    = zcoord;
		ident = identifier;
		type = tileType;
	}

	/**
	 * Get the x coordinate of the top-left corner.
	 * @return X coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set the x coordinate of the top-left corner.
	 * @param x The x coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get the y coordinate of the top-left corner.
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the y coordinate of the top-left corner.
	 * @param y The y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the depth (z coordinate) of the tile.
	 * @return The z coordinate.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Set the depth (z coordinate) of the tile.
	 * @param z The z coordinate.
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Get the specific kind of tile that this is.
	 * @return The type of tile.
	 */
	public TileType getType() {
		return type;
	}

	/**
	 * Change the kind of tile that this is. Be careful that there
	 * is a matching pair as well or the board will be unbeatable.
	 * @param type The new type of tile.
	 */
	public void setType(TileType type) {
		this.type = type;
	}

	/**
	 * Get the unique identifier for this object.
	 * @return A uniquely identifying number.
	 */
	public int getIdent() {
		return ident;
	}

	/**
	 * Change the uniquely identifying number.
	 * @param ident The new identifier.
	 */
	public void setIdent(int ident) {
		this.ident = ident;
	}
}