package View.Lose;

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

public class Lose extends VBox{
	public Button nochmal;
	public Button menue;
	private Label gameOver;
	public Lose() {
		
		Image img = new Image(Main.class.getResource("lose.jpg").toExternalForm());
		BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
		Background bg = new Background(new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		
		gameOver = new Label("Game Over");
		gameOver.getStyleClass().add("gameover");
		gameOver.setPadding(new Insets(150, 0, 50, 0));
		
		nochmal = new Button("Nochmal");
		nochmal.getStyleClass().add("nochmal");
		menue = new Button("MenÜ");
		menue.getStyleClass().add("menue");
		
		this.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
		
		this.setBackground(bg);
		this.getChildren().addAll(gameOver,nochmal,menue);
		this.setSpacing(5);
		this.setAlignment(Pos.TOP_CENTER);
	}
}
