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
		String file = "C:\\Users\\sjsis\\IdeaProjects\\Mahjong\\Puzzles\\defaultPuzzle.txt";
		Game constGame = new Game(file);
		TimerEntry t = new TimerEntry();
		assertNotEquals(0, constGame.getAllTiles().length);
		Thread.sleep(2000);
		assertNotEquals(0, t.getSeconds());
		assertEquals(6, constGame.getShufflesLeft());
		assertNotEquals(0, constGame.getTileIdentifiers().size());
		assertEquals(0, constGame.getRemovedTiles().size());
		assertEquals(GameState.InProgress, constGame.getGameState());
	}

	@Test
	public void gameStateTest() {
		Game stGame = new Game("C:\\Users\\sjsis\\IdeaProjects\\Mahjong\\Puzzles\\TestPuzzle.txt");
		assertEquals(GameState.InProgress, stGame.getGameState());
		stGame.removeTiles(stGame.findMatch()[0], stGame.findMatch()[1]);
		assertEquals(GameState.Won, stGame.getGameState());
		Tile t3 = new Tile(11,11,4,3, TileType.Bam1);
		Tile t4 = new Tile(7,7,1,4, TileType.Bam2);
		stGame.addTile(t3);
		stGame.addTile(t4);
		assertEquals(GameState.Stuck, stGame.getGameState());
		System.out.println(stGame.getShufflesLeft());
		for (int i = 0; i < 6; i++)
			stGame.shuffle();
		assertEquals(GameState.Lost, stGame.getGameState());
	}
}
