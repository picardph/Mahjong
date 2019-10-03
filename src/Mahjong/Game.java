package Mahjong;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import Mahjong.*;

public class Game {

    Tile[][][] board;

    public Game(String template) {
        board = new Tile[30][16][5];
        loadGame(template);
    }

	private void loadGame(String filein) {
	}

    public void saveGame(String fileout) {

    }

    private Tile getTile(int x, int y, int z) {
        if (board[x][y][z] != null)
            return board[x][y][z];
        return null;
    }

    public boolean isValidTile(Tile t) {
        boolean sideFree = false;
        boolean topFree = false;
        int x = t.getX();
        int y = t.getY();
        int z = t.getZ();

            // Check if left side is free, then right side, and if either is free, set sidefree to true
            // Left side check
        if ((getTile(x-1,y,z) != null /* left of the top left square */ &&
             getTile(x-1,y+1,z) != null /* left of the bottom left square */) ||
            // Right side check
            (getTile(x+2,y,z) != null /* right of the top right square */ &&
             getTile(x+2,y+1,z) != null /* right of the bottom right square */))
                sideFree = true;

            // Check all four squares above the tile, and set topfree to true if they are all free
        if (getTile(x,y,z+1) != null /* above the top left square */ &&
            getTile(x,y+1,z+1) != null /* above the bottom left square */ &&
            getTile(x+1,y,z+1) != null /* above the top right square */ &&
            getTile(x+1,y+1,z+1) != null /* above the bottom right square */)
                topFree = true;

        if (sideFree && topFree)
            return true;
        return false;
    }

    public boolean isMatch(Tile t1, Tile t2) {
        if (t1.getType() == t2.getType() && t1 != t2)
            return true;
        return false;
    }

    public void removeTiles(Tile t1, Tile t2) {
        int x1 = t1.getX();
        int y1 = t1.getY();
        int z1 = t1.getZ();
        int x2 = t2.getX();
        int y2 = t2.getY();
        int z2 = t2.getZ();

        board[x1][y1][z1] = null;
        board[x1+1][y1][z1] = null;
        board[x1][y1+1][z1] = null;
        board[x1+1][y1+1][z1] = null;

        board[x2][y2][z2] = null;
        board[x2+1][y2][z2] = null;
        board[x2][y2+1][z2] = null;
        board[x2+1][y2+1][z2] = null;
    }

    public void shuffle() {

    }

    private Tile[] findMatch() {
        return null;
    }

    public Tile[] getHint() {
        return null;
    }

    public GameState getGameState() {
        return null;
    }
}