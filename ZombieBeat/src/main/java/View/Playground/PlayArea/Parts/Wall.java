package View.Playground.PlayArea.Parts;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import properties.Properties;
import properties.isObject;

public class Wall extends ImageView implements isObject{
	public static final int HEIGHT = Properties.BRICKSIZE;
	public static final int WIDTH = Properties.BRICKSIZE;
	private int parentx;
	private int parenty;
	public Wall(int index, int x, int y,char a) {
		if(a =='V') {
			this.parentx = x;
			this.parenty = y+(HEIGHT*(index));
		}else {
			this.parentx = x+(WIDTH*(index));
			this.parenty = y;
		}
		
		this.setImage(new Image("File:"+Properties.WALLPICTURE));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
	}
	public int getMyWidth() {
		return Wall.HEIGHT;
	}
	@Override
	public int getMyHight() {
		return Wall.HEIGHT;
	}
	@Override
	public int getPosX() {
		return (int) parentx;
	}
	@Override
	public int getPosY() {
		return (int) parenty;
	}

}
