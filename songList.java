import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

//refactored to better clean up the console output still does the same thing
// what this does:
// - holds a list of songs read from a file

public class songList {
    public static String[][] songs = new String[100][];
    public final static int SONG_NAME = 0, SONG_ARTIST = 1, SONG_COST = 2, SONG_URI = 3;

    // what this does:
    // - reads the file and puts each line into songs[]
    // input:
    // - filename: like "datafile.txt"
    // returns:
    // - how many songs we actually loaded
    public static int loadSongs(String filename) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line != null && count < songs.length) {
                String[] data = line.split(",");
                songs[count] = data;
                count++;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Sorts the list of songs either by author or title (specified by index).
     * 
     * @param index
     */
    public static void sortList(int index) {
        // 0 = sort by title, 1 = sort by artist
        Arrays.sort(songs, new Comparator<String[]>() {
            @Override
            public int compare(final String[] first, final String[] second) {
                if (first != null && second != null) {
                    final String firsts = first[index];
                    final String seconds = second[index];
                    return firsts.compareTo(seconds);
                } else {
                    return 0;
                }
            }
        });
    }

    // (not needed for the JavaFX app)
    // what this does:
    // - main for testing loading songs
    public static void main(String[] args) {
        int n = loadSongs("datafile.txt");
        System.out.println("Loaded " + n + " songs.");
        sortList(1);
    }
}
