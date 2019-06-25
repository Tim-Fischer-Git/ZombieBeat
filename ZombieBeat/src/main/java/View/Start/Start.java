package View.Start;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import properties.Properties;

public class Start extends VBox{
	public Label titel;
	public Button spielStart;
	public Button steuerung;
	public Button einstellung;
	public Start () {
		
		Image img = new Image("https://download-free-clip.art/wp-content/uploads/2018/03/halloween-background-clipart-haunted-houses.jpg");
		BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
		Background bg = new Background(new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		
		Font.loadFont(
				Start.class.getResource("SCARE.otf").toExternalForm(), 10
		);
		
		titel = new Label("ZombieBeat");
		titel.getStyleClass().add("titel");
		titel.setPadding(new Insets(150, 0, 75, 0));

		spielStart = new Button ("Spiel Start");
		steuerung = new Button ("Steuerung");
		einstellung = new Button("Einstellungen");
		
		this.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
		
		this.setBackground(bg);
		this.getChildren().addAll(titel, spielStart, steuerung, einstellung);
		this.setSpacing(5);
		this.setAlignment(Pos.TOP_CENTER);
	}
}
