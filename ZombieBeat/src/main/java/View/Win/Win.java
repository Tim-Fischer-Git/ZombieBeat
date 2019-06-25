package View.Win;

import Controller.Main;
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
import properties.Properties;

public class Win extends VBox{
	Label mes1;
	Label mes2;
	public Button nochmal;
	public Button menue;
	public	Button spielerview;

	public Win() {
		
		Image img = new Image(Main.class.getResource("hintergrund.png").toExternalForm());
		BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
		Background bg = new Background(new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		
		mes1 = new Label("Diese Apokalypse hast du überlebt,");
		mes2 = new Label("schaffst du auch die nächste?");
		mes1.getStyleClass().add("win");
		mes2.getStyleClass().add("win");
		VBox txt = new VBox();
		txt.getChildren().addAll(mes1,mes2);
		txt.setPadding(new Insets(150, 0, 50, 0));
		txt.setAlignment(Pos.CENTER);
		
		nochmal = new Button("Nochmal");
		nochmal.getStyleClass().add("nochmal");
		menue = new Button("Menü");
		menue.getStyleClass().add("menue");
		spielerview = new Button("Spielauswahl");
		spielerview.getStyleClass().add("menue");
		
		this.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
		
		
		this.setBackground(bg);
		this.getChildren().addAll(txt,nochmal,spielerview,menue);
		this.setSpacing(5);
		this.setAlignment(Pos.TOP_CENTER);
	}
}
