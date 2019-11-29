package mahjong;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Implements a leader board that holds the ten fastest
 * games played. For each entry in the board, the name and
 * time are stored.
 */
public class LeaderBoard {

    private final int size = 10;

    private String[] winningTimes;

    /**
     * Create a new blank leader board with no data.
     */
    public LeaderBoard() {
        winningTimes = new String[size];
    }


    // loads the leaderboard from a file to the array
    private void loadLeaderBoard(String fileIn) {

        try {

            FileInputStream iStream = new FileInputStream(fileIn);
            Scanner scanner = new Scanner(iStream);

            int i = 0;

            // checks for EOF
            while(scanner.hasNextLine()) {
                // error check: file larger than array
                if(i < winningTimes.length){
                    winningTimes[i] = scanner.nextLine();
                    i++;
                }
            }


        } catch (IOException error1) {
            System.out.println("Error related to: " + fileIn);
        }
    }



    // saves the leaderboard information to a .txt file
    private void saveLeaderBoard(String fileout) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileout));

            for (int i = 0; i < winningTimes.length; i++) {
                if(winningTimes[i] != null && !winningTimes.equals("null"))
                    writer.write(winningTimes[i] + "\n");
            }

            writer.close();
        } catch (IOException error1) {
            System.out.println("Error related to: " + fileout);
        }
    }

    /**
     * Adds a time to the leader board if it is high enough to add. Otherwise,
     * it will be ignored.
     * @param time The time in seconds.
     * @param name The name of the user.
     * @param fileIn The file to save the resulting board into.
     */
    public void updateLeaderBoard(int time, String name, String fileIn) {
        loadLeaderBoard(fileIn);

        // some comparison code
        for(int i = 0; i < winningTimes.length; i++) {

            // check that current position is filled
            if(winningTimes[i] != null && !winningTimes[i].equals("null")) {
                // get one entry from the array and split it
                String[] entry = new String[2];
                entry = winningTimes[i].split("\t");

                // if new time is less than stored time
                if(time < Integer.parseInt(entry[1])){
                    insertEntry((name + "\t" + time), i);
                }

            } else {
                winningTimes[i] = name + "\t" + time;
                break;
            }

        }

        saveLeaderBoard(fileIn);

    }


    // inserts an entry into the leaderboard and adjusts the array accordingly
    private void insertEntry(String entry, int index) {

        for(int i = winningTimes.length-1; i > index; i--){
            winningTimes[i] = winningTimes[i-1];
        }

        winningTimes[index] = entry;
    }


    /**
     * Gets the number of entries on the leader board. The board only
     * holds 10 at most but it could be less if the game has not been
     * beaten that many times.
     * @return The number of entries on the board.
     */
    public int size() {

        int size = 0;

        for(int i = 0; i < winningTimes.length; i++) {
            if(winningTimes[i] != null){
                size++;
            }
        }

        return size;
    }

    /**
     * Get the entry information from a position in the leader board.
     * @param pos The position (0 to 9) to look at.
     * @return A string holding entry information.
     */
    public String entryAtPosition(int pos) {
        return winningTimes[pos];
    }

}
