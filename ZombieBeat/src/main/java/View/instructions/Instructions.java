package View.instructions;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties.Properties;

public class Instructions extends VBox {
	public Button right;
	public Button left;
	public Button top;
	public Button down;
	public Button shoot;
	public Button zurueck;
	public Label info;
	public Label infospace;
	
	Label headline;
	Label descrh;
	Label descr;
	
	public Instructions() {
		
		Image img = new Image(Main.class.getResource("hintergrund.png").toExternalForm());
		BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
		Background bg = new Background(new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		
		headline = new Label("Steuerung");
		headline.getStyleClass().add("settings");
		headline.setPadding(new Insets(125, 0, 25, 0));
		
		descrh = new Label("Belege deine Steuerung wie du willst!");
		descrh.setId("descr");
		
		descr = new Label("Um eine Taste neu zu belegen, wähle die gewünschte Taste und belege neu durch tippen.");
		descr.setId("descr");
		descr.setPadding(new Insets(0, 0, 75, 0));
		
		top = new Button();
		top.setId("tmove");
		
		left = new Button();
		left.setId("lmove");
		down = new Button();
		down.setId("dmove");
		right = new Button();
		right.setId("rmove");
		HBox ctr = new HBox();
		ctr.getChildren().addAll(left, down, right);
		ctr.setAlignment(Pos.CENTER);
		ctr.setSpacing(25);
		ctr.setPadding(new Insets(25, 0, 25, 0));
		
		shoot = new Button("Shoot Button");
		info = new Label();
		info.setId("infobox");
		infospace = new Label();
		
		zurueck = new Button("Zurück");
		HBox zbox = new HBox();
		zbox.getChildren().add(zurueck);
		zbox.setPadding(new Insets(50, 0, 0, 0));
		zbox.setAlignment(Pos.BOTTOM_CENTER);
		
		this.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
		
		this.setBackground(bg);
		this.getChildren().addAll(headline, descrh, descr, top, ctr, shoot, infospace, info, zbox);
		this.setAlignment(Pos.TOP_CENTER);
	}
}
