package Mahjong;

public class Tile {

	// Instance variables
	private int x; // x coordinate of top left square that the tile occupies
	private int y; // y coordinate of top left square that the tile occupies
	private int z; // z coordinate of top left square that the tile occupies
	private int ident; // each tile has a specific number
	private TileType type;

	public Tile(int xcoord, int ycoord, int zcoord, int identifier, TileType tileType) {
		x    = xcoord;
		y    = ycoord;
		z    = zcoord;
		ident = identifier;
		type = tileType;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}
}