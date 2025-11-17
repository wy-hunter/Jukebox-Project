import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

// plays songs from purchaseQueue using JavaFX MediaPlayer
public class songPlayer {

    private final purchaseQueue queue;
    private MediaPlayer player;
    private String[] current; // keep the whole row so we can show name/artist

    private final int SONG_NAME, SONG_ARTIST, SONG_URI;

    // what this does:
    // - remembers the queue + indexes so we can pop + show labels
    // input:
    // - q: the same queue you filled in goal 3
    // - nameIdx, artistIdx, uriIdx: positions in songs[][]
    // returns:
    // - none
    public songPlayer(purchaseQueue q, int nameIdx, int artistIdx, int uriIdx) {
        this.queue = q;
        this.SONG_NAME = nameIdx;
        this.SONG_ARTIST = artistIdx;
        this.SONG_URI = uriIdx;
    }

    // what this does:
    // - stops anything currently playing
    // - takes the next song out of the queue and starts it
    // input:
    // - none
    // returns:
    // - true if it started a song, false if queue empty or bad uri
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

    // what this does:
    // - pauses current song if one is playing
    // input:
    // - none
    // returns:
    // - none
    public void pause() {
        if (player != null) player.pause();
    }

    // what this does:
    // - resumes current song if paused
    // input:
    // - none
    // returns:
    // - none
    public void resume() {
        if (player != null) player.play();
    }

    // what this does:
    // - fully stops and releases the player
    // input:
    // - none
    // returns:
    // - none
    public void stop() {
        if (player != null) {
            try { player.stop(); } catch (Exception ignored) {}
            try { player.dispose(); } catch (Exception ignored) {}
            player = null;
        }
        current = null;
    }

    // what this does:
    // - tells you what’s currently playing (Title — Artist) or "[none]"
    // input:
    // - none
    // returns:
    // - string label for UI/console
    public String nowPlaying() {
        if (current == null) return "[none]";
        return current[SONG_NAME] + " — " + current[SONG_ARTIST];
    }

    // --- helpers ---

    // what this does:
    // - cleans up your uri string so JavaFX Media can read local files
    // input:
    // - raw: whatever you stored in csv (e.g., "file://./thespectre.mp3")
    // returns:
    // - a valid URI string for Media(…)
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
