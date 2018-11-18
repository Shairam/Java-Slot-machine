package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
	private String filename;
	private Media media; // instance variables used in the sound class
	private MediaPlayer mediaPlayer;

	public Sound(String filename) { // constructor to set the initialization to each sound object
		super();
		this.filename = filename;
		this.media = new Media(new File(filename).toURI().toString());
		this.mediaPlayer = new MediaPlayer(media);

	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer; // get method to return the media player
	}

}
