import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

public class songPlayer {
    /**
     * This class plays songs from the purchaseQueue using JavaFX MediaPlayer.
     * 
     * @author William Yang, Mohammed Uddin.
     */

    private final purchaseQueue queue;
    private MediaPlayer player;
    private String[] current; // keep the whole row so we can show name/artist

    private final int SONG_NAME, SONG_ARTIST, SONG_URI;

    /**
     * Constructor for songPlayer class. This class handles song queues through indexing each song.
     * 
     * @param q
     * @param nameIdx
     * @param artistIdx
     * @param uriIdx
     */
    public songPlayer(purchaseQueue q, int nameIdx, int artistIdx, int uriIdx) {
        this.queue = q;
        this.SONG_NAME = nameIdx;
        this.SONG_ARTIST = artistIdx;
        this.SONG_URI = uriIdx;
    }

    /**
     * This function stops the currently playing song and immediately plays the next song in queue.
     * 
     * @return boolean
     */
    public boolean playNext() {
        stop(); // stop current if any

        // we need the full row, not just the URI — quick grab by peeking print and reusing helper
        // trick: nextSong() only returns a String URI, so we add a tiny helper in purchaseQueue (getRawNext)
        String[] row = queue.getRawNext(); // pulls and removes the head (like nextSong but returns row)
        if (row == null) return false;

        String uriStr = row[SONG_URI];
        String resolved = resolveUri(uriStr);
        try {
            Media media = new Media(resolved);
            player = new MediaPlayer(media);
            current = row;
            player.play();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            current = null;
            return false;
        }
    }

    /**
     * This function pauses the song.
     */
    public void pause() {
        if (player != null) player.pause();
    }

    /**
     * This function resumes the song.
     */
    public void resume() {
        if (player != null) player.play();
    }

    /**
     * This function fully stops the song player.
     */
    public void stop() {
        if (player != null) {
            try { player.stop(); } catch (Exception ignored) {}
            try { player.dispose(); } catch (Exception ignored) {}
            player = null;
        }
        current = null;
    }

    /**
     * This function displays the title and the artist of the song that's currently playing.
     * Returns [none] if no song is playing.
     * 
     * @return String
     */
    public String nowPlaying() {
        if (current == null) return "[none]";
        return current[SONG_NAME] + " — " + current[SONG_ARTIST];
    }

    // --- helpers ---

    /**
     * This function cleans up URI string for JavaFX to read local files.
     * 
     * @param raw
     * @return String
     */
    private String resolveUri(String raw) {
        if (raw == null) return null;

        String s = raw.trim();

        // handle common local forms:
        // "file://./name.mp3" -> turn into absolute file URI
        if (s.startsWith("file://./")) {
            String local = s.substring("file://./".length()); // after the "./"
            return Paths.get(local).toAbsolutePath().toUri().toString();
        }

        // "./name.mp3" -> absolute
        if (s.startsWith("./")) {
            String local = s.substring(2);
            return Paths.get(local).toAbsolutePath().toUri().toString();
        }

        // if already something like "file:/C:/..." or "file:///Users/..." just pass through
        return s;
    }
}
