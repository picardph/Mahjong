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
	private final int xSize = 30;
	private final int ySize = 16;
	private final int zSize = 5;
	private final int maxIndentifier = 144;

	/**
	 * Create a new tile and fill it with some starting information.
	 * @param xcoord The top-left x coordinate.
	 * @param ycoord The top-left y coordinate.
	 * @param zcoord The z coordinate (tile is only one unit high).
	 * @param identifier A unique identifying number for the tile.
	 * @param tileType The specific type of tile that this is.
	 * @throws IllegalArgumentException Thrown when an input is out of range
	 */
	public Tile(int xcoord, int ycoord, int zcoord, int identifier, TileType tileType) {
		if (xcoord < -1 || xcoord > xSize || ycoord < -1 || ycoord > ySize || zcoord < -1
				|| zcoord > zSize || identifier < 0 || identifier > maxIndentifier)
			throw new IllegalArgumentException("Input out of range");

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
	 * @throws IllegalArgumentException Thrown when the input is out of range
	 */
	public void setX(int x) {
		if (x < -1 || x > xSize)
			throw new IllegalArgumentException("Input out of range");
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
	 * @throws IllegalArgumentException Thrown when the input is out of range
	 */
	public void setY(int y) {
		if (y < -1 || y > ySize)
			throw new IllegalArgumentException("Input out of range");
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
	 * @throws IllegalArgumentException Thrown when the input is out of range
	 */
	public void setZ(int z) {
		if (z < -1 || z > zSize)
			throw new IllegalArgumentException("Input out of range");
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
	 * @throws IllegalArgumentException Thrown when the input is out of range
	 */
	public void setIdent(int ident) {
		if (ident < 0 || ident > maxIndentifier)
			throw new IllegalArgumentException("Input out of range");
		this.ident = ident;
	}
}