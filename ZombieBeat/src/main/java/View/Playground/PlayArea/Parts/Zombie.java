package View.Playground.PlayArea.Parts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Controller.MyImageView;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import properties.Direction;
import properties.Properties;
import properties.isObject;

public class Zombie extends MyImageView implements isObject {
	private SimpleObjectProperty<Direction> lineOfSight;
	private final int WIDTH = Properties.CHARACTERWIDTH;
	private final int HEIGHT = Properties.CHARACTERHIGHT;
	private final int STEP = Properties.CHARACTERSTEP;
	private final int CROSSWISE = Properties.step2;
	private boolean freeze = false;
	private MyImageView character = null;
	private ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> wallCollectionVerticalList;
	private int[] afterWalkedPos;
	private LinkedList<Direction> finalWalkWay = new LinkedList<>();
	
	private Thread walkthread;
	private Thread wayClaThrea;
	

	public Zombie(int x, int y, ArrayList<WallCollectionVertical> wallCollectionVerticalList,
			ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList, MyImageView character) {
		this.wallCollectionVerticalList = wallCollectionVerticalList;
		this.wallCollectionHorizontalList = wallCollectionHorizontalList;
		this.character = character;
		lineOfSight = new SimpleObjectProperty<>();
		// position setzen
		setLayoutX(x);
		setLayoutY(y);
		lineOfSight.set(Direction.NORD);
		setImage(new Image("File:ZombieNORD.png"));
		setView();

		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);

