package View;

import Mp3Player.Mp3Player;
import View.BreakMenue.BreakMenueController;
import View.GameBuilder.TitelSelectionController;
import View.GameBuilder.Title;
import View.GameBuilder.TitleCell;
import View.Help.Helper;
import View.Lose.LoseController;
import View.Playground.PlaygroundController;
import View.Playground.PlayArea.Parts.Zombie;
import View.Settings.SettingsController;
import View.Start.StartController;
import View.Win.WinController;
import View.instructions.InstructionsController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties.Properties;

public class ViewController extends Application {
	
	Scene scene;
	boolean generatmoreZombies = true;
	public Stage primaryStage;
	public float titleVolume =(float)0.5;
	public Mp3Player ingamePlayer;
	public Mp3Player menuePlayer = new Mp3Player();
	
	StartController startViewC;
	TitelSelectionController titelSelectionC;
	InstructionsController controlC;
	SettingsController settingsC;
	WinController winviewC;
	LoseController loseC;
	PlaygroundController pGC;
	BreakMenueController breakMenueC;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		ingamePlayer = new Mp3Player();
		
		menuePlayer.loadSong("ZombieSound.mp3");
		menuePlayer.play();
		menuePlayer.loop();

		startViewC = new StartController();
		startViewAnmelden();
		scene = new Scene(startViewC.getView(), Properties.WidthWindow, Properties.hightWindow);
		scene.getStylesheets().add(getClass().getResource("../Controller/application.css").toExternalForm());

		titelSelectionC = new TitelSelectionController(this, scene, primaryStage, menuePlayer, ingamePlayer);
		controlC = new InstructionsController();
		settingsC = new SettingsController(titelSelectionC.getView(), menuePlayer);
		steuerungViewAnmelden();
		gamebuilderViewAnmelden();
		settingsViewAnmelden();
		loseC = new LoseController();
		loseViewAnmelden();
		winviewC = new WinController();
		winViewAnmelden();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public void startViewAnmelden() {

		startViewC.getView().spielStart.setOnAction(e->{
			primaryStage.getScene().setRoot(titelSelectionC.getView());	
		});

		startViewC.getView().einstellung.setOnAction(e->{
			primaryStage.getScene().setRoot(settingsC.getView());	
		});
		
		startViewC.getView().steuerung.setOnAction(e->{
			primaryStage.getScene().setRoot(controlC.getView());
		});
	}
	
	public void steuerungViewAnmelden() {
		
		controlC.getView().zurueck.setOnAction(e-> {
			primaryStage.getScene().setRoot(startViewC.getView());
		});		
	}
	
	public void settingsViewAnmelden() {
		
		settingsC.getView().zuruek.setOnAction(e->{
			primaryStage.getScene().setRoot(startViewC.getView());
		});
	}
	
	public void winViewAnmelden(){
		
		winviewC.getView().menue.setOnAction(e->{
			primaryStage.getScene().setRoot(startViewC.getView());
		});
		
		winviewC.getView().nochmal.setOnAction(e->{
			menuePlayer.Stop();
			pGC = new PlaygroundController(titelSelectionC.getView().playerchooseView.getImage(),titelSelectionC.getView().mapchooseView.getImage(),  pGC.getView().player.currentTitle.getSongName(),ingamePlayer,scene);
			PlaygroudlistenerAnmelden();
			primaryStage.getScene().setRoot(pGC.getView());
			pGC.getView().player.play();

		});
		
		winviewC.getView().spielerview.setOnAction(e->{
			primaryStage.getScene().setRoot(titelSelectionC.getView());
//			SpielauswahlViewListenerAnmelden();
		});
	}
	
	public void loseViewAnmelden() {
		
		loseC.getView().menue.setOnAction(e->{
			primaryStage.getScene().setRoot(startViewC.getView());
//			startViewListenerAnmelden();
		});
		loseC.getView().nochmal.setOnAction(e->{
			menuePlayer.Stop();
			pGC = new PlaygroundController(titelSelectionC.getView().playerchooseView.getImage(),titelSelectionC.getView().mapchooseView.getImage(),  pGC.getView().player.currentTitle.getSongName(),ingamePlayer,scene);
			PlaygroudlistenerAnmelden();
			primaryStage.getScene().setRoot(pGC.getView());
			pGC.getView().player.play();
//			pGC.playgroundview.goOn();

		});
	}
	
