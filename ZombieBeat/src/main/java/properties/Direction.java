package properties;

public enum Direction {
	NORD(1),OST(3),SUED(5),WEST(7),NORDOST(2),SUEDOST(4),SUEDWEST(6),NORDWEST(8),NIX(0);
	int zahl;
	public int getZahl() {
		return zahl;
	}
	private Direction(int zahl) {
		this.zahl = zahl;
	}
	
}
