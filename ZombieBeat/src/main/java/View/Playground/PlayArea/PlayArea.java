package View.Playground.PlayArea;


import java.util.ArrayList;

import Mp3Player.Mp3Player;
import View.Playground.PlayArea.Parts.Bullet;
import View.Playground.PlayArea.Parts.Character;
import View.Playground.PlayArea.Parts.WallCollectionHorizontal;
import View.Playground.PlayArea.Parts.WallCollectionVertical;
import View.Playground.PlayArea.Parts.Zombie;
import ddf.minim.analysis.BeatDetect;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import properties.Properties;
import properties.isObject;

public class PlayArea extends Pane{

	private BeatDetect beatDetect = new BeatDetect();
	private Mp3Player player;
	public ObservableList<Zombie> zombieList;
	public ObservableList<Bullet> bulletList;
	private ArrayList<Zombie> deleteZombieList;
	private ArrayList<Bullet> deleteBulletList;
	private ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> WallCollectionVerticalList;
	private Character character;
	private boolean produceMoreZombies = true;
	public SimpleBooleanProperty playerisdead;

	public PlayArea(Image [] playerPicturs, Mp3Player mp3Player, String backgroundImage, 
			ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList,
			ArrayList<WallCollectionVertical> wallCollectionVerticalList) {

		this.wallCollectionHorizontalList = wallCollectionHorizontalList;
		this.WallCollectionVerticalList = wallCollectionVerticalList;
		this.player = mp3Player;

		playerisdead = new SimpleBooleanProperty();
		playerisdead.set(false);

		character = new Character(playerPicturs,this.WallCollectionVerticalList,this.wallCollectionHorizontalList);
		bulletList = FXCollections.observableArrayList ();
		zombieList = FXCollections.observableArrayList ();
//		zombieList.add(ZombieSpawn());

		bulletList = character.getWeapon().bulletList;

		//		

		this.getChildren().add(character);
		this.getChildren().addAll(zombieList);
		this.getChildren().addAll(bulletList);
		this.getChildren().addAll(this.wallCollectionHorizontalList);
		this.getChildren().addAll(this.WallCollectionVerticalList);
		this.setStyle(backgroundImage);

		startGame();
		startZombieProduce();

	}
	public void Break() {
		character.notFreeze();
		for(Bullet b:bulletList) {
			b.notFreeze();
		}
		for(Zombie z:zombieList) {
			z.notFreeze();
		}
		player.Break();
		produceMoreZombies = false;
	}
	public void goOn() {
		character.notFreeze();
		for(Bullet b:bulletList) {
			b.notFreeze();
		}
		for(Zombie z:zombieList) {
			z.notFreeze();
		}
		player.play();
		produceMoreZombies =true;
	}
	/**
	 * Method to start start creation of zombies. 
	 * Using beatDetection of the Minim Player.
	 */
	public void startZombieProduce() {

		Thread zombieProduce = new Thread(new Runnable() {

			@Override
			public void run() {
				

				beatDetect.setSensitivity(500); 
				while(true) {
					beatDetect.detect(player.player.left);
					
					if( beatDetect.isOnset()) {
						if(produceMoreZombies) {
							Platform.runLater(new Runnable(){
		
								@Override
								public void run() {
									
											zombieList.add(ZombieSpawn());
								}
						
							});
							try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
						}
					}
				}

			}
		});

		zombieProduce.start();
	}
	/**
	 * Create new zombie with random position
	 * 
	 * @return zombie-object
	 */
	public Zombie ZombieSpawn() {

		int yPosition, xPosition;
		int mathrandom =(int)(Math.random()*4);
		if(mathrandom == 1) {
			xPosition = 0;
			yPosition=(int)	(Math.random()*Properties.hightWindow);
		}else if(mathrandom == 2) {
			yPosition = 0;
			xPosition =(int)(Math.random()*Properties.WidthWindow);
		}else if(mathrandom == 3) {
			yPosition = Properties.hightWindow;
			xPosition =(int)	(Math.random()*Properties.hightWindow);
		}else{
			xPosition = Properties.WidthWindow;
			yPosition =(int)	(Math.random()*Properties.WidthWindow);
		}

		return new Zombie(xPosition,yPosition,WallCollectionVerticalList,wallCollectionHorizontalList,character);
	}
	/**
	 * Method to check intersection of two objects 
	 * @param objOne object one 
	 * @param objTwo object two
	 * @return true if objects intersect else false 
	 */
	public static boolean objectMeet(isObject objOne, isObject objTwo) {
		
		/* for each Posion of object one */
		for(int xCountObjOne = objOne.getPosX() ; xCountObjOne <= objOne.getPosX() + objOne.getMyWidth() ; xCountObjOne++ ) {
			for(int yCountObjOne = objOne.getPosY() ; yCountObjOne <= objOne.getPosY() + objOne.getMyHight() ; yCountObjOne++ ) {
				/* for each Posion of object one */
				for(int xCountObjTwo = objTwo.getPosX() ; xCountObjTwo <= objTwo.getPosX() +objTwo.getMyWidth() ; xCountObjTwo++ ) {
					for(int yCountObjTwo = objTwo.getPosY() ; yCountObjTwo <= objTwo.getPosY() +objTwo.getMyHight() ; yCountObjTwo++ ) {

						if(xCountObjOne==xCountObjTwo & yCountObjOne==yCountObjTwo) {
							return true;
						}
					}
				}
			}
		}		
		return false;
	}
	/**
	 * Method to check collision of zombie,bullet and the character
	 * @param thisPane this current PlaygroundArea
	 */
	public void startGame() {

		Thread collisionThread = new Thread() {

			public void run() {

				while(true) {

					Platform.runLater(new Runnable(){

						@Override
						public void run() {

							deleteZombieList = new ArrayList<>();
							deleteBulletList = new ArrayList<>();
							for(Zombie zombie: zombieList) {

								if(character.getBoundsInParent().intersects(zombie.getBoundsInParent())) {
									character.die();
									playerisdead.set(true);
									disableAllZombies();
									return;
								}

								for(Bullet bullet : character.getWeapon().bulletList) {

									
									if(zombie.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
//										zombie.intersects(bullet.getBoundsInParent());
										zombie.die();
										zombieList.remove(zombie);
										bullet.destroy();
										character.getWeapon().bulletList.remove(bullet);
									}

								}
							}
							character.toFront();
							

//							for(Zombie zombie: deleteZombieList) {
//								
//							}
//							for(Bullet bullet : deleteBulletList) {
//								
//							}


						}
					});
					try {Thread.sleep(150);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		};

		collisionThread.start();
	}
	/**
	 * Method to disable All zombies and stop the zombie creation
	 */
	public void disableAllZombies() {
		produceMoreZombies = false;
		for(Zombie z : zombieList) {
			z.killedPlayer();
		}
	}
	/**
	 * 
	 * @return
	 */
	public Mp3Player getPlayer() {
		return player;
	}
	
	public Character getCharacter() {
		return character;
	}
}