	public void gamebuilderViewAnmelden() {
		
		
		
		pGC= new PlaygroundController("Tim",titelSelectionC.getView().playerchooseView.currentImage(), titelSelectionC.getView().titellist.get(0).getSongName(),ingamePlayer,scene);

		titelSelectionC.getView().zuruek.setOnAction(e->{
			primaryStage.getScene().setRoot(startViewC.getView());	
//			pGC= new Playground("Tim",titelSelectionC.getView().playerchooseView.currentImage(), titelSelectionC.getView().titellist.get(0).getSongName(),ingamePlayer);
//			PlaygroudlistenerAnmelden();
		});
		titelSelectionC.getView().titells.setCellFactory((Callback<ListView<Title>, ListCell<Title>>) new Callback<ListView<Title>, ListCell<Title>>() {

			@Override
			public ListCell<Title> call(ListView<Title> param) {
				TitleCell tc= new TitleCell();
				tc.coverbutton.setOnAction(e->{
					pGC= new PlaygroundController(titelSelectionC.getView().playerchooseView.getImage(),titelSelectionC.getView().mapchooseView.getImage(),  tc.item.getSongName(),ingamePlayer,scene);
					pGC.getView().player.Stop();
					pGC.getView().player.play();
					menuePlayer.Stop();
					PlaygroudlistenerAnmelden();
					primaryStage.getScene().setRoot(pGC.getView());
				});
				return tc;
			}

		});

	}

	public void PlaygroudlistenerAnmelden() {
	
		pGC.getView().playArea.getPlayer().ended.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					primaryStage.getScene().setRoot(winviewC.getView());
					ingamePlayer.ended.set(false);
					pGC.getView().playArea.Break();
					menuePlayer.play();

				}

			}
		});
		
		pGC.getView().playArea.playerisdead.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue) {
					primaryStage.getScene().setRoot(loseC.getView());
					pGC.getView().playArea.Break();
					menuePlayer.play();

				}
			}
		});
		
		pGC.getView().playArea.zombieList.addListener(new ListChangeListener<Zombie>() {

			@Override
			public void onChanged(Change<? extends Zombie> c) {
				if(pGC.getView().playArea.zombieList != null) {

					pGC.getView().playArea.getChildren().removeAll(pGC.getView().playArea.zombieList);
//					pGC.playArea.getChildren().add(c);
					pGC.getView().playArea.getChildren().addAll(pGC.getView().playArea.zombieList);
				}



			}

		});
		
		
		pGC.getView().headline.setOnMouseClicked(e->{

			pGC.getView().headline.menue.setDisable(false);
			pGC.getView().headline.menue.setOnAction(f->{
				breakMenueC = new BreakMenueController(pGC.getView());

				primaryStage.getScene().setRoot(breakMenueC.getView());
				pGC.getView().playArea.Break();
				menuePlayer.play();
				BreakViewAnmelden();
				
			});
		});

	}
	
	public void BreakViewAnmelden(){
		
		breakMenueC.getView().buttons.einstellung.setOnAction(e->{
			settingsC.getView().setiwas();
			primaryStage.getScene().setRoot(settingsC.getView());
		});
		breakMenueC.getView().buttons.menue.setOnAction(e->{
			primaryStage.getScene().setRoot(startViewC.getView());
			startViewAnmelden();
		});
		breakMenueC.getView().buttons.neustart.setOnAction(e->{
			menuePlayer.Stop();
			pGC = new PlaygroundController(titelSelectionC.getView().playerchooseView.getImage(),titelSelectionC.getView().mapchooseView.getImage(),  pGC.getView().player.currentTitle.getSongName(),ingamePlayer,scene);
// 		  	primaryStage.getScene().setRoot(null);
			PlaygroudlistenerAnmelden();
			primaryStage.getScene().setRoot(pGC.getView());
			pGC.getView().player.play();
			//			pGC.playgroundview.goOn();
			
		});
		breakMenueC.getView().buttons.play.setOnAction(e->{
			menuePlayer.Stop();
			pGC.getView().playArea.goOn();
			//			primaryStage.getScene().setRoot(startview);
			//			primaryStage.getScene().setRoot(null);
			primaryStage.getScene().setRoot(new Helper(pGC.getView()));

		});
	}
}
