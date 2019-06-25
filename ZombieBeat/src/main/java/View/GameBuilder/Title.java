package View.GameBuilder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Title {
	
	private String titleName; 
	private float length;
	private String interpret;
	private Image image;
	private String album;
	private String fielName;
	
	public String getSongName() {
		return fielName;
	}

	public Image getImage() {
		return image;
	}

	public String getAlbum() {
		return album;
	}
	public String toString() {
		return titleName;
	}
	public Title(String Name) {
		try {
			Mp3File song = new Mp3File(Name);
			this.fielName = Name;
			if(song.hasId3v2Tag()) {
				this.titleName = song.getId3v2Tag().getTitle();
				this.length = song.getLengthInMilliseconds()/1000;
				this.interpret = song.getId3v2Tag().getArtist();
				this.album = song.getId3v2Tag().getAlbum();
				
			    byte[] imageData = song.getId3v2Tag().getAlbumImage();
			    //converting the bytes to an image
			    if(imageData != null) {
			    	  	BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
			    	  
						image = SwingFXUtils.toFXImage(img, null);
			    } else {
			    	image = new Image("File:default-cover.jpg");
			    }
			  
			}
			
			
		} catch (UnsupportedTagException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public String getTitlename() {
		return titleName;
	}
	
	public String getInterpret() {
		return interpret;
	}
	
	public float getLength() {
		return length;
	}
}
