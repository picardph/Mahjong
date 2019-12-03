package mahjong;

/**
 * Represents a single Mahjong tile.
 * Each tile is of a class (wind, flower, character, etc.) and of a certain
 * type (the specific kind of tile it is). Both must be matching for a tile
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
    public Tile(final int xcoord, final int ycoord, final int zcoord,
                final int identifier, final TileType tileType) {
        if (xcoord < 0 || xcoord > xSize || ycoord < 0
                || ycoord > ySize || zcoord < 0
                || zcoord > zSize || identifier < 0
                || identifier > maxIndentifier) {
            throw new IllegalArgumentException("Input out of range");
        }

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
     * @param newX The x coordinate.
     * @throws IllegalArgumentException Thrown when the input is out of range
     */
    public void setX(final int newX) {
        if (newX < -1 || newX > xSize) {
            throw new IllegalArgumentException("Input out of range");
        }
        x = newX;
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
     * @param newY The y coordinate.
     * @throws IllegalArgumentException Thrown when the input is out of range
     */
    public void setY(final int newY) {
        if (newY < -1 || newY > ySize) {
            throw new IllegalArgumentException("Input out of range");
        }
        y = newY;
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
     * @param newZ The z coordinate.
     * @throws IllegalArgumentException Thrown when the input is out of range
     */
    public void setZ(final int newZ) {
        if (newZ < -1 || newZ > zSize) {
            throw new IllegalArgumentException("Input out of range");
        }
        z = newZ;
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
     * @param newType The new type of tile.
     */
    public void setType(final TileType newType) {
        type = newType;
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
     * @param newIdent The new identifier.
     * @throws IllegalArgumentException Thrown when the input is out of range
     */
    public void setIdent(final int newIdent) {
        if (newIdent < 0 || newIdent > maxIndentifier) {
            throw new IllegalArgumentException("Input out of range");
        }
        ident = newIdent;
    }
}
