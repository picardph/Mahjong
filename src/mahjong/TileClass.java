package mahjong;

/**
 * There are several categories of tiles in Mahjong
 * that are listed here.
 */
public enum TileClass {
	/** A set of nine characters tiles. Numbers must be matching. */
	Character,
	/** A set of nine bamboo tiles. Numbers must be matching. */
	Bamboo,
	/** A set of nine circle tiles. Numbers must be matching. */
	Circle,
	/** Any dragon tile can be matched with any other dragon tile. */
	Dragon,
	/** Any wind tile can be matched with any other wind tile. */
	Wind,
	/** Any season tile can be matched with any other season tile. */
	Season,
	/** Any flower tile can be matched with any other flower tile. */
	Flower,
}
