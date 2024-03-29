package mahjong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the board and the core rules of the
 * game. Also implements loading and saving the game
 * from a text file.
 */
public class Game {

    private final int xSize = 30;
    private final int ySize = 16;
    private final int zSize = 5;
    private final int startShuffles = 5;
    private final int tileCount = 144;

    // Board Array
    private int[][][] board;

    // Hashmap matches tile reference to a specific identifier
    private HashMap<Integer, Tile> tileIdentifiers;

    // Integer that holds the amount of shuffles the player can still use.
    private int shufflesLeft;

    // Stack object to hold the tiles that the player has removed.
    private Stack<Tile> removedTiles;

    /**
     * Creates a new game from the passed in template file.
     * @param template The path to the template to use.
     */
    public Game(final String template) {
        board = new int[xSize][ySize][zSize];
        tileIdentifiers = new HashMap<Integer, Tile>();
        removedTiles = new Stack<Tile>();
        shufflesLeft = startShuffles;
        loadGame(template);
    }

    private void loadGame(final String filein) {

        // temporary Tile object
        Tile tempTile;

        // variables to increment for loops
        int i;
        int j;
        int k;

        // String array stores information from 1 line of the file
        String[] positionInfo;

        //creates scanner and an input stream
        StringBuffer buffer = new StringBuffer();

        //reads in the information from the file
        try {

            final int xIndex = 0;
            final int yIndex = 1;
            final int zIndex = 2;
            final int idIndex = 3;
            final int typeIndex = 4;

            BufferedReader reader = new BufferedReader(new FileReader(filein));

            String line = reader.readLine();
            positionInfo = line.split(",");
            TimerEntry.set(Integer.parseInt(positionInfo[1]),
                    Integer.parseInt(positionInfo[0]));
            shufflesLeft = Integer.parseInt(positionInfo[2]);

            // increments height
            for (i = 0; i < zSize; i++) {
                // increments y
                for (j = 0; j < ySize; j++) {
                    // increments x
                    for (k = 0; k < xSize; k++) {
                        // holds one line from the file
                        line = reader.readLine();
                        if (line.equals("null")) {
                            board[k][j][i] = 0;
                        } else {
                            positionInfo = line.split(",");
                            // stores identifier in board
                            board[k][j][i] =
                                    Integer.parseInt(positionInfo[idIndex]);
                            // stores the information in a temporary tile
                            tempTile = new Tile(Integer.parseInt(
                                    positionInfo[xIndex]), // x
                                    // y
                                    Integer.parseInt(positionInfo[yIndex]),
                                    // z
                                    Integer.parseInt(positionInfo[zIndex]),
                                    // identifier
                                    Integer.parseInt(positionInfo[idIndex]),
                                    // type
                                    TileType.valueOf(positionInfo[typeIndex]));

                            if (tempTile.getX() != k || tempTile.getY() != j
                                    || tempTile.getZ() != i) {
                                throw new IllegalArgumentException(
                                        "Illegal template file passed in.");
                            }

                            if (tempTile.getIdent() > tileCount) {
                                throw new IllegalArgumentException(
                                        "Illegal template file passed in.");
                            }

                            // add the tile if the key doesn't already exist
                            if (!tileIdentifiers.containsKey(
                                    tempTile.getIdent())) {
                                tileIdentifiers.put(tempTile.getIdent(),
                                        tempTile);
                            }
                        }
                    }
                }
            }

            //runs if there is a problem with the file
        } catch (IOException error1) {
            throw new IllegalArgumentException(
                    "Illegal template file passed in.");
        }

        if (shufflesLeft == startShuffles + 1) {
            shuffle();
        }
    }

    /**
     * Add a tile to the board. Make sure that you add another matching
     * tile otherwise the board will become unbeatable.
     * @param t The instance of the tile to add.
     */
    public void addTile(final Tile t) {
        int x = t.getX();
        int y = t.getY();
        int z = t.getZ();
        int id = t.getIdent();

        board[x][y][z] = id;
        board[x + 1][y][z] = id;
        board[x][y + 1][z] = id;
        board[x + 1][y + 1][z] = id;

        tileIdentifiers.put(board[x][y][z], t);
    }

    private void removeTile(final Tile t) {
        // Add the tile to the removed tiles stack
        removedTiles.push(t);

        int x = t.getX();
        int y = t.getY();
        int z = t.getZ();

        tileIdentifiers.remove(board[x][y][z]);
        tileIdentifiers.remove(board[x + 1][y][z]);
        tileIdentifiers.remove(board[x][y + 1][z]);
        tileIdentifiers.remove(board[x + 1][y + 1][z]);

        board[x][y][z] = 0;
        board[x + 1][y][z] = 0;
        board[x][y + 1][z] = 0;
        board[x + 1][y + 1][z] = 0;
    }

