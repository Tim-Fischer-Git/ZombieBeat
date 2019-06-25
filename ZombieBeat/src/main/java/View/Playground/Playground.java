package View.Playground;


import java.util.ArrayList;

import Mp3Player.Mp3Player;
import View.Playground.Headline.Headline;
import View.Playground.PlayArea.PlayArea;
import View.Playground.PlayArea.Parts.WallCollectionHorizontal;
import View.Playground.PlayArea.Parts.WallCollectionVertical;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import properties.Properties;

public class Playground extends VBox{
	public Headline headline;
	public PlayArea playArea;
	private Image [] PlayerSkinImage ;
	private String background;
	public Mp3Player player;
	private ArrayList<WallCollectionHorizontal> wallistR;
	private ArrayList<WallCollectionVertical> wallistup;

	
	public Playground(String PlayerSkin, String MapSkin,String Title, Mp3Player inGamePlayer) {
				
		//Spieler Laden
			this.PlayerSkinImage = new Image[5];
			PlayerSkinImage[0]= new Image("File:Figuren/"+PlayerSkin+"/P1NORD.png");
			PlayerSkinImage[1]= new Image("File:Figuren/"+PlayerSkin+"/P1SUED.png");
			PlayerSkinImage[2]= new Image("File:Figuren/"+PlayerSkin+"/P1WEST.png");
			PlayerSkinImage[3]= new Image("File:Figuren/"+PlayerSkin+"/P1OST.png");
			PlayerSkinImage[4]= new Image("File:PlayerTOT.png");
			
			headline= new Headline(PlayerSkinImage[1]);
			headline.menue.setDisable(true);
		
		//liedLaden
		player = inGamePlayer;
		player.loadSong(Title);
		InfoviewListenerAnmelden();
		
		// Map Laden
			Image img = new Image("File:Maps/"+MapSkin+"/Map.png");
			BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
			Background bg = new Background(new BackgroundImage(img,
					BackgroundRepeat.NO_REPEAT,
		            BackgroundRepeat.NO_REPEAT,
		            BackgroundPosition.CENTER,
		            bSize));
			
			this.setBackground(bg);
			
			wallistR= new ArrayList<>();
			wallistup = new ArrayList<>();
			
			if(MapSkin.equals("1")) {
				
				wallistR.add(new WallCollectionHorizontal(230, 0, 1));
				wallistR.add(new WallCollectionHorizontal(190, 40, 1));
				wallistR.add(new WallCollectionHorizontal(150, 80, 2));
				wallistR.add(new WallCollectionHorizontal(100, 170, 2));
				wallistR.add(new WallCollectionHorizontal(180, 425, 4));
				wallistR.add(new WallCollectionHorizontal(600, 1025, 2));
				wallistR.add(new WallCollectionHorizontal(500, 700, 2));
				wallistR.add(new WallCollectionHorizontal(670, 555, 6));
				wallistR.add(new WallCollectionHorizontal(50, 850, 6));
				
				wallistup.add(new WallCollectionVertical(600, 1025, 3));
				wallistup.add(new WallCollectionVertical(485, 540, 3));
				wallistup.add(new WallCollectionVertical(300, 700, 5));
				
			} else if(MapSkin.equals("2")) {
				
				wallistR.add(new WallCollectionHorizontal(15, 60, 6));
				wallistR.add(new WallCollectionHorizontal(615, 457, 12));
				wallistR.add(new WallCollectionHorizontal(100, 480, 7));
				
				wallistup.add(new WallCollectionVertical(265, 300, 7));
				wallistup.add(new WallCollectionVertical(7, 1032, 3));
				wallistup.add(new WallCollectionVertical(235, 1140, 4));
			}

		
		playArea = new PlayArea(PlayerSkinImage,player,background,wallistR, wallistup);
		playArea.setOnMouseClicked(e->{
			headline.menue.setDisable(true);
		});
		this.getChildren().addAll(headline,playArea);
	}
	public void InfoviewListenerAnmelden() {
		headline.slider.setMax(player.getCurrentTitle().getLength());
		player.currentTime.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				headline.slider.setValue(((double)newValue));
				
			}
		});
	}
	public void deabelEffect() {
		BoxBlur bb = new BoxBlur();
		bb.setHeight(50);
		bb.setWidth(50);
		bb.setIterations(0);
		
		this.setEffect(bb);
	}
	
	

}