		Walk();

	}

	public void setView() {
		lineOfSight.addListener(new ChangeListener<Direction>() {

			@Override
			public void changed(ObservableValue<? extends Direction> observable, Direction oldValue,
					Direction newValue) {
				if (!freeze) {
					switch (lineOfSight.getValue()) {
					case NORD:
						setImage(new Image("File:Figuren/Zombie/ZombieNORD.png"));
						break;
					case NORDOST:
						setImage(new Image("File:Figuren/Zombie/ZombieNORDOST.png"));
						break;
					case OST:
						setImage(new Image("File:Figuren/Zombie/ZombieOST.png"));
						break;
					case SUEDOST:
						setImage(new Image("File:Figuren/Zombie/ZombieSUEDOST.png"));
						break;
					case SUED:
						setImage(new Image("File:Figuren/Zombie/ZombieSUED.png"));
						break;
					case SUEDWEST:
						setImage(new Image("File:Figuren/Zombie/ZombieSUEDWEST.png"));
						break;
					case WEST:
						setImage(new Image("File:Figuren/Zombie/ZombieWEST.png"));
						break;
					case NORDWEST:
						setImage(new Image("File:Figuren/Zombie/ZombieNORDWEST.png"));
						break;
					}
				}
			}
		});

	}

	private int[] calculateplayerNode(MyImageView mIV) {
		int tempPos[] = new int[2];
		if (((int) mIV.getLayoutX()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[0] = (int) mIV.getLayoutX() / Properties.NODESIZE;
		} else {
			tempPos[0] = (int) mIV.getLayoutX() / Properties.NODESIZE + 1;
		}
		if (((int) mIV.getLayoutY()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[1] = (int) mIV.getLayoutY() / Properties.NODESIZE;
		} else {
			tempPos[1] = (int) mIV.getLayoutY() / Properties.NODESIZE + 1;
		}
		return tempPos;
	}

	private int[] calculatBlockNode(isObject WC) {
		
		int tempPos[] = new int[2];
		
		if (((int) WC.getPosX()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[0] = (int) WC.getPosX() / Properties.NODESIZE;
		} else {
			tempPos[0] = (int) WC.getPosX() / Properties.NODESIZE + 1;
		}
		if (((int) WC.getPosY()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[1] = (int) WC.getPosY() / Properties.NODESIZE;
		} else {
			tempPos[1] = (int) WC.getPosY() / Properties.NODESIZE + 1;
		}
		return tempPos;
	}

	public LinkedList<Direction> calculateWalkWay(LinkedList<Direction> lst) {
		int temp[] = calculateplayerNode(this);
		this.afterWalkedPos = new int[] { (int) getLayoutX(), (int) getLayoutY() };
		Node initialNode = new Node(temp[0], temp[1]);
		temp = calculateplayerNode(character);
		Node finalNode = new Node(temp[0], temp[1]);
		ArrayList<int[]> Arlst1 = new ArrayList<>();
		int rows = (Properties.WidthWindow / Properties.NODESIZE)+1;
		int cols = (Properties.hightWindow / Properties.NODESIZE)+1;

		Astern aStar = new Astern(rows, cols, initialNode, finalNode);
		for (WallCollectionHorizontal WCH : wallCollectionHorizontalList) {
			for(Wall w : WCH.wl) {
				Arlst1.add(calculatBlockNode(w));
			}
			
		}
		for (WallCollectionVertical WCV : wallCollectionVerticalList) {
			for(Wall w : WCV.wl) {
				Arlst1.add(calculatBlockNode(w));
			}
		}
		int[][] blocksArray = new int[Arlst1.size()][2];
//		int[][] blocksArray1 = new int[][]{{1, 3}, {2, 3}, {3, 3}};
		for (int i = 0; i < Arlst1.size(); i++) {
			blocksArray[i][0] = Arlst1.get(i)[0];
			blocksArray[i][1] = Arlst1.get(i)[1];
		}

		aStar.setBlocks(blocksArray);
		List<Node> path = aStar.findPath();
		for (Node node : path) {
			lst.addAll(convertTodirectionlist(new LinkedList<Direction>(), new int[] { node.getRow(), node.getCol() }));
		}

		return lst;
	}

	boolean realBigger(int isBigger, int than) {
		int x =isBigger-than;
		if( x>STEP) {
			return true;
		}else {
			return false;
		}
	}
	boolean realSmaller(int isSmaller, int than) {
		int x =than-isSmaller;
		if( x>STEP) {
			return true;
		}else {
			return false;
		}
	}
	boolean eql(int a, int b) {
		int x =a-b;
		if( -STEP<=x && x<=STEP) {
			return true;
		}else {
			return false;
		}
	}

	public LinkedList<Direction> convertTodirectionlist(LinkedList<Direction> lst, int[] NodePos) {
		NodePos[0]*=Properties.NODESIZE;
		NodePos[1]*=Properties.NODESIZE;
		while(!eql(afterWalkedPos[0],NodePos[0])||!eql(afterWalkedPos[1],NodePos[1])) {
			
			if (!eql(afterWalkedPos[0],NodePos[0])&&!eql(afterWalkedPos[1],NodePos[1])) {
				
				if (realBigger(afterWalkedPos[0],NodePos[0]) && realBigger(afterWalkedPos[1],NodePos[1])) {
					afterWalkedPos[0] -= CROSSWISE;
					afterWalkedPos[1] -= CROSSWISE;
					lst.add(Direction.NORDWEST);
				} else if (realBigger(afterWalkedPos[0],NodePos[0]) && realSmaller(afterWalkedPos[1],NodePos[1])) {
					afterWalkedPos[0] -= CROSSWISE;
					afterWalkedPos[1] += CROSSWISE;
					lst.add(Direction.SUEDWEST);

				} else if (realSmaller(afterWalkedPos[0],NodePos[0]) && realBigger(afterWalkedPos[1],NodePos[1])) {
					afterWalkedPos[0] += CROSSWISE;
					afterWalkedPos[1] -= CROSSWISE;
					lst.add(Direction.NORDOST);

				} else if (realSmaller(afterWalkedPos[0],NodePos[0]) && realSmaller(afterWalkedPos[1],NodePos[1])) {
					afterWalkedPos[0] += CROSSWISE;
					afterWalkedPos[1] += CROSSWISE;
					lst.add(Direction.SUEDOST);

				}
			} else if (!eql(afterWalkedPos[0],NodePos[0])) {
				if (realBigger(afterWalkedPos[0],NodePos[0])) {
					afterWalkedPos[0] -= STEP;
					lst.add(Direction.WEST);
				} else {
					afterWalkedPos[0] += STEP;
					lst.add(Direction.OST);
				}
			} else {
				if (realBigger(afterWalkedPos[1],NodePos[1])) {
					afterWalkedPos[1] -= STEP;
					lst.add(Direction.NORD);
				} else {
					afterWalkedPos[1] += STEP;
					lst.add(Direction.SUED);
				}
			}
		}
		return lst;
	}
	

	private void Walk() {
		finalWalkWay = new LinkedList<>();
		wayClaThrea = new Thread() {
			public void run() {
			
				while (!isInterrupted()) {

					finalWalkWay = calculateWalkWay(new LinkedList<Direction>());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						interrupt();
						
					}
				}
			}
		};
		wayClaThrea.start();

		Thread theadWalk = new Thread() {
			public void run() {
				moveCeck();
			}
		};
		theadWalk.start();
	}

	public void moveCeck() {
		 walkthread = new Thread() {
			public void run() {
				while (!isInterrupted()) {
					if (finalWalkWay != null && !freeze) {
						if (!finalWalkWay.isEmpty()) {
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									move(finalWalkWay.getFirst());
									finalWalkWay.removeFirst();

								}
							});

							
						}

					}
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						interrupt();
						
					}

				}
			}
		};
		walkthread.start();

	}

	public void move(Direction nextStepDirection) {

		switch (nextStepDirection) {
		case NORD:
			this.setLayoutX(getPosX());
			this.setLayoutY(getPosY() - STEP);
			lineOfSight.set(Direction.NORD);
			break;
		case NORDOST:
			this.setLayoutX(getPosX() + CROSSWISE);
			this.setLayoutY(getPosY() - CROSSWISE);
			lineOfSight.set(Direction.NORDOST);
			break;
		case OST:
			this.setLayoutX(getPosX() + STEP);
			this.setLayoutY(getPosY());
			lineOfSight.set(Direction.OST);
			break;
		case SUEDOST:
			this.setLayoutX(getPosX() + CROSSWISE);
			this.setLayoutY(getPosY() + CROSSWISE);
			lineOfSight.set(Direction.SUEDOST);
			break;
		case SUED:
			this.setLayoutX(getPosX());
			this.setLayoutY(getPosY() + STEP);
			lineOfSight.set(Direction.SUED);
			break;
		case SUEDWEST:
			this.setLayoutX(getPosX() - CROSSWISE);
			this.setLayoutY(getPosY() + CROSSWISE);
			lineOfSight.set(Direction.SUEDWEST);
			break;
		case WEST:
			this.setLayoutX(getPosX() - STEP);
			this.setLayoutY(getPosY());
			lineOfSight.set(Direction.WEST);
			break;
		case NORDWEST:
			this.setLayoutX(getPosX() - CROSSWISE);
			this.setLayoutY(getPosY() - CROSSWISE);
			lineOfSight.set(Direction.NORDWEST);
			break;
		case NIX:
			break;

		}
	}

	@Override
	public int getMyWidth() {
		return WIDTH;
	}

	@Override
	public int getMyHight() {
		return HEIGHT;
	}

	@Override
	public int getPosX() {
		return (int) getLayoutX();
	}

	@Override
	public int getPosY() {
		return (int) getLayoutY();
	}

	public void die() {
		this.setImage(new Image("File:Zombieblut.png"));
		walkthread.interrupt(); 
		wayClaThrea.interrupt();
		freeze = true;
	}

	public void killedPlayer() {
		freeze = true;
	}

	public void notFreeze() {
		freeze = !freeze;
	}

}
