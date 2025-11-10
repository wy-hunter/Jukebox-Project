import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class songList {
    public static String[][] songs = new String[100][];
    public final static int SONG_NAME = 0, SONG_ARTIST = 1, SONG_COST = 2, SONG_URI = 3;

    public static void main(String[] args) {
        String filename = "datafile.txt"; 
        int lineNum = 0; // Variable to increment index in while loop
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) { 
            String line = br.readLine(); // Reads next line
            while (line != null) { // If line is not empty
                String[] data = line.split(","); // Split line by ,
                // Testing lines: Shows the parsed line
                for (String s: data) System.out.print(s); 
                System.out.println();
                // Testing lines
                songs[lineNum] = data; // Adds the parsed line to the list of songs
                line = br.readLine(); // Reads next line
                lineNum++; // Increments index
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing Goal 2
        coinPayments testCoin = new coinPayments();
        testCoin.takePayment(61);
        System.out.println(testCoin.returnFunds());
        creditPayments testCredit = new creditPayments();
        testCredit.takePayment(69);
        System.out.println(testCredit.returnFunds());

        balanceBox testBox = new balanceBox();
        testBox.addFunds(50);
        testBox.deductFunds(Integer.parseInt(songs[0][SONG_COST]));
        testBox.addFunds(50);
        testBox.deductFunds(Integer.parseInt(songs[0][SONG_COST]));
    }
}