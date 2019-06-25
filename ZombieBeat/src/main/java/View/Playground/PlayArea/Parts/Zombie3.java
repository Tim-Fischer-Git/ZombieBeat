package View.Playground.PlayArea.Parts;

import java.util.ArrayList;
import java.util.LinkedList;

import Controller.MyImageView;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import properties.Direction;
import properties.Properties;
import properties.isObject;

public class Zombie3 extends MyImageView implements isObject{
	public SimpleObjectProperty<Direction> lineOfSight;
	public final int WIDTH = Properties.CHARACTERWIDTH;
	public final int HEIGHT = Properties.CHARACTERHIGHT;
	public final int STEP = Properties.CHARACTERSTEP;
	public final int CROSSWISE = Properties.step2;
	private boolean freeze = false;
	private MyImageView character = null;
	private ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> wallCollectionVerticalList;
	public int x;
	public int y;
	boolean found = false;
	public Zombie3 hi= this;
//	private boolean recdel = false;
//	private boolean recend = false;
	
	
	private LinkedList<Direction> finalWalkWay = new LinkedList<>();
	private LinkedList<Direction> walkWayCal = new LinkedList<>();
//	private LinkedList<Direction> walkWayCalsafe = new LinkedList<>();
	Thread theadWalk;


	public Zombie3(int x, int y,ArrayList<WallCollectionVertical> wallCollectionVerticalList,ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList, MyImageView character) {
		this.wallCollectionVerticalList = wallCollectionVerticalList;
		this.wallCollectionHorizontalList = wallCollectionHorizontalList;
		this.character = character;
		lineOfSight = new SimpleObjectProperty<>();
		//position setzen
		setLayoutX(x);
		setLayoutY(y);
		lineOfSight.set(Direction.NORD);
		setImage(new Image("File:ZombieNORD.png"));
		setView();

		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);


		walkCalculator();

	}
	public void setView() {
		lineOfSight.addListener(new ChangeListener<Direction>() {

			@Override
			public void changed(ObservableValue<? extends Direction> observable, Direction oldValue,
					Direction newValue) {
				if(!freeze) {
					switch(lineOfSight.getValue()) {
					case NORD:
						setImage(new Image("File:ZombieNORD.png"));
						break;
					case NORDOST:
						setImage(new Image("File:ZombieNORDOST.png"));
						break;
					case OST:
						setImage(new Image("File:ZombieOST.png"));
						break;
					case SUEDOST:
						setImage(new Image("File:ZombieSUEDOST.png"));
						break;
					case SUED:
						setImage(new Image("File:ZombieSUED.png"));
						break;
					case SUEDWEST:
						setImage(new Image("File:ZombieSUEDWEST.png"));
						break;
					case WEST:
						setImage(new Image("File:ZombieWEST.png"));
						break;
					case NORDWEST:
						setImage(new Image("File:ZombieNORDWEST.png"));
						break;
					}
				}
			}
		});

	}
