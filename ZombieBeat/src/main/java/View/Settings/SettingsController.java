package View.Settings;

import java.io.File;

import Mp3Player.Mp3Player;
import View.GameBuilder.TitelSelection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class SettingsController {
	private Settings settingsC;
	
	public SettingsController(TitelSelection titelSelection, Mp3Player menuePlayer) {
		settingsC = new Settings();
		initialize(titelSelection, menuePlayer);
		
	}
	
	private void initialize(TitelSelection titelSelection, Mp3Player menuePlayer) {
		
		settingsC.SoundeffektvolSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed( ObservableValue<? extends Number> ov,
					Number oldValue,
					Number newValue){
				menuePlayer.SetVolume(newValue.floatValue()/10);
			}
		});
		settingsC.currentTitel.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != settingsC.currentTitel
						&& event.getDragboard().hasFiles()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		settingsC.currentTitel.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					for(File f : db.getFiles()) {
						titelSelection.generateTitel(f);
						settingsC.currentTitel.setText(f.getName());
					}


					success = true;
				}
				event.setDropCompleted(success);

				event.consume();
			}
		});
	}
	
	public Settings getView() {
		return settingsC;
	}
}
