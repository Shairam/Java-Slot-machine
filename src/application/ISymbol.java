package application;

import javafx.scene.image.Image;

public interface ISymbol {
	
// below are the abstract methods in this interface that is
//	to be implemented by the classes that implements this interface
	
	
	public  void setImage(Image image);
	public Image getImage();
	public void setValue(int v);
	public int getValue();
}
