package mahjong.unittests;

import mahjong.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GameTest {
	@Test
	public void badFileNameTest() {
		Game g = new Game("bad input");
		assertEquals(0, g.getAllTiles().length);
	}

	@Test
	public void gameConstructorTest() throws InterruptedException {
		String file = "Puzzles\\defaultPuzzle.txt";
		Game constGame = new Game(file);
		TimerEntry t = new TimerEntry();
		assertNotEquals(0, constGame.getAllTiles().length);
		Thread.sleep(2000);
		assertNotEquals(0, t.getSeconds());
		assertEquals(5, constGame.getShufflesLeft());
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
}
