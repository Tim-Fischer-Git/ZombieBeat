package View.Playground.PlayArea.Parts;

import java.util.ArrayList;

import Controller.MyImageView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import properties.Direction;
import properties.Properties;
import properties.isObject;

public class Character extends MyImageView implements isObject{
	public static final int STEP= Properties.CHARACTERSTEP;
	private static final int WIDTH = Properties.CHARACTERWIDTH;
	private static final int HIGHT = Properties.CHARACTERHIGHT;
	private Weapon weapon; 
	private boolean freeze = false;
	private Image image[];
	private ArrayList<WallCollectionHorizontal> WallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> WallCollectionVerticalList;

	public SimpleObjectProperty<Direction> lineOfSight;
	
	/**
	 * 
	 * @param image 0 = Nord
	 * 				1 = SUED
	 * 				2 = WEST
	 * 				3 = OST
	 * 				4 = TOT
	 */
	public Character(Image [] image, ArrayList<WallCollectionVertical> WallCollectionVerticalList,ArrayList<WallCollectionHorizontal> WallCollectionHorizontalList) {
		this.WallCollectionHorizontalList = WallCollectionHorizontalList;
		this.WallCollectionVerticalList = WallCollectionVerticalList;
		this.image = image;
		weapon = new Weapon(WallCollectionVerticalList,WallCollectionHorizontalList);
		lineOfSight = new SimpleObjectProperty<>();
		
		setLayoutX(Properties.CHARACTERSTARTX);
		setLayoutY(Properties.CHARACTERSTARTY);
		setImage(image[0]);
		this.setFitHeight(HIGHT);
		this.setFitWidth(WIDTH);
		lineOfSight.set(Direction.NORD);
		setView();
	}
	
	public void setView() {
		lineOfSight.addListener(new ChangeListener<Direction>() {

			@Override
			public void changed(ObservableValue<? extends Direction> observable, Direction oldValue,
					Direction newValue) {
				switch(lineOfSight.getValue()){
					
					case NORD:
						setImage(image[0]);
						break;
					case SUED:
						setImage(image[1]);
						break;
					case WEST:
						setImage(image[2]);
						break;
					case OST:
						setImage(image[3]);
						break;
					default:
						break;
				}
			}
		});
	}

	public void walkRigth() {
		if(!freeze) {
			wallChecktMove(this, getPosX()+STEP, getPosY(), WallCollectionVerticalList, WallCollectionHorizontalList);
			lineOfSight.set(Direction.OST);
		}
	}

	public void walkLeft() {
		if(!freeze) {
			wallChecktMove(this, getPosX()-STEP, getPosY(), WallCollectionVerticalList, WallCollectionHorizontalList);
			lineOfSight.set(Direction.WEST);
		}
	}

	public void walkUp() {
		if(!freeze) {
			wallChecktMove(this, getPosX(), getPosY()-STEP, WallCollectionVerticalList, WallCollectionHorizontalList);
			lineOfSight.set(Direction.NORD);
		}
	}
	
	public void walkDown() {
		if(!freeze) {
			wallChecktMove(this, getPosX(), getPosY()+STEP, WallCollectionVerticalList, WallCollectionHorizontalList);
			lineOfSight.set(Direction.SUED);
		}
	}
	
	
	public void shot() {
		if(!freeze) {
			weapon.shot(getPosX(),getPosY(), lineOfSight.getValue());
		}
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	@Override
	public int getMyWidth() {
		return WIDTH;
	}
	@Override
	public int getMyHight() {
		return HIGHT;
	}
	@Override
	public int getPosX() {
		return (int) getLayoutX();
	}
	@Override
	public int getPosY() {
		return (int) getLayoutY();
	}
	public void die() {
		setImage(image[4]);
		freeze = true;
	}
	public void notFreeze() {
		freeze = !freeze;
	}
	
}
