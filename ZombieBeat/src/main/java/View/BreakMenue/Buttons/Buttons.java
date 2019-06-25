package View.BreakMenue.Buttons;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Buttons extends VBox{
	public Button menue;
	public Button neustart;
	public Button play;
	public Button einstellung;
	public Buttons() {
		menue = new Button("Menü");
		menue.getStyleClass().add("pausebutton");
		neustart = new Button("Neustart");
		neustart.getStyleClass().add("pausebutton");
		play = new Button("Fortsetzen");
		play.getStyleClass().add("pausebutton");
		
		// Inaktiv
		einstellung = new Button("Einstellungen");
		einstellung.getStyleClass().add("pausebutton");

		this.getStylesheets().add(getClass().getResource("../../style.css").toExternalForm());
		
		this.getChildren().addAll(play,neustart,menue);
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
	}

}
