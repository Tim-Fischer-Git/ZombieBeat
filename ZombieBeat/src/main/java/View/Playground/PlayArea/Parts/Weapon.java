package View.Playground.PlayArea.Parts;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import properties.Direction;

public class Weapon extends ImageView{
	public ObservableList<Bullet> bulletList;
	private ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> wallCollectionVerticalList;
	
	public Weapon(ArrayList<WallCollectionVertical> wallCollectionVerticalList,ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList) {
		bulletList =FXCollections.observableArrayList ();
		this.wallCollectionVerticalList = wallCollectionVerticalList;
		this.wallCollectionHorizontalList = wallCollectionHorizontalList;
	}
	public void shot(int xPos, int yPos,Direction lineOfSight){
		Bullet bullet =new Bullet(yPos, xPos, lineOfSight,wallCollectionVerticalList,wallCollectionHorizontalList);
		bullet.calculateTrajectory();
		bulletList.add(bullet);
	}
	
}
