package application;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Stats extends Application {
	private static Scene scene;
	public Label lbltxt;
	public Label lblval; // instance variables used in this class
	private Button save;
	private static boolean isClicked;
	Sound er = new Sound("error.mp3"); // error sound when an alert box is displayed (this is windows error sound)
	
	
	public Stats() {
		Stage st = new Stage(); // constructor used
		start(st);
	}

	@Override
	public void start(Stage stage) {
		scene = new Scene(new Group(), Color.THISTLE); // creating scene

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // adding style sheet to
		
		// the scene
		stage.setTitle("Statistics");
		stage.setWidth(600); // setting properties to the stage
		stage.setHeight(500);
		
		save = new Button("Save statistics");
		save.setTranslateX(200); // initializing and positioning the save button
		save.setTranslateY(400);

		lbltxt = new Label("Average credits netted:");
		lbltxt.setTranslateX(350); // label initialized and positioned which is used to display a text
		lbltxt.setTranslateY(400);
		lbltxt.getStyleClass().add("textstat");

		lblval = new Label();
		lblval.setText(calcAverage());
		lblval.setTranslateX(500); // label initialized and positioned which is used to display average crdits
									// earned
		lblval.setTranslateY(400);
		lblval.getStyleClass().add("textstat");

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Games Won", Machinegui.getGamesWon()),
				new PieChart.Data("Games Lost", Machinegui.getGamesLost())); // creating pie chart and passing the data
																				// to be displayed
		// pie chart is used to graphically show the statistics as it can be understood
		// by everyone and go
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Game Statistics");

		((Group) scene.getRoot()).getChildren().addAll(chart, save, lbltxt, lblval);

		stage.setScene(scene);
		stage.setResizable(false); // properties set for the stage
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		
		stage.setOnCloseRequest(event -> {
			isClicked = false; // to check if it is already clicked
			
		});

		EventHandler<MouseEvent> saveStats = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (isClicked == false) {
					try { // event handler to the save button that says to write to the file
						Machinegui.writeFile(); // writeFile() method found in Machinegui class
						isClicked = true;
					} catch (IOException e1) {

						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Slot Machine"); // exception handling
						alert2.setHeaderText("File not found");
						alert2.setContentText("The required file is missing");

						alert2.show();
					}
				} else {

					er.getMediaPlayer().play();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Slot Machine"); // message displayed if statistics saved already
					alert.setHeaderText("Already Saved");
					alert.setContentText("Statistics alrady saved");

					alert.show();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Slot Machine"); // exception handling
						alert1.setHeaderText("ERROR");
						alert1.setContentText("Slot machine isn't responding");

						alert1.show();
					}
					er.getMediaPlayer().stop();
				}
			}
		};

		save.addEventHandler(MouseEvent.MOUSE_CLICKED, saveStats);
	}

	public static String calcAverage() {
		double average = (Machinegui.getTotCreditsWon() - Machinegui.getTotCreditsLost()) / Machinegui.getTotGames(); // method
																													// to
																													// calculate
																													// and
																													// return
																													// the
																													// average
																													// credits
																													// earned
		
		return String.valueOf(average);

	}
	
	

}
