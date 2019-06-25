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
import javafx.scene.layout.Pane;
import properties.Direction;
import properties.Properties;
import properties.isObject;

public class Zombie4 extends MyImageView implements isObject {
	public SimpleObjectProperty<Direction> lineOfSight;
	public final int WIDTH = Properties.CHARACTERWIDTH;
	public final int HEIGHT = Properties.CHARACTERHIGHT;
	public final int STEP = Properties.CHARACTERSTEP;
	public final int CROSSWISE = Properties.step2;
	private boolean freeze = false;
	private MyImageView character = null;
	private ArrayList<WallCollectionHorizontal> wallCollectionHorizontalList;
	private ArrayList<WallCollectionVertical> wallCollectionVerticalList;
	public int x;
	public int y;
	boolean found = false;
	public Zombie4 hi = this;
	public int[] walkedPos;
//	private boolean recdel = false;
//	private boolean recend = false;

	private LinkedList<Direction> finalWalkWay = new LinkedList<>();

	public Zombie4(int x, int y, ArrayList<WallCollectionVertical> wallCollectionVerticalList,
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

		walkCalculator();

	}

	public void setView() {
		lineOfSight.addListener(new ChangeListener<Direction>() {

			@Override
			public void changed(ObservableValue<? extends Direction> observable, Direction oldValue,
					Direction newValue) {
				if (!freeze) {
					switch (lineOfSight.getValue()) {
					case NORD:
						setImage(new Image("File:ZombieNORD.png"));
						break;
					case NORDOST:
						setImage(new Image("File:ZombieNORDOST.png"));
						break;
					case OST:
						setImage(new Image("File:ZombieOST.png"));
						break;
					case SUEDOST:
						setImage(new Image("File:ZombieSUEDOST.png"));
						break;
					case SUED:
						setImage(new Image("File:ZombieSUED.png"));
						break;
					case SUEDWEST:
						setImage(new Image("File:ZombieSUEDWEST.png"));
						break;
					case WEST:
						setImage(new Image("File:ZombieWEST.png"));
						break;
					case NORDWEST:
						setImage(new Image("File:ZombieNORDWEST.png"));
						break;
					}
				}
			}
		});

	}

