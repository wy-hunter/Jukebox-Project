import java.util.ArrayDeque;
import java.util.Deque;

// handles buying songs (using a shared balanceBox) and keeping them in play order
public class purchaseQueue {
    /**
     * This class handles buying songs (using a shared balanceBox) and keeping them in play order.
     * This class also handles queueing the songs, including the enqueue and enqueue first methods.
     * 
     * @author William Yang, Mohammed Uddin
     */
    // use same positions from songList so we can read fields cleanly
    private final int SONG_NAME, SONG_ARTIST, SONG_COST, SONG_URI;
    private final Deque<String[]> queue = new ArrayDeque<>();

    /**
     * Constructor for purchaseQueue. Remembers the indices for name/artist/cost/uri so we can print later.
     * 
     * @param nameIdx
     * @param artistIdx
     * @param costIdx
     * @param uriIdx
     */
    public purchaseQueue(int nameIdx, int artistIdx, int costIdx, int uriIdx) {
        this.SONG_NAME = nameIdx;
        this.SONG_ARTIST = artistIdx;
        this.SONG_COST = costIdx;
        this.SONG_URI = uriIdx;
    }

    /**
     * This class enqueues the song by spending funds to purchase the song.
     * If there is not enough money, the method will return false. Otherwise, returns true.
     * 
     * @param songIndex
     * @param box
     * @return boolean 
     */
    public boolean enqueue(int songIndex, balanceBox box) {
        String[] song = get(songIndex);
        if (song == null) return false;

        int cost = parseIntSafe(song[SONG_COST], 0);
        if (box.deductFunds(cost)) {
            queue.addLast(song);
            // System.out.println("Song purchased.");
            return true;
        }
        // System.out.println("Not enough funds.");
        return false;
    }

    /**
     * This class also enqueues the song but places the song at the FRONT (like a priority play). 
     * There is an extra fee charged for enqueueing at the front. 
     * 
     * @param songIndex
     * @param box
     * @param extraFee
     * @return
     */
    public boolean enqueueFront(int songIndex, balanceBox box, int extraFee) {
        String[] song = get(songIndex);
        if (song == null) return false;

        int cost = parseIntSafe(song[SONG_COST], 0) + Math.max(extraFee, 0);
        if (box.deductFunds(cost)) {
            queue.addFirst(song);
            return true;
        }
        return false;
    }

    /**
     * Prints the queue in a readable list (1., 2., …)
     * 
     * @return String
     */
    public String printQueue() {
        if (queue.isEmpty()) return "[Queue is empty]";
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String[] s : queue) {
            sb.append(i++).append(". ")
              .append(s[SONG_NAME]).append(" — ").append(s[SONG_ARTIST])
              .append(" ($").append(centsToDollars(parseIntSafe(s[SONG_COST], 0))).append(")")
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Pops the next song from the queue and gives back its URI.
     * 
     * @return String
     */
    public String nextSong() {
        String[] next = queue.pollFirst();
        return (next == null) ? null : next[SONG_URI];
    }

    // --- helpers (internal) ---

    /**
     * Safely pulls songs[songIndex]; returns null if out of bounds.
     * 
     * @param songIndex
     * @return String[]
     */
    private static String[] get(int songIndex) {
        if (songIndex < 0 || songIndex >= songList.songs.length) return null;
        return songList.songs[songIndex];
    }

    /**
     * Parses an int from a String; if bad input returns def.
     * 
     * @param s
     * @param def
     * @return int
     */
    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    /**
     * Converts cents to a dollars string like 199 -> "1.99"
     * 
     * @param cents
     * @return String
     */
    private static String centsToDollars(int cents) {
        int d = Math.abs(cents);
        return (cents < 0 ? "-" : "") + (d / 100) + "." + String.format("%02d", d % 100);
    }

    /**
     * Polls first from the queue.
     * 
     * @return String[]
     */
    public String[] getRawNext() {
        return queue.pollFirst();
    }
}
