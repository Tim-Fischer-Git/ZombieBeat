package Controller;

import java.util.ArrayList;

import View.Playground.PlayArea.Parts.WallCollectionHorizontal;
import View.Playground.PlayArea.Parts.WallCollectionVertical;
import javafx.scene.image.ImageView;
import properties.isObject;

public class MyImageView extends ImageView{
	boolean helper = true;
	
	/**
	 * Method to move an object 
	 * @param moveToX  x - position object should be moved to.
	 * @param moveToY y -position object should be moved to.
	 */
	public void moveObject(int moveToX, int moveToY) {
		this.setLayoutX(moveToX);
		this.setLayoutY(moveToY);
	}
	
	public boolean wallChecktMove1(isObject currentObj,int xPosToMove, int yPosToMove,ArrayList<WallCollectionVertical> wallCollectionVertical,ArrayList<WallCollectionHorizontal> wallCollectionHorizontal) {
	final MyImageView self = this;
		Thread thread = new Thread() {
			public void run() {
						for(int currentXPos = xPosToMove; currentXPos <= xPosToMove+currentObj.getMyWidth(); currentXPos++) {
							for(int currentYPos = yPosToMove; currentYPos <= yPosToMove+currentObj.getMyHight(); currentYPos++) {
							
								for(WallCollectionVertical currentwall: wallCollectionVertical) {
									for(int currentXPosWall = currentwall.getPosX(); currentXPosWall <= currentwall.getPosX()+currentwall.getMyWidth(); currentXPosWall++) {
										for(int currentYPosWall = currentwall.getPosY(); currentYPosWall <= currentwall.getPosY()+currentwall.getMyHight(); currentYPosWall++) {
											if(currentXPos==currentXPosWall & currentYPos==currentYPosWall) {
												helper = false;
											}
										}
									}
								}
								
								for(WallCollectionHorizontal currentwall: wallCollectionHorizontal) {
									for(int currentXPosWall = currentwall.getPosX(); currentXPosWall <= currentwall.getPosX()+currentwall.getMyWidth(); currentXPosWall++) {
										for(int currentYPosWall = currentwall.getPosY(); currentYPosWall <= currentwall.getPosY()+currentwall.getMyHight(); currentYPosWall++) {
											if(currentXPos==currentXPosWall & currentYPos==currentYPosWall) {
												helper = false;
												
											}
										}
									}
								}
							}
						}
						
	
			}
			
		
		};
		thread.start();
		if(helper) {
			moveObject(xPosToMove, yPosToMove);
			return true;}
		else {
			helper = true;
			return false;
		}
		
	}
	public boolean wallChecktMove(isObject currentObj,int xPosToMove, int yPosToMove,ArrayList<WallCollectionVertical> wallCollectionVertical,ArrayList<WallCollectionHorizontal> wallCollectionHorizontal) {
		Thread thread1 = new Thread() {
			public void run() {
				for(WallCollectionVertical currentwall: wallCollectionVertical) {
					if(currentwall.getBoundsInParent().intersects(xPosToMove, yPosToMove, currentObj.getMyWidth(), currentObj.getMyHight())) {
						helper = false;
						break;
					}
				}
				for(WallCollectionHorizontal currentwall: wallCollectionHorizontal) {
					if(currentwall.getBoundsInParent().intersects(xPosToMove, yPosToMove, currentObj.getMyWidth(), currentObj.getMyHight())) {
						helper = false;
						break;
					}
				}
			}
		};thread1.start();
		
		if(helper) {
			moveObject(xPosToMove, yPosToMove);
			return true;}
		else {
			helper = true;
			return false;
		}
			
	}
	public boolean wallCheckt(isObject currentObj,int xPosToMove, int yPosToMove,ArrayList<WallCollectionVertical> wallCollectionVertical,ArrayList<WallCollectionHorizontal> wallCollectionHorizontal) {
		Thread thread2 = new Thread() {
			public void run() {
				for(WallCollectionVertical currentwall: wallCollectionVertical) {
					if(currentwall.getBoundsInParent().intersects(xPosToMove, yPosToMove, currentObj.getMyWidth(), currentObj.getMyHight())) {
						helper = false;
						break;
					}
				}
				for(WallCollectionHorizontal currentwall: wallCollectionHorizontal) {
					if(currentwall.getBoundsInParent().intersects(xPosToMove, yPosToMove, currentObj.getMyWidth(), currentObj.getMyHight())) {
						helper = false;
						break;
					}
				}
			}
		};thread2.start();
		
		if(helper) {
			return true;}
		else {
			helper = true;
			return false;
		}
			
	}
}
