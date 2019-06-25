package View.GameBuilder;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Controller.Main;
import View.GameBuilder.SelectionBox.SelectionBox;
import View.Start.Start;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import properties.Properties;

public class TitelSelection extends VBox{
	public Button zuruek;
	public Label headline;
	public Label underheadline;
	Button leftPlayer;
	Button RigthPlayer;
	Button leftMap;
	Button RigthMap;
	ImageView Player;
	ImageView Map;
	BufferedReader reader;
	public ObservableList<Title> titellist =FXCollections.observableArrayList ();
	ObservableList<Image> playerImageList =FXCollections.observableArrayList ();
	ObservableList<Image> mapImageList =FXCollections.observableArrayList ();
	public ListView<Title> titells;
	public SelectionBox playerchooseView;
	public SelectionBox mapchooseView;
	
	public TitelSelection() {
		
		Image img = new Image(Main.class.getResource("hintergrund.png").toExternalForm());
		BackgroundSize bSize = new BackgroundSize(Properties.WidthWindow, Properties.hightWindow, false, false, true, true);
		Background bg = new Background(new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		
		Font.loadFont(
			Start.class.getResource("SCARE.otf").toExternalForm(), 10
		);
		
		headline = new Label("Spielauswahl");
		headline.getStyleClass().add("settings");
		headline.setPadding(new Insets(25, 0, 0, 0));
		
		underheadline = new Label("Wähle deine Figur, das Spielfeld und das Lied.");
		underheadline.setId("descr");
		underheadline.setPadding(new Insets(25, 0, 75, 0));
		
		playerImageListAnlegen();
		mapImageListAnlegen();
		playerchooseView = new SelectionBox(playerImageList, 100, 100);
		mapchooseView = new SelectionBox(mapImageList, 200, 300);
		MapchooseEventAnmelden();
		PlayerchooseEventAnmelden();
		
		generateTitel("1.txt");
		
		
		titells = new ListView<>();
		titells.setItems(titellist);
//		HBox.setHgrow(titells, Priority.ALWAYS);
		
		VBox right = new VBox();
		right.getChildren().addAll(playerchooseView, mapchooseView);
		playerchooseView.setAlignment(Pos.TOP_CENTER);
		right.setAlignment(Pos.TOP_CENTER);
		right.setSpacing(30);
		
		HBox view = new HBox();
		view.getChildren().addAll(titells, right);
		view.setAlignment(Pos.CENTER);
		view.setSpacing(75);
		
		zuruek = new Button("Zurück");
		HBox zbox = new HBox();
		zbox.getChildren().add(zuruek);
		zbox.setPadding(new Insets(45, 0, 0, 0));
		zbox.setAlignment(Pos.CENTER);
		
		this.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
		
		this.setBackground(bg);
		this.getChildren().addAll(headline, underheadline, view, zbox);
		this.setAlignment(Pos.TOP_CENTER);
		
	}
	public void PlayerchooseEventAnmelden() {
		playerchooseView.skipLeftButton.setOnAction(e->{
			playerchooseView.skipLeft();
		});
		playerchooseView.SkipRigthButton.setOnAction(e->{
			playerchooseView.skipRigth();
		});
	}
	public void MapchooseEventAnmelden() {
		mapchooseView.skipLeftButton.setOnAction(e->{
			mapchooseView.skipLeft();
		});
		mapchooseView.SkipRigthButton.setOnAction(e->{
			mapchooseView.skipRigth();
		});
	}
	public void playerImageListAnlegen() {
		playerImageList.add(new Image("File:Figuren/1/P1SUED.png"));
		playerImageList.add(new Image("File:Figuren/2/P1SUED.png"));
		playerImageList.add(new Image("File:Figuren/3/P1SUED.png"));
		playerImageList.add(new Image("File:Figuren/4/P1SUED.png"));
//		playerImageList.add(new Image("File:Figuren/5/P1SUED.png"));
	}
	public void mapImageListAnlegen() {
		mapImageList.add(new Image("File:Maps/1/MapC.png"));
		mapImageList.add(new Image("File:Maps/2/MapC.png"));
//		mapImageList.add(new Image("File:Maps/3/Map.png"));
	}
	public void generateTitel(String fileName) {
		titellist.removeAll(titellist);
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			String currentline = reader.readLine();
			
			while(currentline != null) {
				
				String [] currentlineSplit = currentline.split(":");
				if(currentlineSplit[0].equals("Song")){
					titellist.add(new Title(currentlineSplit[1]));
				}
				
				currentline = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public void generateTitel(File fileName) {
		titellist.removeAll(titellist);
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			String currentline = reader.readLine();
			
			while(currentline != null) {
				
				String [] currentlineSplit = currentline.split(":");
				if(currentlineSplit[0].equals("Song")){
					titellist.add(new Title(currentlineSplit[1]));
				}
				
				currentline = reader.readLine();
			}
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
