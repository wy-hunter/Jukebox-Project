import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// For UI controls (e.g., textboxes and labels)
// import javafx.scene.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.event.ActionEvent;

// To run, you need only these modules
// --add-modules=javafx.fxml,javafx.media,javafx.controls

public class SimpleText extends Application {

   @Override
   public void start(Stage stage) {
	Group root = new Group();
	Scene scene = new Scene(root, 350, 200, Color.WHITE);

	Label label1 = new Label("Number 1:");
	TextField textField1 = new TextField ();
	Label label2 = new Label("Number 2:");
	TextField textField2 = new TextField ();

	Label label3 = new Label(" ");

	HBox hb1 = new HBox();									// create a horizontal layout box
	hb1.getChildren().addAll(label1, textField1);
	hb1.setSpacing(10);

	HBox hb2 = new HBox();									// and another
	hb2.getChildren().addAll(label2, textField2);
	hb2.setSpacing(10);

	Button b1 = new Button("Add");

	// we are passing a function to the setOnAction button.
	b1.setOnAction(new EventHandler<ActionEvent>() {		// an anonymous function to handle button action
	    @Override public void handle(ActionEvent e) {
        	label1.setText("Number 1 was added.");
        	label2.setText("Number 2 was added.");
		int sum = Integer.parseInt( textField1.getCharacters().toString() )
			+ Integer.parseInt ( textField2.getCharacters().toString() );
		label3.setText( "The sum of the numbers is " + sum );
	    }
	});

	VBox vb1 = new VBox();									// we add the two HBOX to the VBOX
	vb1.getChildren().addAll(hb1,hb2, label3, b1);			// because we can only add one element to canvas
	vb1.setSpacing(10);

	root.getChildren().add(vb1);

	stage.setTitle("A simple demo of user input");
	stage.setScene(scene);
	stage.show();
   }

   public static void main(String[] args) {
   	launch(args);
   }
}