    /**
     * Undos an operation that was done on the board. Will do nothing
     * if no operation was done.
     */
    public void undo() {
        for (int i = 0; i < 2; i++) {
            if (removedTiles.isEmpty()) {
                return;
            }

            Tile t = removedTiles.pop();

            int x = t.getX();
            int y = t.getY();
            int z = t.getZ();
            int ident = t.getIdent();

            board[x][y][z] = ident;
            board[x + 1][y][z] = ident;
            board[x][y + 1][z] = ident;
            board[x + 1][y + 1][z] = ident;

            tileIdentifiers.put(board[x][y][z], t);
        }

    }

    /**
     * Takes the board and game state and outputs it to a text file in
     * such a way that it can be loaded again at a later time.
     * @param fileout The path of the tile to write to.
     * @throws IOException Thrown when unable to write to the file.
     */
    public void saveGame(final String fileout) throws IOException {

        // variables to increment for loops
        int i;
        int j;
        int k;

        // create new writer to write in the save file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileout));

        // include the number of shuffles left
        writer.write(TimerEntry.getMinutes() + "," + TimerEntry.getSeconds()
                + "," + shufflesLeft + "\n");

        // increments height
        for (i = 0; i < zSize; i++) {
            // increments y
            for (j = 0; j < ySize; j++) {
                // increments x
                for (k = 0; k < xSize; k++) {

                    // if the position is empty
                    if (board[k][j][i] == 0) {
                        writer.write("null\n");
                    // if the position is not empty
                    } else {
                        // write to file in order: x,y,z,identifier,type
                        writer.write(k + "," + j + "," + i + ","
                                + tileIdentifiers.get(board[k][j][i]).getIdent()
                                + "," + tileIdentifiers.get(board[k][j][i])
                                .getType() + "\n");
                    }
                }
            }
        }

        writer.close();
    }

