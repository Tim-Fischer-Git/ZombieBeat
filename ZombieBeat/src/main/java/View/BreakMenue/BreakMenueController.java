package View.BreakMenue;

import View.Playground.Playground;

public class BreakMenueController {
	private BreakMenue breakMenueC;
	
	public BreakMenueController(Playground pGC) {
		breakMenueC = new BreakMenue(pGC);	
	}
	
	public BreakMenue getView() {
		return breakMenueC;
	}
}
