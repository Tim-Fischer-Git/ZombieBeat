package View.Playground.PlayArea.Parts;

import java.util.ArrayList;

import Controller.MyImageView;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import properties.Direction;
import properties.Properties;
import properties.isObject;

public class Zombie2 extends MyImageView implements isObject{
	public SimpleObjectProperty<Direction> lineOfSight;
	private int currentCharacterX = 0;
	private int currentCharacterY = 0;
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
	public Zombie2 hi= this;
	Thread theadWalk;


	public Zombie2(int x, int y,ArrayList<WallCollectionVertical> wallCollectionVerticalList,ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList, MyImageView character) {
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


		walk();

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

	private void walk() {
		final MyImageView self =this;
		Thread theadWalk = new Thread () {
			public void run() {

				while (true) {
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							if(!freeze) {
								if(getLayoutX() != character.getLayoutX() && getLayoutY() != character.getLayoutY()) {
									if(getLayoutX()> character.getLayoutX()&& getLayoutY()> character.getLayoutY()) {
										trymove(getPosX()-CROSSWISE, getPosY()-CROSSWISE);
										lineOfSight.set(Direction.NORDWEST);
									} else if(getLayoutX()> character.getLayoutX()&&getLayoutY()< character.getLayoutY()){
										trymove(getPosX()-CROSSWISE, getPosY()+CROSSWISE);
										lineOfSight.set(Direction.SUEDWEST);
									}else if(getLayoutX()< character.getLayoutX()&&getLayoutY()> character.getLayoutY()){
										trymove(getPosX()+CROSSWISE, getPosY()-CROSSWISE);
										lineOfSight.set(Direction.NORDOST);
									}else if(getLayoutX()< character.getLayoutX()&&getLayoutY()< character.getLayoutY()){
										trymove(getPosX()+CROSSWISE, getPosY()+CROSSWISE);
										lineOfSight.set(Direction.SUEDOST);
									}
								}else if(getLayoutX()!= character.getLayoutX()) {
									if(getLayoutX()> character.getLayoutX()) {
										trymove(getPosX()-STEP, getPosY());
										lineOfSight.set(Direction.WEST);
									}else {
										trymove(getPosX()+STEP, getPosY());
										lineOfSight.set(Direction.OST);
									}
								}else {
									if(getLayoutY()> character.getLayoutY()) {
										trymove(getPosX(), getPosY()-CROSSWISE);
										lineOfSight.set(Direction.NORD);
									}else {
										trymove(getPosX(), getPosY()+CROSSWISE);
										lineOfSight.set(Direction.SUED);
									}
								}
							}
						}
					});

					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		theadWalk.start();
	}
//	public trymove1(Direction waldDirection,int x, int y) {
//		
//	}
	
	public void trymove(int x, int y) {

		if(wallChecktMove(this, x, y, wallCollectionVerticalList, wallCollectionHorizontalList)) {
			
		}else {
			
		}
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
