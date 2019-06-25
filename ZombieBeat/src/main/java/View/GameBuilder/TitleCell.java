package View.GameBuilder;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
	
	public class TitleCell extends ListCell<Title> {
		private Label titleLabel;
		private Label artistLabel;
		private HBox root;
		private ImageView cover;
		public Button coverbutton;
		public Title item;
		
		public TitleCell() {
			
			
			VBox infoPane = new VBox();
			titleLabel = new Label();
//			titleLabel.setStyle("-fx-text-fill: white");
			artistLabel = new Label();
//			artistLabel.setStyle("-fx-text-fill: white");
			infoPane.getChildren().addAll(titleLabel, artistLabel);
			
			cover = new ImageView();
			coverbutton = new Button();
		
			root = new HBox();
			root.getChildren().addAll(coverbutton, infoPane);
			root.setSpacing(10);
		}
		
		protected void updateItem(Title item, boolean empty) {
			super.updateItem(item, empty);
			this.item=item;
			if(!empty) {
				titleLabel.setText(item.getTitlename());
				artistLabel.setText(item.getInterpret());
				cover.setImage(item.getImage());
				cover.setFitHeight(50);
				cover.setFitWidth(50);
				coverbutton.setGraphic(cover);
				coverbutton.setOnMouseEntered(event -> {
					cover.setImage(new Image("File:playButton.png"));
					coverbutton.setGraphic(cover);  
	           
	            });
				coverbutton.setOnMouseExited(event -> {
					cover.setImage(item.getImage());
					coverbutton.setGraphic(cover);  
	            });
				
				this.setGraphic(root);
			} else {
				this.setGraphic(null);
			}
			
		}
}
