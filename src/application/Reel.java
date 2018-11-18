package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.Timer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Reel {
	public Symbol[] sym_list = new Symbol[6]; // an arrray of symbol data type as an instance variable to store the
												// images and the points

	public Reel() {

		this.sym_list = spin(); // initializing the array

	}

	public Symbol[] getSym_list() {
		return sym_list; // method to return the array
	}

	public Symbol[] spin() {
		sym_list[0] = new Symbol(new Image("file:redseven.png"), 7, 1);
		sym_list[1] = new Symbol(new Image("file:bell.png"), 6, 2);
		sym_list[2] = new Symbol(new Image("file:watermelon.png"), 5, 3); // method to put all the images and respective
																			// points
		sym_list[3] = new Symbol(new Image("file:plum.png"), 4, 4); // this method also shuffles the array
		sym_list[4] = new Symbol(new Image("file:lemon.png"), 3, 5);
		sym_list[5] = new Symbol(new Image("file:cherry.png"), 2, 6);

		Collections.shuffle(Arrays.asList((sym_list)));
		return sym_list;

	}

}
