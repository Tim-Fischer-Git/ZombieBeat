package View.BreakMenue;

import View.BreakMenue.Buttons.Buttons;
import View.Playground.Playground;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.StackPane;

public class BreakMenue extends StackPane{
	public Buttons buttons;
	public BreakMenue(Playground pGC) {
		buttons = new Buttons();
		
		BoxBlur bb = new BoxBlur();
		bb.setHeight(this.getHeight());
		bb.setWidth(this.getWidth());
		bb.setIterations(3);
		
		ColorAdjust adj = new ColorAdjust(0, 0, -0.3, 0);
		adj.setInput(bb);
	
		this.getChildren().addAll(pGC, buttons);
	}
	
}
