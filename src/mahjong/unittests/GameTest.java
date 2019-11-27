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

	@Test
	public void gameConstructorTest() throws InterruptedException {
		String file = "Puzzles\\defaultPuzzle.txt";
		Game constGame = new Game(file);
		assertNotEquals(0, constGame.getAllTiles().length);
		Thread.sleep(2000);
		assertNotEquals(0, TimerEntry.getSeconds());
		assertEquals(5, constGame.getShufflesLeft());
		assertEquals(GameState.InProgress, constGame.getGameState());
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
		Tile[] tiles = new Tile[10];
		tiles = unGame.getAllTiles();
		unGame.removeTiles(unGame.findMatch()[0], unGame.findMatch()[1]);
		unGame.undo();
		assertArrayEquals(unGame.getAllTiles(), tiles);
	}

}
