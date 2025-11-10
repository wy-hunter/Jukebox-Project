import java.util.ArrayDeque;
import java.util.Deque;

// handles buying songs (using a shared balanceBox) and keeping them in play order
public class purchaseQueue {

    // use same positions from songList so we can read fields cleanly
    private final int SONG_NAME, SONG_ARTIST, SONG_COST, SONG_URI;
    private final Deque<String[]> queue = new ArrayDeque<>();

    // what this does:
    // - remembers the indices for name/artist/cost/uri so we can print later
    // input:
    // - nameIdx, artistIdx, costIdx, uriIdx: positions in songs[][] for each field
    // returns:
    // - none
    public purchaseQueue(int nameIdx, int artistIdx, int costIdx, int uriIdx) {
        this.SONG_NAME = nameIdx;
        this.SONG_ARTIST = artistIdx;
        this.SONG_COST = costIdx;
        this.SONG_URI = uriIdx;
    }

    // what this does:
    // - tries to buy the song at songs[songIndex] using money from box
    // - if enough money, puts it at the end of the queue
    // input:
    // - songIndex: which song in songs[][]
    // - box: the shared balance box we’re drawing from
    // returns:
    // - true if purchase worked and we queued it, false if not enough money or bad index
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

    // what this does:
    // - same as enqueue but places the song at the FRONT (like a priority play)
    // - charges an extra fee (extraFee >= 0)
    // input:
    // - songIndex: which song
    // - box: the shared balance
    // - extraFee: additional cents to charge on top of song cost
    // returns:
    // - true if we had enough and added to front, false otherwise
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

    // what this does:
    // - prints the queue in a readable list (1., 2., …)
    // input:
    // - none
    // returns:
    // - a String you can println (or show in GUI later)
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

    // what this does:
    // - pops the next song from the queue and gives back its URI
    // input:
    // - none
    // returns:
    // - the URI string for the next song to play, or null if queue is empty
    public String nextSong() {
        String[] next = queue.pollFirst();
        return (next == null) ? null : next[SONG_URI];
    }

    // --- helpers (internal) ---

    // what this does:
    // - safely pulls songs[songIndex]; returns null if out of bounds
    // input:
    // - songIndex
    // returns:
    // - String[] for that song or null
    private static String[] get(int songIndex) {
        if (songIndex < 0 || songIndex >= songList.songs.length) return null;
        return songList.songs[songIndex];
    }

    // what this does:
    // - parses an int from a String; if bad input returns def
    // input:
    // - s: the string
    // - def: fallback value
    // returns:
    // - parsed int or def
    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    // what this does:
    // - converts cents to a dollars string like 199 -> "1.99"
    // input:
    // - cents
    // returns:
    // - formatted dollars string
    private static String centsToDollars(int cents) {
        int d = Math.abs(cents);
        return (cents < 0 ? "-" : "") + (d / 100) + "." + String.format("%02d", d % 100);
    }
}
