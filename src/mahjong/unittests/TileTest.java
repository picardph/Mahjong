package mahjong.unittests;

import mahjong.Tile;
import mahjong.TileType;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
		t.setZ(4);
		t.setIdent(5);
		t.setType(TileType.Bam5);
		assertEquals(7, t.getX());
		assertEquals(8, t.getY());
		assertEquals(4, t.getZ());
		assertEquals(5, t.getIdent());
		assertEquals(TileType.Bam5, t.getType());
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest1() {
		Tile a = new Tile(-1,1,1,1,TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest2() {
		Tile a = new Tile(300,1,1,1,TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest3() {
		Tile a = new Tile(1,-1,1,1,TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest4() {
		Tile a = new Tile(1, 300, 1, 1, TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest5() {
		Tile a = new Tile(1, 1, -1, 1, TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest6() {
		Tile a = new Tile(1, 1, 100, 1, TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest7() {
		Tile a = new Tile(1, 1, 1, -1, TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badTileConstructorTest8() {
		Tile a = new Tile(1, 1, 1, 150, TileType.Bam1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest1() {
		t.setX(-1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest2() {
		t.setX(300);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest3() {
		t.setY(-1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest4() {
		t.setY(300);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest5() {
		t.setZ(-1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest6() {
		t.setZ(30);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest7() {
		t.setIdent(-1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void badMutatorTest8() {
		t.setIdent(150);
	}
}