    /**
     * Get the tile at a specific position on the board. Remember that a
     * tile is 2x2 in size so the coordinates are not a one-to-one mapping
     * to the order of tiles. (0,0,0) is the top left and the top.
     * @param x The x coordinate. Must be > 0 and < then the board size.
     * @param y The y coordinate. Must be > 0 and < then the board size.
     * @param z The z coordinate. Must be > 0 and < then the board size.
     * @return The tile found or null if the passed
     * in coordinates are out of range.
     */
    public Tile getTile(final int x, final int y, final int z) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize || z < 0 || z >= zSize) {
            return null;
        }
        return  tileIdentifiers.get(board[x][y][z]);
    }

    /**
     * Checks to see if the tile could even be removed. A tile must have no
     * neighbors to the left or to the right in order to be removed. This
     * method checks to see if that is the case.
     * @param t The tile to look at.
     * @return True if the tile could be removed and otherwise false.
     */
    public boolean isValidTile(final Tile t) {
        if (t == null) {
            return false;
        }

        boolean sideFree = false;
        boolean topFree = false;
        int x = t.getX();
        int y = t.getY();
        int z = t.getZ();

        // Check if left side is free, then right side,
        // and if either is free, set sidefree to true
        // Left side check
        /* left of the top left square */
        if ((getTile(x - 1, y, z) == null
                /* left of the bottom left square */
            && getTile(x - 1, y + 1, z) == null)
            // Right side check
                /* right of the top right square */
            || (getTile(x + 2, y, z) == null
                /* right of the bottom right square */
            && getTile(x + 2, y + 1, z) == null)) {
            sideFree = true;
        }

        final int depth = 5;

        // Check all four squares above the tile, and set topfree
        // to true if they are all free
        // If the tile is at the top of the board, its top must be free
        if (z + 1 >= board[0][0].length) {
            topFree = true;
        } else if (z + 1 < depth && getTile(x, y, z + 1) == null
                /* above the bottom left square */
            && getTile(x, y + 1, z + 1) == null
                /* above the top right square */
            && getTile(x + 1, y, z + 1) == null
                /* above the bottom right square */
            && getTile(x + 1, y + 1, z + 1) == null) {
            topFree = true;
        }

        return (sideFree && topFree);
    }

    /**
     * Checks to see if two tiles are of the same type and therefor
     * are matching. It does not check if those two tiles are valid (meaning
     * that the sides are clear).
     * @param t1 The first tile.
     * @param t2 The second tile to look at.
     * @return True if they are matching, otherwise false.
     */
    public boolean isMatch(final Tile t1, final Tile t2) {
        // Makes sure the tile is not being checked against itself
        if (t1 == null || t2 == null) {
            return false;
        }
        // True if the tile type is the same
        return t1.getType() == t2.getType() && t1 != t2;
    }

    /**
     * Removes a pair of tiles as long as they are matching and
     * are both valid. Otherwise, nothing happens.
     * @param t1 The first tile to look at.
     * @param t2 The second tile to look at.
     */
    public void removeTiles(final Tile t1, final Tile t2) {
        if (isMatch(t1, t2) && isValidTile(t1) && isValidTile(t2)) {
            removeTile(t1);
            removeTile(t2);
        }
    }

    /**
     * Reshuffles the board. The shape of the board will stay the same
     * but the specific tile that is at each location will change. The user
     * only gets five shuffles per game. After that, the shuffle method
     * will do nothing.
     */
    public void shuffle() {

        // Don't shuffle if there are none left.
        if (shufflesLeft == 0) {
            return;
        }

        int i;
        int tempIdent;
        ArrayList<Integer> shuffleIdents = new ArrayList<>();
        ArrayList<Tile> shuffleTiles = new ArrayList<>();
        Tile tempTile;
        removedTiles.clear();

        // puts existing tile identifiers and Tiles into ArrayList
        for (i = 1; i < (tileCount + 1); i++) {
            // if the tile is in the HashMap
            if (tileIdentifiers.containsKey(i)) {
                shuffleIdents.add(i);
                shuffleTiles.add(tileIdentifiers.get(i));
            }
        }

        // shuffle the two ArrayLists
        Collections.shuffle(shuffleIdents);
        Collections.shuffle(shuffleTiles);

        // add identifiers in a new order and remove old
        for (i = 0; i < shuffleTiles.size(); i++) {
            // store values in temporary variables
            tempIdent = shuffleIdents.get(i);
            tempTile = shuffleTiles.get(i);
            // set tile identifier to the key in the HashMap
            tempTile.setIdent(tempIdent);
            // remove the identifier from the HashTable
            tileIdentifiers.remove(tempIdent);
            // add the identifier back in but paired with a new Tile object
            tileIdentifiers.put(tempIdent, tempTile);
        }

        // update coordinates of tiles so they match their
        // positions in board array

        int j;
        int k;

        // stores identifiers of tiles that have been updated
        ArrayList<Integer> updatedCoords = new ArrayList<>();

        // loop through board
        for (i = 0; i < zSize; i++) {
            for (j = 0; j < ySize; j++) {
                for (k = 0; k < xSize; k++) {
                    if (board[k][j][i] != 0) {
                        // ensure that only top left coordinate is updated
                        // since there are 4 positions per tile
                        if (!updatedCoords.contains(board[k][j][i])) {
                            updatedCoords.add(board[k][j][i]);
                        }
                        // update coordinates of tiles
                        tileIdentifiers.get(board[k][j][i]).setX(k - 1);
                        tileIdentifiers.get(board[k][j][i]).setY(j - 1);
                        tileIdentifiers.get(board[k][j][i]).setZ(i);
                    }
                }
            }
        }
        shufflesLeft--;
    }

    /**
     * This method will find a pair of tiles that can be removed. Used to
     * determine if the game can no longer be beaten or for the user to get
     * a hint.
     * @return An array with two values for the match or null if none was found.
     */
    public Tile[] findMatch() {
        for (Tile t1 : tileIdentifiers.values()) {
            for (Tile t2 : tileIdentifiers.values()) {
                if (isMatch(t1, t2) && isValidTile(t1) && isValidTile(t2)) {
                    return new Tile[]{t1, t2};
                }
            }
        }
        // If there are no valid tiles or no valid matches, return null.
        return null;
    }

    /**
     * Determine what the current state of the game is. Used to determine if the
     * game is won, lost, in progress, etc.
     * @return The state of the game.
     */
    public GameState getGameState() {
        if (tileIdentifiers.isEmpty() && !removedTiles.isEmpty()) {
            return GameState.Won;
        }
        if (findMatch() == null && shufflesLeft == 0) {
            return GameState.Lost;
        }
        if (findMatch() == null && shufflesLeft > 0) {
            return GameState.Stuck;
        }
        return GameState.InProgress;
    }

    /**
     * Get the number of shuffles the user has left. Each game
     * starts with five shuffles.
     * @return An integer number.
     */
    public int getShufflesLeft() {
        return shufflesLeft;
    }

    /**
     * Get a list of all the tiles that are left and need
     * to be removed.
     * @return An array of tiles. The array will be empty if there are no tiles.
     */
    public Tile[] getAllTiles() {
        return tileIdentifiers.values().toArray(new Tile[0]);
    }
}
