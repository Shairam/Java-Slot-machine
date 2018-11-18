package application;

import application.ISymbol;
import javafx.scene.image.Image;

public class Symbol implements ISymbol {
	// instance variables used in this class

	private Image image;
	private int value;
	private int im_id;

	public Symbol(Image image, int value, int im_id) {
		super();
		this.image = image; // constructors to initialize the values
		this.value = value;
		this.im_id = im_id;
	}

	// overriding the methods implemented from interface ISymbol

	@Override
	public void setImage(Image image) {
		this.image = image;

	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setValue(int v) {
		value = v;

	}

	@Override
	public int getValue() {
		return value;
	}

	public int getIm_id() {
		return im_id;
	}

	public void setIm_id(int im_id) {
		this.im_id = im_id;
	}

}
