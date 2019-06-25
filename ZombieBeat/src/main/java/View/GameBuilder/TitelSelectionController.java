package View.GameBuilder;

import Mp3Player.Mp3Player;
import View.ViewController;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TitelSelectionController {
	 TitelSelection titelSelection;
	
	public TitelSelectionController(ViewController view, Scene scene, Stage primaryStage, Mp3Player menuePlayer, Mp3Player ingamePlayer) {
		titelSelection= new TitelSelection();
		
		initialize(view, scene, primaryStage, menuePlayer, ingamePlayer);

		
	}
	
	public void initialize(ViewController view, Scene scene, Stage primaryStage, Mp3Player menuePlayer, Mp3Player ingamePlayer) {
		titelSelection.titellist.addListener(new ListChangeListener<Title>() {

			@Override
			public void onChanged(Change<? extends Title> c) {
				titelSelection.titells.setItems(titelSelection.titellist);

			}

		});
		
		
	}
	
	public TitelSelection getView() {
		return titelSelection;
	}
}
