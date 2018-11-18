package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Machinegui extends Application {
	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;
	private Button btnSpin;
	private Button btnReset;
	private Button add_coin;
	private Button bet_one;
	private Button bet_max;
	private Button btn_stats;
	private Button btn_payout;

	// declaring all the instance variables and javafx components that are used in
	// the game
	private Label lbl_credit;
	private Label lbl_creditValue;
	private Label lbl_bet;
	private Label lbl_bVal;
	private TextField notify;
	private MyThread t1;
	private MyThread t2;
	private MyThread t3;
	private Sound coinSound;
	private Sound startMusic = new Sound("startm.mp3");
	private Sound spinMusic = new Sound("spinning.mp3");
	private Sound betVoice = new Sound("bet.m4a");

	private static int gamesWon;
	private static int gamesLost;
	private static int totCreditsWon;
	private static int totCreditsLost;
	private int bet;

	private static int totGames;
	private boolean isclicked1, isclicked2, isclicked3;

	public static int getGamesWon() {
		return gamesWon;
	}

	public static int getGamesLost() { // getter methods for certain instance variables to return their value
		return gamesLost;
	}

	public static int getTotCreditsWon() {
		return totCreditsWon;
	}

	public static int getTotCreditsLost() {
		return totCreditsLost;
	}

	public static void setTotCreditsLost(int totCreditsLost) {
		Machinegui.totCreditsLost = totCreditsLost;
	}

	public static int getTotGames() {
		return totGames;
	}

	@Override
	public void start(Stage primaryStage) {

		try {

			Stage root = new Stage();
			StackPane pane = new StackPane(); // creating stage and pane
			pane.getStyleClass().add("pane");

			StackPane pane2 = new StackPane();
			pane2.getStyleClass().add("pane1");
			Scene scene = new Scene(pane, 600, 500);
			Scene scene2 = new Scene(pane2, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // adding style
																										// sheet to both
																										// scenes

			int depth = 70; // Setting the uniform variable for the glow width and height

			final Glow glow = new Glow();
			glow.setLevel(0.0);

			final Timeline timeline = new Timeline();
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.setAutoReverse(true);
			final KeyValue kv = new KeyValue(glow.levelProperty(), 1.5); // code to make the "play game" button glow and
																			// blink
			final KeyFrame kf = new KeyFrame(Duration.millis(700), kv);
			timeline.getKeyFrames().add(kf);
			timeline.play();

			// adding audio to start page

			startMusic.getMediaPlayer().play();

			Button btn1 = new Button("Play game");
			btn1.setTranslateY(120);
			btn1.getStyleClass().add("btn1"); // setting properties and position to play game button in the first scene
			pane2.getChildren().addAll(btn1);
			btn1.setEffect(glow);

			root.setTitle("Slot Machine"); // setting title of the window

			btnSpin = new Button("Spin");
			btnSpin.setTranslateX(-100); // setting properties and position to spin button
			btnSpin.setTranslateY(120);
			btnSpin.getStyleClass().add("buttons"); // getting the style class from style sheet for spin button

			btnReset = new Button("Reset");
			btnReset.setTranslateX(100); // setting properties and position to reset button
			btnReset.setTranslateY(120);
			btnReset.getStyleClass().add("buttons"); // getting the style class from style sheet for reset button

			add_coin = new Button("Add coin");
			add_coin.setTranslateX(-250); // setting properties and position to Add coin button
			add_coin.setTranslateY(-150);

			bet_one = new Button("Bet One");
			bet_one.setTranslateX(-250); // setting properties and position to Bet one button
			bet_one.setTranslateY(-100);

			bet_max = new Button("Bet Max");
			bet_max.setTranslateX(-250); // setting properties and position to Bet max button
			bet_max.setTranslateY(-50);

			btn_stats = new Button("Statistics");
			btn_stats.setTranslateX(250); // setting properties and position to statistics button
			btn_stats.setTranslateY(-150);
			
			btn_payout = new Button("Payout Table");
			btn_payout.setTranslateX(250); // setting properties and position to statistics button
			btn_payout.setTranslateY(-100);

			lbl_credit = new Label("Credit:");
			lbl_credit.setTranslateX(-100);
			lbl_credit.setTranslateY(80);
			lbl_credit.getStyleClass().add("lbl");

			lbl_creditValue = new Label("02");
			lbl_creditValue.setTranslateX(-60);
			lbl_creditValue.setTranslateY(80); // declaring, setting properties and positioning the labels used.
			lbl_creditValue.getStyleClass().add("lbl");

			lbl_bet = new Label("Bet:");
			lbl_bet.setTranslateX(80);
			lbl_bet.setTranslateY(80);
			lbl_bet.getStyleClass().add("lbl");

			lbl_bVal = new Label("00");
			lbl_bVal.setTranslateX(110);
			lbl_bVal.setTranslateY(80);
			lbl_bVal.getStyleClass().add("lbl");

			notify = new TextField("Welcome to slot machine Game");
			notify.setMaxWidth(250);
			notify.setEditable(false); // text field to notify the player
			notify.getStyleClass().add("text-field");
			notify.setTranslateY(-150);

			iv1 = new ImageView(new Image("file:plum.png"));
			iv1.setFitWidth(45);
			iv1.setFitHeight(50);
			iv1.setTranslateX(-100);
			iv1.setPreserveRatio(true);

			iv2 = new ImageView(new Image("file:cherry.png")); // setting properties and passing the initial images for
																// the image views used inside each reel
			iv2.setFitWidth(45);
			iv2.setFitHeight(50);
			iv2.setTranslateX(0);

			iv3 = new ImageView(new Image("file:bell.png"));
			iv3.setFitWidth(45);
			iv3.setFitHeight(50);
			iv3.setTranslateX(100);

			// adding all the components to the pane
			pane.getChildren().addAll(iv1, iv2, iv3, btnSpin, lbl_credit, lbl_creditValue, lbl_bet, lbl_bVal, btnReset,
					add_coin, bet_one, bet_max, notify, btn_stats, btn_payout);

			root.setScene(scene2);
									// setting properties of the stage
			root.show();

			// event and event handler to get performed when the spin button is clicked

			EventHandler<MouseEvent> spinEvent = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {

					if (Integer.parseInt(lbl_bVal.getText()) != 0) {

						t1 = new MyThread(new Reel(), iv1);
						t2 = new MyThread(new Reel(), iv2); // initialising threads
						t3 = new MyThread(new Reel(), iv3);
						Thread myTh1 = new Thread(t1);
						Thread myTh2 = new Thread(t2); // passing the thread object created to the thread class
						Thread myTh3 = new Thread(t3);

						myTh1.start();
						myTh2.start(); // starting the threads
						myTh3.start();

						notify.setText("spinning");
						isclicked1 = isclicked2 = isclicked3 = false;
						totGames++;

						spinMusic.getMediaPlayer().play();
						bet = Integer.parseInt(lbl_bVal.getText());
						lbl_bVal.setText("00");
						btnSpin.setDisable(true);
						bet_max.setDisable(false);
					} else {

						notify.setText("Sorry no bets");
						betVoice.getMediaPlayer().play();

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Slot Machine");
							alert.setHeaderText("Error");
							alert.setContentText("Unexpected error occured.Slot machine isn't responding");

							alert.show();
						}

						betVoice.getMediaPlayer().stop();

					}

				}

			};

			EventHandler<MouseEvent> sceneChange = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					root.setScene(scene); // event and event handler to get performed when the play game button is
											// clicked in the first window
					startMusic.getMediaPlayer().stop();

				}
			};

			// event and event handler to get performed when the first image view is clicked

			EventHandler<MouseEvent> imgViewEvent1 = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if (isclicked1 == false) {
						t1.setStop(true); // stopping the reel using a boolean variable
						isclicked1 = true;

						if (!isAllSpinning()) {
							win();
							spinMusic.getMediaPlayer().stop(); // checks if all reels stopped spinning and does the
																// winning procedures
							btnSpin.setDisable(false);
							bet = 0;
						}

					}
				}
			};

			// event and event handler to get performed when the statistics button is
			// clicked
			EventHandler<MouseEvent> statisticsevnt = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if ((totGames == 0)) { // checks if you haven't played a single game and displays an alert message

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Slot Machine");
						alert.setHeaderText("Play a game");
						alert.setContentText("You haven't played any game to show statistics.Play a game.");
						alert.setOnCloseRequest(event -> {
							alert.close();
						});
						alert.show();

					} else if (isAllSpinning()) {
						// checks if the reels are spinning and if so it doesn't allow to go the
						// statistics page

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Slot Machine");
						alert.setHeaderText("Spinning");
						alert.setContentText("The spin is being made. Finish the game to see the statistics");
						alert.setOnCloseRequest(event -> {
							alert.close();
						});
						alert.show();

					} else {
						new Stats();
											// shows the statistics window
					}
				}
			};

			EventHandler<MouseEvent> imgViewEvent2 = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if (isclicked2 == false) {
						t2.setStop(true);
						isclicked2 = true;
						if (!isAllSpinning()) {
							win();
							spinMusic.getMediaPlayer().stop();
							btnSpin.setDisable(false);
							bet = 0;
						}
					}
				}
			};
			
			
			EventHandler<MouseEvent> payoutpage = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					new Payout();
				}
			};

			EventHandler<MouseEvent> imgViewEvent3 = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if (isclicked3 == false) {
						t3.setStop(true);
						isclicked3 = true;
						if (!isAllSpinning()) {
							win();
							spinMusic.getMediaPlayer().stop();
							btnSpin.setDisable(false);
							bet = 0;
						}
					}
				}
			};

			// event and event handler to be performed when the add coin button is pressed
			EventHandler<MouseEvent> addcoinHandler = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					int j = Integer.parseInt(lbl_creditValue.getText());
					j++;
					lbl_creditValue.setText(String.valueOf(j)); // increase the available coins by 1 for each click

					coinSound = new Sound("coin.mp3");
					coinSound.getMediaPlayer().play();
				}
			};

			// event and event handler to be performed when the bet max button is pressed
			EventHandler<MouseEvent> bettingMax = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					int j = Integer.parseInt(lbl_bVal.getText());
					int k = Integer.parseInt(lbl_creditValue.getText());

					if (k >= 3) { // checks if the available credit is greater than 3

						j += 3;
						k -= 3;
						lbl_bVal.setText(String.valueOf(j));
						lbl_creditValue.setText(String.valueOf(k)); // adds 3 credits to bet and notifies the playe
						notify.setText("a bet has been made");
						bet_max.setDisable(true); // setting disability of the button because bet max can be pressed
													// only once before each spin
					} else {
						notify.setText("Not enough credits. please add coins");
					}

					bet = Integer.parseInt(lbl_bVal.getText());
				}

			};

			// event and event handler to be performed when the bet one button is pressed
			EventHandler<MouseEvent> bettingOne = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					int j = Integer.parseInt(lbl_bVal.getText());
					int k = Integer.parseInt(lbl_creditValue.getText());
					if (k > 0) { // checks if there are credits available to make a bet

						j++;
						k--;
						lbl_bVal.setText(String.valueOf(j));
						lbl_creditValue.setText(String.valueOf(k));
						notify.setText("a bet has been made");

					} else {
						notify.setText("Not enough credits. please add coins");
					}

				}
			};

			// event and event handler to be performed when the resetting button is pressed
			EventHandler<MouseEvent> resetting = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					int j = Integer.parseInt(lbl_bVal.getText());
					int k = Integer.parseInt(lbl_creditValue.getText());
					if (j > 0) {

						// gets the bet value back to the players available credits
						k += j;
						j = 0;
						lbl_bVal.setText(String.valueOf(j));
						lbl_creditValue.setText(String.valueOf(k));
						notify.setText("");
					}

					bet = Integer.parseInt(lbl_bVal.getText());
					bet_max.setDisable(false);
				}
			};

			// adding all the eventhandlers to the respective components which needs to
			// perform events.

			btnSpin.addEventHandler(MouseEvent.MOUSE_CLICKED, spinEvent);
			btnReset.addEventHandler(MouseEvent.MOUSE_CLICKED, resetting);
			iv1.addEventHandler(MouseEvent.MOUSE_CLICKED, imgViewEvent1);
			iv2.addEventHandler(MouseEvent.MOUSE_CLICKED, imgViewEvent2);
			iv3.addEventHandler(MouseEvent.MOUSE_CLICKED, imgViewEvent3);

			add_coin.addEventHandler(MouseEvent.MOUSE_CLICKED, addcoinHandler);
			bet_one.addEventHandler(MouseEvent.MOUSE_CLICKED, bettingOne);
			bet_max.addEventHandler(MouseEvent.MOUSE_CLICKED, bettingMax);
			btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, sceneChange);
			btn_stats.addEventHandler(MouseEvent.MOUSE_CLICKED, statisticsevnt);
			btn_payout.addEventHandler(MouseEvent.MOUSE_CLICKED, payoutpage);

		} catch (Exception e) {
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Slot Machine"); // exception handling
			alert1.setHeaderText("ERROR");
			alert1.setContentText("Slot machine isn't responding");
		}
	}

	public boolean isAllSpinning() {
		if ((t1.stop == true) && (t2.stop == true) && (t3.stop == true)) { // checks if allthe reels are spinning by
																			// accessing each of its stop variable

			return false;
		}
		return true;
	}

	// method to check if player wins
	public void win() {
		int temp_cre;
		temp_cre = Integer.parseInt(lbl_creditValue.getText()); // stores the currently available credit value
		int tot;
		int wonCredits;
		if (!isAllSpinning()) { // checks if all stops spinning

			// checks if first and the second reel has the symbol by comparing the id of
			// each symbol
			if (t1.getReel().sym_list[t1.getIndex()].getIm_id() == t2.getReel().sym_list[t2.getIndex()].getIm_id()) {
				wonCredits = bet * checkIm(t1.getReel(), iv1.getImage()); // gets the score
				notify.setText("You scored  " + wonCredits);
				tot = temp_cre + wonCredits; // assigns the credits won and available credit to tot variable to put back
												// to credits area
				lbl_creditValue.setText(String.valueOf(tot));
				gamesWon++;
				totCreditsWon += wonCredits; // adds the credits won to a global variable which is used later
			}

			// checks if first and the third reel has the symbol by comparing the id of each
			// symbol
			else if (t1.getReel().sym_list[t1.getIndex()].getIm_id() == t3.getReel().sym_list[t3.getIndex()]
					.getIm_id()) {

				wonCredits = bet * checkIm(t1.getReel(), iv1.getImage());
				notify.setText("You scored  " + wonCredits);
				tot = temp_cre + wonCredits;
				lbl_creditValue.setText(String.valueOf(tot));
				gamesWon++;
				totCreditsWon += wonCredits;
			}

			// checks if second and the third reel has the symbol by comparing the id of
			// each symbol
			else if (t2.getReel().sym_list[t2.getIndex()].getIm_id() == t3.getReel().sym_list[t3.getIndex()]
					.getIm_id()) {

				wonCredits = bet * checkIm(t2.getReel(), iv2.getImage());
				notify.setText("You scored  " + wonCredits);
				tot = temp_cre + wonCredits;
				lbl_creditValue.setText(String.valueOf(tot));
				gamesWon++;
				totCreditsWon += wonCredits;
			}

			// if none of the reel matches then notifies the player
			else {
				notify.setText("You Lost. Better luck next time");
				totCreditsLost += bet;
				gamesLost++;
			}

		}
	}

	public int checkIm(Reel r, Image im) { // method to get the value of the symbol
		Symbol[] arr = r.getSym_list();
		for (int i = 0; i < 6; i++) { // for loop to iterate through the reel and compares the symbols
			if (arr[i].getImage().equals(im)) {
				return arr[i].getValue();

			}

		}
		return 0;
	}

	public static void writeFile() throws IOException { // method to write the statistics data into file
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		File folder = new File("C:\\Users\\ACER\\eclipse-workspace\\coursework-2");
		String name = df.format(new Date());
		File f = new File(folder, String.format("%s.TXT", name));
		try (FileWriter ps = new FileWriter(f)) {
			ps.write("Total winnings: " + gamesWon + "\n Total loses: " + gamesLost + "\n  Average Credits netted: "
					+ Stats.calcAverage());

		} catch (IOException e) {
			System.out.println("File not found");

		}
	}

	class MyThread implements Runnable {

		private Reel reel;
		private ImageView iv; // instance varibles for my own class which extends Runable interface
		private int index;
		private boolean stop;

		public MyThread(Reel rl, ImageView imgV) {
			this.reel = rl;
			this.iv = imgV; // constructor used to initialize the local variables
			this.index = -1;
			this.stop = false;

		}

		@Override
		public void run() {

			try {

				while (!this.stop) { // a while loop to constantly change the image on the image view (reel) till the
										// object's stop variable is changed to true

					Platform.runLater(() -> {
						
						
						
						index++;
						if (index == 6) {
							index = 0;
						}
						this.iv.setImage(this.reel.sym_list[index].getImage());
						
					});

					Thread.sleep(50); // time delay for each image
				}
			} catch (Exception e) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Slot Machine");
				alert.setHeaderText("Error");
				alert.setContentText("Unexpected error occured.Slot machine isn't responding"); // exception handling

				alert.show();
			}

		}

		// getters and setters for certain instance variables
		public Reel getReel() {
			return reel;
		}

		public ImageView getIv() {
			return iv;
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public int getIndex() {
			return index;
		}

	}
}
