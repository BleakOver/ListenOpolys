/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.views;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Callback;
import listenopolys.controllers.PlaylistServices;
import listenopolys.models.Playlist;
import listenopolys.models.Time;
import listenopolys.models.Track;
import listenopolys.models.TrackReader;

/**
 *
 * @author husoeur
 */
public class View {
    
    PlaylistServices playlistService;
    TrackReader reader;
    ListView<Playlist> listPlaylists;    
    ListView<Track> listTracks;
   AddPlaylistView addView;
    Stage primaryStage;
   
    
    public View(Stage primaryStage){
    	this.primaryStage=primaryStage;
        playlistService= new PlaylistServices();
        playlistService.addPlaylist(new Playlist("Hello world!"));
        listPlaylists = new ListView<Playlist>();
        listPlaylists.setCellFactory(lv -> new ListCell<Playlist>(){
            @Override
            public void updateItem(Playlist item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    String text = item.getTitle();
                    setText(text);
                }
            }
        }
        );
        
        
        GridPane structure = new GridPane();
        Scene scene = new Scene(structure, 800, 600);
        
        GridPane menu = new GridPane();
        GridPane media = new GridPane();
        GridPane player = new GridPane();
        
        structure.add(menu, 0, 0, 1, 2);
        structure.add(media, 1, 0);
        structure.add(player, 1, 1);
        
        menu.setStyle("-fx-background-color: #0b5351;");
        media.setStyle("-fx-background-color: #01060a;");
        player.setStyle("-fx-background-color: #f4f1f2;");
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(65);
        structure.getColumnConstraints().addAll(col1, col2);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(80);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        structure.getRowConstraints().addAll(row1, row2);
        
        Button addPlaylistButton= new Button("Add New Playlist");
        addPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		
        		appelAddPlaylistView();

        	}
        });
        
        
        
        /*
        Track t = new Track("chopin", "/home/etud/enmora/Téléchargements/13632.wav", "classique", 1665, new Time(9, 17, 2));
        reader = new TrackReader(t);
        */
        
        Button playPauseButton = new Button("play");
       
        /*playPauseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(reader.getStatus().equals("pause")){
                    reader.play();
                    playPauseButton.setText("pause");
                }
                else if(reader.getStatus().equals("play")){
                    reader.pause();
                    playPauseButton.setText("play");
                }
            }
        });
        */
        Button stopButton = new Button("stop");
        /*
        stopButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                reader.stop();
            }
        });
        
        */
        listPlaylists.setItems(FXCollections.observableArrayList(playlistService.getPlaylistList()));
        
        player.add(playPauseButton, 0, 1);
        player.add(stopButton, 0, 0);
        player.add(addPlaylistButton, 0, 2);
        
        listPlaylists.prefWidthProperty().bind(menu.widthProperty());
        
        
        RowConstraints menu1 = new RowConstraints();
        menu1.setPercentHeight(50);
        menu.getRowConstraints().addAll(menu1, menu1);
        
        
        menu.add(listPlaylists, 0, 0);
        
        primaryStage.setTitle("ListenOpolys");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void appelAddPlaylistView() {
   	 new AddPlaylistView(primaryStage,  this);
   }
    
}
