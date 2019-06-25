package View.Settings;

import Controller.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import properties.Properties;

public class Settings extends VBox{
	HBox headlinebox;
	public Slider volumenslider;
	public Slider SoundeffektvolSlider;
	HBox volumenBox;
	HBox soundBox;
	public Button zuruek;
	Label headline;
	public Button susu;
	private Label volumetext;
	private Label soundtext;
	HBox currentTitelBox;
	public Label currentTitel;
	public Settings() {
		
		Image img = new Image(Main.class.getResource("hintergrund.png").toExternalForm());
		BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
		Background bg = new Background(new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		
		headline = new Label("Einstellungen");
		headline.getStyleClass().add("settings");
		headline.setPadding(new Insets(125, 0, 90, 0));
		
		
//		Inaktiv
		volumetext= new Label("Musiklautstärke");
		volumetext.setId("setlab");
		volumenBox= new HBox();
		volumenslider= new Slider();
		volumenslider.setMax(10);
		volumenslider.setValue(5);
		volumenBox.getChildren().addAll(volumetext,volumenslider);
		volumenBox.setPadding(new Insets(5, 0, 15, 0));
		volumenBox.setAlignment(Pos.CENTER);
		
		soundtext= new Label("Soundeffekte");
		soundtext.setId("setlab");
		soundBox = new HBox();
		SoundeffektvolSlider = new Slider();
		SoundeffektvolSlider.setMax(20);
		SoundeffektvolSlider.setValue(14);
		SoundeffektvolSlider.setId("settingslider");
		HBox.setHgrow(SoundeffektvolSlider, Priority.SOMETIMES);
		soundBox.getChildren().addAll(soundtext,SoundeffektvolSlider);
		soundBox.setPadding(new Insets(5, 525, 75, 525));
		soundBox.setAlignment(Pos.CENTER);
		
		currentTitelBox = new HBox();
		currentTitel = new Label("Import Playlist");
		currentTitel.setId("import");
		currentTitelBox.getChildren().add(currentTitel);
		currentTitelBox.setPadding(new Insets(0, 0, 50, 0));
		currentTitelBox.setAlignment(Pos.CENTER);
		
		zuruek = new Button("Zurück");
		HBox zbox = new HBox();
		zbox.getChildren().add(zuruek);
		zbox.setPadding(new Insets(145, 0, 0, 0));
		zbox.setAlignment(Pos.CENTER);
		
		this.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
		
		this.setBackground(bg);
		this.getChildren().addAll(headline, soundtext, soundBox, currentTitelBox, zbox);
		this.setAlignment(Pos.TOP_CENTER);
	}
	
	public void setiwas() {
		susu.setDisable(false);
	}
}
