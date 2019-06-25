package View.Playground;

import Mp3Player.Mp3Player;
import View.Playground.PlayArea.Parts.Bullet;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import properties.Properties;

public class PlaygroundController {
	Playground pGC;
	
	public PlaygroundController(String PlayerSkin, String MapSkin,String Title, Mp3Player inGamePlayer, Scene scene) {
		pGC = new Playground(PlayerSkin, MapSkin, Title, inGamePlayer);

		initialize(scene);
	}
	
	private void initialize(Scene scene) {
		pGC.playArea.bulletList.addListener(new ListChangeListener<Bullet>() {

			@Override
			public void onChanged(Change<? extends Bullet> c) {
				pGC.playArea.getChildren().removeAll(pGC.playArea.bulletList);
				pGC.playArea.getChildren().addAll(pGC.playArea.bulletList);

			}



		});

		scene.setOnKeyPressed(
				new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent keyEvent){
						Thread thread = new Thread() {
							public void run() {
								Platform.runLater(new Runnable(){
									public void run() {


										if(keyEvent.getCode().toString().equals(Properties.LEFT)) {
											pGC.playArea.getCharacter().walkLeft();
										}
										if(keyEvent.getCode().toString().equals(Properties.RIGHT)) {
											pGC.playArea.getCharacter().walkRigth();
										}
										if(keyEvent.getCode().toString().equals(Properties.TOP)) {
											pGC.playArea.getCharacter().walkUp();
										}
										if(keyEvent.getCode().toString().equals(Properties.DOWN)) {
											pGC.playArea.getCharacter().walkDown();
										}if(keyEvent.getCode().toString().equals(Properties.SHOOT)) {
											pGC.playArea.getCharacter().shot();
										}
									}

								});
							}
						};
						thread.start();

					}
				});

	}
	
	public Playground getView() {
		return pGC;
	}
}
