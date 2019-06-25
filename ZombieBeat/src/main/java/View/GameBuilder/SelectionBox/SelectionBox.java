package View.GameBuilder.SelectionBox;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SelectionBox extends HBox{
	public Button skipLeftButton = new Button("    ");
	public Button SkipRigthButton = new Button("    ");
	public ImageView Player = new ImageView();
	ObservableList<Image> chancelist =FXCollections.observableArrayList ();
	
	public SimpleIntegerProperty currentImage;
	
	public SelectionBox(ObservableList<Image> chancelist,int xsice, int ysice) {
		currentImage = new SimpleIntegerProperty();
		this.chancelist= chancelist; 
		currentImage.set(0);
		
		skipLeftButton.getStyleClass().add("lbutton");
		SkipRigthButton.getStyleClass().add("rbutton");
		
		Player.setImage(chancelist.get(currentImage.getValue()));
		Player.setFitWidth(ysice);
		Player.setFitHeight(xsice);
		
		this.getStylesheets().add(getClass().getResource("../../style.css").toExternalForm());
		
		this.getChildren().addAll(skipLeftButton,Player,SkipRigthButton);
	}
	public void skipRigth() {
		if(chancelist.size() > currentImage.getValue()+1) {
			currentImage.set((int)currentImage.getValue()+1);
		}else{
			currentImage.set(0);
		}
		Player.setImage(chancelist.get(currentImage.getValue()));
		
	}
	public String currentImage() {
		return currentImage.getValue().toString();
	}
	public void skipLeft() {
		if(0 > currentImage.getValue()-1) {
			currentImage.set(chancelist.size()-1);
		}else{
			currentImage.set((int)currentImage.getValue()-1);
		}
		Player.setImage(chancelist.get(currentImage.getValue()));
	}
	public String getImage() {
		String returnVal=Integer.toString(currentImage.intValue()+1);
		return returnVal;
	}

}
