package View.Playground.PlayArea.Parts;

import java.util.ArrayList;

import Controller.MyImageView;
import javafx.application.Platform;
import javafx.scene.image.Image;

import properties.Direction;
import properties.Properties;

import properties.isObject;

public class Bullet extends MyImageView implements isObject{
	private Direction lineOfSight;
	public static final int HIGHT = Properties.BULLETHIGHT;
	public static final int WIDTH = Properties.BULLETWIDTH;
	public static final int FIRERATE = Properties.FIRERATE;
	
	private boolean isDestroy = false;
	private boolean freeze = false;
	private ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> wallCollectionVerticalList;
	private Thread Trajectory ;


	public Bullet(int startPosY, int startPosX, Direction lineOfSight,ArrayList<WallCollectionVertical> WallCollectionVerticalList,ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList) {
		this.lineOfSight = lineOfSight;
		this.wallCollectionHorizontalList = wallCollectionHorizontalList;
		this.wallCollectionVerticalList = WallCollectionVerticalList;

		switch(lineOfSight) {
			case NORD:
				setLayoutX(startPosX+(Properties.CHARACTERWIDTH/2));
				setLayoutY(startPosY);
				break;
			case WEST:
				setLayoutX(startPosX);
				setLayoutY(startPosY+(Properties.CHARACTERHIGHT/2));
				break;
			case SUED:
				setLayoutX(startPosX+(Properties.CHARACTERWIDTH/2));
				setLayoutY(startPosY+Properties.CHARACTERHIGHT);
				break;
			case OST:
				setLayoutX(startPosX+(Properties.CHARACTERWIDTH));
				setLayoutY(startPosY+(Properties.CHARACTERHIGHT/2));
				break;
			default:break;
		}
		
		this.setImage(new Image("File:"+Properties.BULLETPICTURE));
		this.setFitHeight(HIGHT);
		this.setFitWidth(WIDTH);
	}
/**
 * Method to calculate the Trajectory of the bullet
 */
	public void calculateTrajectory() {
		
		Trajectory = new Thread() {
			public void run() {
				while(!interrupted()) {
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							if(!freeze) {
								switch(lineOfSight) {
									case NORD:
										trymove(getPosY()-Properties.FIRERATE, getPosX());
										break;
									case WEST:
										trymove(getPosY(), getPosX()-Properties.FIRERATE);
										break;
									case SUED:
										trymove(getPosY()+Properties.FIRERATE, getPosX());
										break;
									case OST:
										trymove(getPosY(), getPosX()+Properties.FIRERATE);
										break;
									default:
										break;
								}
							}
						}
					});
					try {Thread.sleep(250);} catch (InterruptedException e) {interrupt();}
					if(isDestroy) {
						break;
					}
				}
			}
		};Trajectory.start();

	}
	public void trymove(int y, int x) {
		if(wallChecktMove(this, x, y, wallCollectionVerticalList, wallCollectionHorizontalList)) {
		}else {destroy();}
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
	public void destroy() {
		this.setImage(new Image("File:"));
		Trajectory.interrupt();
		isDestroy = true;
	}
	public void notFreeze() {
		freeze = !freeze;
	}

}
