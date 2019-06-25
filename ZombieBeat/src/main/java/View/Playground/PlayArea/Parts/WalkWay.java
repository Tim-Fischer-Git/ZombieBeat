package View.Playground.PlayArea.Parts;

import java.util.LinkedList;



import properties.Direction;

public class WalkWay{
	private int current;
	public Direction circul [] ={Direction.NORD,Direction.NORDOST,Direction.OST,Direction.SUEDOST,Direction.SUED,Direction.SUEDWEST,Direction.WEST,Direction.NORDWEST};
	public WalkWay(Direction direction) {
		current = direction.getZahl()-1;
	}
	public Direction getcurrent() {
		Direction returnValue = circul[current];
		if(current+1 >= circul.length) {
			current = 0;
		}else {
			current++;
		}
		return returnValue;
	}
	
}

