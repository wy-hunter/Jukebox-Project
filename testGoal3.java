
// testGoal3 is a tiny demo of buying + queueing + playing next
public class testGoal3 {

    // what this does:
    // - loads the songs
    // - adds money
    // - tries to buy a couple songs (end + front)
    // - prints queue, pops next, prints again
    // input:
    // - none
    // returns:
    // - none (prints everything)
    public static void main(String[] args) {
        // load songs into songList.songs
        songList.main(null);

        // setup queue using the indexes from songList
        purchaseQueue q = new purchaseQueue(
            songList.SONG_NAME,
            songList.SONG_ARTIST,
            songList.SONG_COST,
            songList.SONG_URI
        );

        balanceBox box = new balanceBox();

        // add $2.50
        box.addFunds(250);

        // try two purchases
        System.out.println("Buy 0: " + q.enqueue(0, box)); // true if enough money
        System.out.println("Buy 1: " + q.enqueue(1, box)); // depends on remaining

        // see queue
        System.out.println("\nQueue:");
        System.out.println(q.printQueue());

        // play next
        String uri1 = q.nextSong();
        System.out.println("Now playing: " + (uri1 == null ? "(none)" : uri1));

        // after pop
        System.out.println("\nQueue after pop:");
        System.out.println(q.printQueue());

        // add $5 and do a priority add to front with a small fee
        box.addFunds(500);
        System.out.println("Priority buy 2 (+50 fee): " + q.enqueueFront(2, box, 50));

        System.out.println("\nFinal queue:");
        System.out.println(q.printQueue());
    }
}
