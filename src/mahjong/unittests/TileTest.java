package mahjong.unittests;

import mahjong.Tile;
import mahjong.TileType;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

	private Tile t = new Tile(3, 5, 1, 0, TileType.Cir2);

	@Test
	public void tileConstructorTest() {
		assertEquals(3, t.getX());
		assertEquals(5, t.getY());
		assertEquals(1, t.getZ());
		assertEquals(0, t.getIdent());
		assertEquals(TileType.Cir2, t.getType());
	}

	@Test
	public void tileMutatorTest() {
		t.setX(7);
		t.setY(8);
		t.setZ(9);
		t.setIdent(5);
		t.setType(TileType.Bam5);
		assertEquals(7, t.getX());
		assertEquals(8, t.getY());
		assertEquals(9, t.getZ());
		assertEquals(5, t.getIdent());
		assertEquals(TileType.Bam5, t.getType());
	}
}
