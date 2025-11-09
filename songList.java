import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class songList {
    public static String[][] songs = new String[100][];

    public static void main(String[] args) {
        String filename = "datafile.txt";
        int lineNum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(",");
                for (String s: data) System.out.print(s);
                System.out.println();
                songs[lineNum] = data;
                line = br.readLine();
                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}