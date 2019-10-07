package mahjong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {

    // Board Array
    int[][][] board;

    // Hashmap matches tile reference to a specific identifier
    HashMap<Integer, Tile> tileIdentifiers = new HashMap<Integer, Tile>();

    public Game(String template) {
        board = new int[30][16][5];
        loadGame(template);
    }

	private void loadGame(String filein) {

        // temporary Tile object
        Tile tempTile;

        // variables to increment for loops
        int i, j, k;

        // String array stores information from 1 line of the file
        String[] positionInfo = new String[5];

        //creates scanner and an input stream
        StringBuffer buffer = new StringBuffer();

        //reads in the information from the file
        try {

            BufferedReader reader = new BufferedReader(new FileReader(filein));

            // increments height
            for(i = 0; i < 5; i++){
                // increments y
                for(j = 0; j < 16; j++) {
                    // increments x
                    for(k = 0; k < 30; k++) {
                        // holds one line from the file
                        String line = reader.readLine();
                        if(line.equals("null")) {
                            board[k][j][i] = 0;
                        }
                        else {
                            positionInfo = line.split(",");
                            // stores identifier in board
                            board[k][j][i] = Integer.parseInt(positionInfo[3]);
                            // stores the information in a temporary tile
                            tempTile = new Tile(Integer.parseInt(positionInfo[0]), // x
                                    Integer.parseInt(positionInfo[1]), // y
                                    Integer.parseInt(positionInfo[2]), // z
                                    Integer.parseInt(positionInfo[3]), // identifier
                                    TileType.valueOf(positionInfo[4])); // type

                            // add the tile if the key doesn't already exist
                            if(!tileIdentifiers.containsKey(tempTile.getIdent())){
                                tileIdentifiers.put(tempTile.getIdent(), tempTile);
                            }
                        }
                    }
                }
            }
        }

        //runs if there is a problem with the file
        catch (IOException error1) {
            System.out.println("Error related to: " + filein);
        }
	}

	private void removeTile(Tile t) {
    }

    public void saveGame(String fileout) {

    }

    public Tile getTile(int x, int y, int z) {
        if (tileIdentifiers.get(board[x][y][z]) != null)
            return tileIdentifiers.get(board[x][y][z]);
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
        if (isMatch(t1, t2) && isValidTile(t1) && isValidTile(t2)) {
            removeTile(t1);
            removeTile(t2);
        }
    }

    public void shuffle() {

        int i, tempIdent;
        ArrayList<Integer> shuffleIdents = new ArrayList<>();
        ArrayList<Tile> shuffleTiles = new ArrayList<>();
        Tile tempTile;

        // puts existing tile identifiers and Tiles into ArrayList
        for(i = 0; i < 144; i++) {
            // if the tile is in the HashMap
            if(tileIdentifiers.containsKey(i)){
                shuffleIdents.add(i);
                shuffleTiles.add(tileIdentifiers.get(i));
            }
        }

        // shuffle the two ArrayLists
        Collections.shuffle(shuffleIdents);
        Collections.shuffle(shuffleTiles);

        // add identifiers in a new order and remove old
        for(i = 0; i < shuffleTiles.size(); i++){
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

        // update coordinates of tiles so they match their positions in board array

        int j, k;

        // stores identifiers of tiles that have been updated
        ArrayList<Integer> updatedCoords = new ArrayList<>();

        // loop through board
        for(i = 0; i < 5; i++) {
            for (j = 0; j < 16; j++) {
                for (k = 0; k < 30; k++) {
                    if(board[k][j][i] != 0){
                        // ensure that only top left coordinate is updated
                        // since there are 4 positions per tile
                        if(!updatedCoords.contains(board[k][j][i]))
                            updatedCoords.add(board[k][j][i]);
                        // update coordinates of tiles
                        tileIdentifiers.get(board[k][j][i]).setX(k-1);
                        tileIdentifiers.get(board[k][j][i]).setY(j-1);
                        tileIdentifiers.get(board[k][j][i]).setZ(i);
                    }
                }
            }
        }

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

    public TileClass getTileClass(TileType type) {
        switch (type) {
            case Ch1:
            case Ch2:
            case Ch3:
            case Ch4:
            case Ch5:
            case Ch6:
            case Ch7:
            case Ch8:
            case Ch9:
                return TileClass.Character;
            case Bam1:
            case Bam2:
            case Bam3:
            case Bam4:
            case Bam5:
            case Bam6:
            case Bam7:
            case Bam8:
            case Bam9:
                return TileClass.Bamboo;
            case Cir1:
            case Cir2:
            case Cir3:
            case Cir4:
            case Cir5:
            case Cir6:
            case Cir7:
            case Cir8:
            case Cir9:
                return TileClass.Circle;
            case Dra1:
            case Dra2:
            case Dra3:
                return TileClass.Dragon;
            case Wind1:
            case Wind2:
            case Wind3:
            case Wind4:
                return TileClass.Wind;
            case Sea:
                return TileClass.Season;
            case Flo:
                return TileClass.Flower;
            default: // Really not possible but makes the compiler stop complaining.
                return null;
        }
    }
}