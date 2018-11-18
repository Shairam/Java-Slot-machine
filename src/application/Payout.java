package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Payout extends Application {
	
	private static Scene scene;
	private ImageView ivPayout;

	public Payout() {
		Stage st = new Stage();
		start(st);
	}
	
	@Override
	public void start(Stage stage){
		
		stage.setTitle("Payout table");
		
		
		StackPane pane = new StackPane();
		scene = new Scene(pane, 1000, 1000);
		
		ivPayout = new ImageView(new Image("file:payout.png"));
		
		pane.getChildren().addAll(ivPayout);
	
		
		stage.setScene(scene);
		stage.show();
		
	}

}