//	public Direction calDirection() {
//		
//		if(getLayoutX() != character.getLayoutX() && getLayoutY() != character.getLayoutY()) {
//			if(getLayoutX()> character.getLayoutX()&& getLayoutY()> character.getLayoutY()) {
//				return Direction.NORDWEST;
//			} else if(getLayoutX()> character.getLayoutX()&&getLayoutY()< character.getLayoutY()){
//				return Direction.SUEDWEST;
//			}else if(getLayoutX()< character.getLayoutX()&&getLayoutY()> character.getLayoutY()){
//				return Direction.NORDOST;
//			}else if(getLayoutX()< character.getLayoutX()&&getLayoutY()< character.getLayoutY()){
//				return Direction.SUEDOST;
//			}
//		}else if(getLayoutX()!= character.getLayoutX()) {
//			if(getLayoutX()> character.getLayoutX()) {
//				return Direction.WEST;
//			}else {
//				return Direction.OST;
//			}
//		}else {
//			if(getLayoutY()> character.getLayoutY()) {
//				return Direction.NORD;
//			}else {
//				return Direction.SUED;
//		
//			}
//		}
//		return null;
//	}
	private Direction calDirection(int x ,int y) {
		
		if(x != character.getLayoutX() && y != character.getLayoutY()) {
			if(x> character.getLayoutX()&& y> character.getLayoutY()) {
				return Direction.NORDWEST;
			} else if(x> character.getLayoutX()&&y< character.getLayoutY()){
				return Direction.SUEDWEST;
			}else if(x< character.getLayoutX()&&y> character.getLayoutY()){
				return Direction.NORDOST;
//			}else if(x< character.getLayoutX()&&y< character.getLayoutY()){
//				return Direction.SUEDOST;
			}else {
				return Direction.SUEDOST;
			}
		}else if(x!= character.getLayoutX()) {
			if(x> character.getLayoutX()) {
				return Direction.WEST;
			}else {
				return Direction.OST;
			}
		}else {
			if(y> character.getLayoutY()) {
				return Direction.NORD;
			}else {
				return Direction.SUED;
		
			}
		}
		
	}
	private void shit(LinkedList<Direction> lst, int x, int y) {
		WalkWay circul = new WalkWay(calDirection(x,y));
		int [] array ;
		Direction safe ;
		for(int triedDirections = 0; triedDirections<Properties.DIRECTIONOCCURRENCE; triedDirections++) {
			safe =circul.getcurrent();
			array = walk(safe);
			if(wallCheckt(this, array[0], array[1], wallCollectionVerticalList, wallCollectionHorizontalList)) {
				lst.add(safe);
				if(character.getBoundsInParent().intersects(array[0], array[1], this.WIDTH, this.HEIGHT)) {
					if( walkWayCal != null) {
						if( walkWayCal.size() > lst.size()) {
							walkWayCal = lst;
						}
					}else {
						walkWayCal = lst;
					}
					lst.removeLast();
					return ;
				}
				
				shit(lst,array[0],array[1]);
				lst.removeLast();
			}
		}
		
		return;
	}

	private void walkCalculator() {
		finalWalkWay = new LinkedList<>();
		Thread wayClaThrea = new Thread() {
			public void run() {
				while (true) {

					shit(new LinkedList<Direction>(), (int) getLayoutX(), (int)getLayoutY());
					finalWalkWay = walkWayCal;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};wayClaThrea.start();
		
		Thread theadWalk = new Thread () {
			public void run() {

				while (true) {
					
					int safe[] = walk(!finalWalkWay.isEmpty()? finalWalkWay.getFirst(): Direction.NIX);
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							moveObject(safe[0], safe[1]);
							if(!finalWalkWay.isEmpty()) {
								finalWalkWay.removeFirst();
							}
							
						}
					});
					
					
					
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		theadWalk.start();
	}

	public int[] walk(Direction nextStepDirection) {
		int array[] = new int[2];
		switch(nextStepDirection) {
		case NORD:
			array[0] = getPosX();
			array[1] = getPosY()-CROSSWISE;
			lineOfSight.set(Direction.NORD);
			break;
		case NORDOST:
			array[0] = getPosX()+CROSSWISE;
			array[1] = getPosY()-CROSSWISE;
			lineOfSight.set(Direction.NORDOST);
			break;
		case OST:
			array[0] = getPosX()+STEP;
			array[1] = getPosY();
			lineOfSight.set(Direction.OST);
			break;
		case SUEDOST:
			array[0] = getPosX()+CROSSWISE;
			array[1] = getPosY()+CROSSWISE;
			lineOfSight.set(Direction.SUEDOST);
			break;
		case SUED:
			array[0] = getPosX();
			array[1] = getPosY()+CROSSWISE;
			lineOfSight.set(Direction.SUED);
			break;
		case SUEDWEST:
			array[0] = getPosX()-CROSSWISE;
			array[1] = getPosY()+CROSSWISE;
			lineOfSight.set(Direction.SUEDWEST);
			break;
		case WEST:
			array[0] = getPosX()-STEP;
			array[1] = getPosY();
			lineOfSight.set(Direction.WEST);
			break;
		case NORDWEST:
			array[0] = getPosX()-CROSSWISE;
			array[1] = getPosY()-CROSSWISE;
			lineOfSight.set(Direction.NORDWEST);
			break;
		case NIX:
			break;
		
		}
		return array;
	}

	@Override
	public int getMyWidth() {
		return WIDTH;
	}
	@Override
	public int getMyHight() {
		return HEIGHT;
	}
	@Override
	public int getPosX() {
		return (int)getLayoutX();
	}
	@Override
	public int getPosY() {
		return (int)getLayoutY();
	}
	public void die() {
		this.setImage(new Image("File:Zombieblut.png"));
//		theadWalk.interrupt();
		freeze = true;
	}
	public void killedPlayer() {
		freeze = true;
	}
	public void notFreeze() {
		freeze = !freeze;
	}

}
