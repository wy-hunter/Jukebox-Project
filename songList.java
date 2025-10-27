import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class songList {
    public static String[][] songs;

    public static void main(String[] args) {
        String filename = "datafile.txt";
        int lineNum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while (br.readLine() != null) {
                String[] data = br.readLine().split(",");
                for (int i = 0; i <= data.length; ++i) {
                    songs[lineNum][i] = data[i];
                }
                lineNum += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}