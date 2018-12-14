package listenopolys.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listenopolys.models.Playlist;

public class AddTrackView {
	
	public AddTrackView(Stage primaryStage, View view) {
		GridPane structAddTrack = new GridPane();
		TextArea newTrackName = new TextArea();
		Button addNewTrack= new Button("Add Track");
		
		
		Scene addTrackScene = new Scene(structAddTrack,500,500);
		Stage addTrack = new Stage();
		addTrack.setTitle("Add Track Window");
		
		addNewTrack.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if(newTrackName.getText() != null && !newTrackName.getText().isEmpty()){
	    			Playlist p = new Playlist(newTrackName.getText());
	    			view.playlistService.addPlaylist(p);
	    			
		}
	    		

	    	}
	    });
		
		addTrack.setX(primaryStage.getX()+100);
		addTrack.setY(primaryStage.getY()+200);
		
		structAddTrack.add(newTrackName, 0, 0);
		structAddTrack.add(addNewTrack, 0, 1);
		
		
		addTrack.setScene(addTrackScene);
		addTrack.show();
	}
	

	
}