	private int[] CalNode(MyImageView mIV) {
		int tempPos[] = new int[2];
		if (((int) mIV.getLayoutX()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[0] = (int) getLayoutX() / Properties.WidthWindow;
		} else {
			tempPos[0] = (int) getLayoutX() / Properties.WidthWindow + 1;
		}
		if (((int) mIV.getLayoutY()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[1] = (int) getLayoutY() / Properties.WidthWindow;
		} else {
			tempPos[1] = (int) getLayoutY() / Properties.WidthWindow + 1;
		}
		return tempPos;
	}

	private int[] calBlockArray(Pane WC) {
		int tempPos[] = new int[2];
		if (((int) WC.getLayoutX()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[0] = (int) getLayoutX() / Properties.WidthWindow;
		} else {
			tempPos[0] = (int) getLayoutX() / Properties.WidthWindow + 1;
		}
		if (((int) WC.getLayoutY()) % Properties.NODESIZE < (Properties.NODESIZE / 2)) {
			tempPos[1] = (int) getLayoutY() / Properties.WidthWindow;
		} else {
			tempPos[1] = (int) getLayoutY() / Properties.WidthWindow + 1;
		}
		return tempPos;
	}

	public LinkedList<Direction> shit(LinkedList<Direction> lst) {
		int temp[] = CalNode(this);
		this.walkedPos = new int[] { (int) getLayoutX(), (int) getLayoutY() };
		Node initialNode = new Node(temp[0], temp[1]);
		temp = CalNode(character);
		Node finalNode = new Node(temp[0], temp[1]);
		ArrayList<int[]> Arlst1 = new ArrayList<>();
		int rows = Properties.WidthWindow / Properties.NODESIZE;
		int cols = Properties.hightWindow / Properties.NODESIZE;

		Astern aStar = new Astern(rows, cols, initialNode, finalNode);
		for (WallCollectionHorizontal WCH : wallCollectionHorizontalList) {
			Arlst1.add(calBlockArray(WCH));
		}
		for (WallCollectionVertical WCV : wallCollectionVerticalList) {
			Arlst1.add(calBlockArray(WCV));
		}
		int[][] blocksArray = new int[Arlst1.size()][2];

		for (int i = 0; i < Arlst1.size(); i++) {
			blocksArray[i][0] = Arlst1.get(i)[0];
			blocksArray[i][1] = Arlst1.get(i)[1];
		}

		aStar.setBlocks(blocksArray);
		List<Node> path = aStar.findPath();
		for (Node node : path) {
			lst.addAll(calWalPart(new LinkedList<Direction>(), new int[] { node.getRow(), node.getCol() }));
		}

		return lst;
	}

	public LinkedList<Direction> calWalPart(LinkedList<Direction> lst, int[] NodePos) {
		while (walkedPos[0] != NodePos[0] || walkedPos[1] != NodePos[1]) {

			if (walkedPos[0] != NodePos[0] && walkedPos[1] != NodePos[1]) {
				if (walkedPos[0] > NodePos[0] && walkedPos[1] > NodePos[1]) {
					walkedPos[0] -= CROSSWISE;
					walkedPos[1] -= CROSSWISE;
					lst.add(Direction.NORDWEST);
				} else if (walkedPos[0] > NodePos[0] && walkedPos[1] < NodePos[1]) {
					walkedPos[0] -= CROSSWISE;
					walkedPos[1] += CROSSWISE;
					lst.add(Direction.SUEDWEST);

				} else if (walkedPos[0] < NodePos[0] && walkedPos[1] > NodePos[1]) {
					walkedPos[0] += CROSSWISE;
					walkedPos[1] -= CROSSWISE;
					lst.add(Direction.NORDOST);

				} else if (walkedPos[0] < NodePos[0] && walkedPos[1] < NodePos[1]) {
					walkedPos[0] += CROSSWISE;
					walkedPos[1] += CROSSWISE;
					lst.add(Direction.SUEDOST);

				}
			} else if (walkedPos[0] != NodePos[0]) {
				if (walkedPos[0] > NodePos[0]) {
					walkedPos[0] -= STEP;
					lst.add(Direction.WEST);
				} else {
					walkedPos[0] += STEP;
					lst.add(Direction.OST);
				}
			} else {
				if (walkedPos[1] > NodePos[1]) {
					walkedPos[1] -= STEP;
					lst.add(Direction.NORD);
				} else {
					walkedPos[1] += STEP;
					lst.add(Direction.SUED);
				}
			}
		}
		return lst;
	}

	private void walkCalculator() {
		finalWalkWay = new LinkedList<>();
		Thread wayClaThrea = new Thread() {
			public void run() {
				while (true) {

					finalWalkWay = shit(new LinkedList<Direction>());
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		wayClaThrea.start();

		Thread theadWalk = new Thread() {
			public void run() {
				Walk();
			}
		};
		theadWalk.start();
	}

	public void Walk() {
		System.out.println("1");
		Thread t = new Thread() {
			public void run() {
				while (true) {
					if (finalWalkWay != null) {
						if (!finalWalkWay.isEmpty()) {
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									walkD(finalWalkWay.getFirst());
									finalWalkWay.removeFirst();

								}
							});

							try {
								Thread.sleep(250);
							} catch (InterruptedException e) {

								e.printStackTrace();
							}
						}

					}

				}
			}
		};
		t.start();

	}

	public void walkD(Direction nextStepDirection) {

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
//		theadWalk.interrupt();
		freeze = true;
	}

	public void killedPlayer() {
		freeze = true;
	}

	public void notFreeze() {
		freeze = !freeze;
	}

}
