package View.Playground.PlayArea.Parts;

import java.util.ArrayList;

import javafx.scene.layout.HBox;

import properties.isObject;

public class WallCollectionHorizontal extends HBox implements isObject{
	private int numberOfWall;
	public ArrayList<Wall> wl= new ArrayList<Wall>();
	
	public WallCollectionHorizontal(int yPosition, int xPosition, int numberOfWall) {
		this.numberOfWall = numberOfWall;
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		Wall itw;
		for (int i = 0; i< numberOfWall; i++) {
			itw = new Wall(i,getPosX(),getPosY(),'H');
			wl.add(itw);
			this.getChildren().add(itw);
		}
	}
	
	@Override
	public int getMyWidth() {
		return Wall.HEIGHT * numberOfWall;
	}
	@Override
	public int getMyHight() {
		return Wall.HEIGHT;
	}
	@Override
	public int getPosX() {
		return (int) getLayoutX();
	}
	@Override
	public int getPosY() {
		return (int) getLayoutY();
	}
	
	
	
}
