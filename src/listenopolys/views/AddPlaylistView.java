package listenopolys.views;

public class AddPlaylistView {
	
	PlaylistServices playlistService;
	
	
	public AddPlaylistView(Stage primaryStage) {
		
		playlistService = new PlaylistServices();
		
		GridPane structAddPlaylist = new GridPane();
		
		Scene addPlaylistScene = new Scene(structAddPlaylist,300,200);
		Stage addPlaylist = new Stage();
		addPlaylist.setTitle("Add Playlist Window");
		
		addPlaylist.setX(primaryStage.getX() + 200);
		addPlaylist.setY(primaryStage.getY() + 100);
		
		
		addPlaylist.setScene(addPlaylistScene);
		addPlaylist.show();
		
	}
	
	

}
