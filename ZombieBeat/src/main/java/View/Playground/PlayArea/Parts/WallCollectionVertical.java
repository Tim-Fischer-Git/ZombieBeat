package View.Playground.PlayArea.Parts;

import java.util.ArrayList;

import javafx.scene.layout.VBox;
import properties.isObject;

public class WallCollectionVertical extends VBox implements isObject{
	private int numberOfWall;
	public ArrayList<Wall> wl= new ArrayList<Wall>();
	
	public WallCollectionVertical(int y, int x, int numberOfWall) {
		this.numberOfWall = numberOfWall;
		this.setLayoutX(x);
		this.setLayoutY(y);
		Wall itw;
		for (int i = 0; i< numberOfWall; i++) {
			itw =new Wall(i,getPosX(),getPosY(),'V');
			wl.add(itw);
			this.getChildren().add(itw);
		}
	}
	
	@Override
	public int getMyWidth() {
		return Wall.WIDTH;
	}
	@Override
	public int getMyHight() {
		return Wall.HEIGHT * numberOfWall;
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
