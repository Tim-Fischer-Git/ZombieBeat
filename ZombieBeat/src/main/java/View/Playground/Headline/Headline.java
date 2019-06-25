package View.Playground.Headline;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import properties.Properties;

public class Headline extends HBox {
//	public WaveVisualization waveVisualization;
	public Button menue;
	public Slider slider;
	public ImageView PlayerLogo;
	public ImageView WaponLogo;
	private int width = Properties.CHARACTERWIDTH;
	private int higth = Properties.CHARACTERHIGHT;
	
	public Headline(Image PlayerSkin) {
		
		menue = new Button("Menü");

		slider = new Slider ();
		slider.setMinWidth(500);
		slider.setDisable(true);
		
		PlayerLogo = new ImageView();
		PlayerLogo.setImage(PlayerSkin);
		PlayerLogo.setFitHeight(higth);
		PlayerLogo.setFitWidth(width);
		
		this.getStylesheets().add(getClass().getResource("../../style.css").toExternalForm());
		
		this.getChildren().addAll(menue, slider, PlayerLogo);
		this.setSpacing(20);
		this.setAlignment(Pos.CENTER);
		
	}

}
