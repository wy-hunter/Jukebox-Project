import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;

// launches JavaFX and play from the queue with simple buttons
public class songPlayerGUI extends Application {

    private static purchaseQueue q;
    private static songPlayer sp;
    private static balanceBox box;

    // what this does:
    // - Java entry point to start FX
    // input: 
    // loadSongs in songList called and sent datafile.txt
    // returns:
    // - none
    public static void main(String[] args) {
        // load songs
        songList.loadSongs("datafile.txt");

        // build a queue
        q = new purchaseQueue(
            songList.SONG_NAME,
            songList.SONG_ARTIST,
            songList.SONG_COST,
            songList.SONG_URI
        );

        // add some funds + enqueue a couple for demo
        box = new balanceBox();
        box.addFunds(1000);           // $10
        q.enqueue(0, box);            // Cruel Summer
        q.enqueue(1, box);            // The Spectre
        q.enqueue(2, box);            // Ignite

        // hook up the player
        sp = new songPlayer(q, songList.SONG_NAME, songList.SONG_ARTIST, songList.SONG_URI);

        launch(args);
    }

    // what this does:
    // - builds a simple UI (no FXML from scene builder): Now Playing label + 3 buttons
    // goal 4 doesn't require anything fancy
    // bare bones ui
    // input:
    // - the primaryStage provided by FX
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
	    Scene scene = new Scene(root, 1000, 800, Color.WHITE);

        Label now = new Label("Now Playing: " + sp.nowPlaying());
        Label sortBy = new Label("Sort By: ");
        Label credits = new Label("Credits: " + box.getFunds());
        Label addAmount = new Label("Add funds: ");
        TextField creditText = new TextField();

        ChoiceBox sort = new ChoiceBox();
        sort.getItems().addAll("Artist", "Title");

        ChoiceBox paymentMethod = new ChoiceBox();
        paymentMethod.getItems().addAll("Credit", "Coin");

        Button btnNext = new Button("Next");
        btnNext.setOnAction(e -> {
            sp.playNext();
            now.setText("Now Playing: " + sp.nowPlaying());
        });

        Button btnPause = new Button("Pause");
        btnPause.setOnAction(e -> sp.pause());

        Button btnResume = new Button("Resume");
        btnResume.setOnAction(e -> sp.resume());

        Button btnAddCredits = new Button("Add Funds");
        btnAddCredits.setOnAction(e -> {
            if(paymentMethod.getValue() != null) box.addFunds(Integer.parseInt(creditText.getCharacters().toString()));
            credits.setText("Credits: " + box.getFunds());
        });

        Button enqueue = new Button("Enqueue");
        Button enqueueFront = new Button("Enqueue at Front");

        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList (q.printQueue());
        list.setItems(items);

        list.setPrefWidth(100);
        list.setPrefHeight(70);

        HBox hb1 = new HBox(20, sortBy, sort);
        HBox hb2 = new HBox(20, enqueue, enqueueFront);
        HBox hb3 = new HBox(20, addAmount, creditText, btnAddCredits, paymentMethod);
        VBox vb1 = new VBox(20, now, hb1, list, btnNext, btnPause, btnResume, hb2, credits, hb3);
        root.getChildren().add(vb1);

        primaryStage.setTitle("Song Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
