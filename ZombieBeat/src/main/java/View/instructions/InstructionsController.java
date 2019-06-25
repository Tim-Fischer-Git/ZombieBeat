package View.instructions;

import properties.Properties;

public class InstructionsController {
	private Instructions control;
	
	public InstructionsController() {
		control = new Instructions();
		initialize();
	}
	
	private void initialize() {
		control.top.setOnAction(e-> {
			control.info.setText("UP Button :   " + Properties.TOP);
			control.top.setId("tmovec");
			control.left.setId("lmove");
			control.down.setId("dmove");
			control.right.setId("rmove");
			control.shoot.setId("shoot");
			control.top.setOnKeyPressed(event -> {
				control.top.setId("tmove");
				Properties.TOP = event.getCode().toString();
				control.info.setText("UP Button :   " + Properties.TOP);
				
			});
		});
		
		control.left.setOnAction(e-> {
			control.info.setText("LEFT Button :   " + Properties.LEFT);
			control.top.setId("tmove");
			control.left.setId("lmovec");
			control.down.setId("dmove");
			control.right.setId("rmove");
			control.shoot.setId("shoot");
			control.left.setOnKeyPressed(event -> {
				control.left.setId("lmove");
				Properties.LEFT = event.getCode().toString();
				control.info.setText("LEFT Button :   " + Properties.LEFT);
				
			});
		});
		
		control.down.setOnAction(e-> {
			control.info.setText("DOWN Button :   " + Properties.DOWN);
			control.top.setId("tmove");
			control.left.setId("lmove");
			control.down.setId("dmovec");
			control.right.setId("rmove");
			control.shoot.setId("shoot");
			control.down.setOnKeyPressed(event -> {
				control.down.setId("dmove");
				Properties.DOWN = event.getCode().toString();
				control.info.setText("DOWN Button :   " + Properties.DOWN);
				
			});
		});
		
		control.right.setOnAction(e-> {
			control.info.setText("RIGHT Button :   " + Properties.RIGHT);
			control.top.setId("tmove");
			control.left.setId("lmove");
			control.down.setId("dmove");
			control.right.setId("rmovec");
			control.shoot.setId("shoot");
			control.right.setOnKeyPressed(event -> {
				control.right.setId("rmove");
				Properties.RIGHT = event.getCode().toString();
				control.info.setText("RIGHT Button :   " + Properties.RIGHT);
				
			});
		});
		
		control.shoot.setOnAction(e-> {
			control.info.setText("SHOOT Button :   " + Properties.SHOOT);
			control.top.setId("tmove");
			control.left.setId("lmove");
			control.down.setId("dmove");
			control.right.setId("rmove");
			control.shoot.setId("shootc");
			control.shoot.setOnKeyPressed(event -> {
				control.shoot.setId("shoot");
				Properties.SHOOT = event.getCode().toString();
				control.info.setText("SHOOT Button :   " + Properties.SHOOT);
				
			});
		});
		
	}
	
	public Instructions getView() {
		return control;
	}
}
