package mahjong.unittests;

import mahjong.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GameTest {
	@Test(expected = IllegalArgumentException.class)
	public void badFileNameTest() {
		Game g = new Game("bad input");
	}

	@Test(expected = IllegalArgumentException.class)
	public void badIdentifierTest() {
		Game g = new Game("TestTemplates\\BadTest.txt");
	}

	@Test
	public void gameConstructorTest() throws InterruptedException {
		Game constGame = new Game("Puzzles\\defaultPuzzle.txt");
		assertNotEquals(0, constGame.getAllTiles().length);
		Thread.sleep(2000);
		assertNotEquals(0, TimerEntry.getSeconds());
		assertEquals(5, constGame.getShufflesLeft());
		assertEquals(GameState.InProgress, constGame.getGameState());
	}

	@Test
	public void shuffleTest() {
		Game shGame = new Game("Puzzles\\defaultPuzzle.txt");
		Tile[] before = shGame.getAllTiles();
		for (int i = 0; i < 5; i++)
			shGame.shuffle();
		assertNotEquals(shGame.getAllTiles(), before);
		Tile[] middle = shGame.getAllTiles();
		shGame.shuffle();
		assertArrayEquals(shGame.getAllTiles(), middle);
	}

	@Test
	public void matchTest() {
		Game mtGame = new Game("TestTemplates\\Test1.txt");
		Tile t1 = null;
		Tile t2 = null;
		assertEquals(false, mtGame.isMatch(t1, t2));

		t1 = new Tile(5,6,1,4,TileType.Bam9);
		t2 = new Tile(7,8,1,5,TileType.Bam9);
		mtGame.addTile(t1);
		mtGame.addTile(t2);
		assertEquals(true, mtGame.isMatch(t1, t2));

		t2.setType(TileType.Bam5);
		assertEquals(false, mtGame.isMatch(t1,t2));
	}

	@Test
	public void gameStateTest() {
		Game stGame = new Game("TestTemplates\\Test1.txt");
		assertEquals(GameState.InProgress, stGame.getGameState());
		stGame.removeTiles(stGame.findMatch()[0], stGame.findMatch()[1]);
		assertEquals(GameState.Won, stGame.getGameState());
		Game badGame = new Game("TestTemplates\\Test2.txt");
		assertEquals(GameState.Stuck, badGame.getGameState());
		for (int i = 0; i < 5; i++)
			badGame.shuffle();
		assertEquals(GameState.Lost, badGame.getGameState());
	}

	@Test
	public void saveLoadTest() throws IOException {
		Game IOGame = new Game("TestTemplates\\Test3.txt");
		IOGame.removeTiles(IOGame.findMatch()[0], IOGame.findMatch()[1]);
		IOGame.saveGame("TestTemplates\\testout.txt");
		Game ngGame = new Game("TestTemplates\\testout.txt");
		assertEquals(2, ngGame.getAllTiles().length);
	}

	@Test
	public void undoTest() {
		Game unGame = new Game ("TestTemplates\\Test3.txt");
		Tile[] tiles = unGame.getAllTiles();
		unGame.undo();
		assertArrayEquals(unGame.getAllTiles(), tiles);
		unGame.removeTiles(unGame.findMatch()[0], unGame.findMatch()[1]);
		unGame.undo();
		assertArrayEquals(unGame.getAllTiles(), tiles);
		unGame.removeTiles(unGame.findMatch()[0], unGame.findMatch()[1]);
		unGame.shuffle();
		Tile[] tiles2 = unGame.getAllTiles();
		unGame.undo();
		assertArrayEquals(tiles2, unGame.getAllTiles());
	}

	@Test
	public void validTileTest() {
		Game vtGame = new Game("TestTemplates\\Test1.txt");
		Tile t1 = new Tile(3,3,4,11,TileType.Bam1);
		Tile t3 = new Tile(7,7,3,12,TileType.Bam1);
		Tile t4 = new Tile(9,6,3,13,TileType.Bam1);
		Tile t5 = new Tile(11,7,3,14,TileType.Bam1);
		Tile t6 = new Tile(10,6,4,15,TileType.Bam1);
		vtGame.addTile(t1);
		vtGame.addTile(t3);
		vtGame.addTile(t4);
		vtGame.addTile(t5);
		vtGame.addTile(t6);
		assertTrue(vtGame.isValidTile(t1));
		assertFalse(vtGame.isValidTile(t4));
		assertFalse(vtGame.isValidTile(t5));
	}
}
