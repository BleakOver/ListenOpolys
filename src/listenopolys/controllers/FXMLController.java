/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import listenopolys.models.*;
import listenopolys.models.PlaylistServices;

/**
 *
 * @author enmora
 */
public class FXMLController implements Initializable, TrackReaderListener {

    @FXML
    private ListView<Playlist> viewPlaylists;

    @FXML
    private ListView<Track> viewTracks;

    @FXML
    private Button buttonPlayPause;

    @FXML
    private Button buttonRepeat;

    private PlaylistServices playlists;
    private TrackReader reader;
    private boolean repeat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewPlaylists.setCellFactory(playLv -> new ListCell<Playlist>(){
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
        viewTracks.setCellFactory(trackLv -> new ListCell<Track>(){
            @Override
            public void updateItem(Track item, boolean empty){
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
        playlists = new PlaylistServices();
        viewPlaylists.setItems(playlists.getPlaylistList());
        playlists.addPlaylist(new Playlist("Hello World!"));
        playlists.getPlaylist("Hello World!").addTrack(new Track("test", "hugoladobe.wav", "rock", 1995, new Time(15, 15, 15)));
    }    


    public void viewPlaylistsClicked(){
        if(viewPlaylists.getSelectionModel().getSelectedItem() != null)
            viewTracks.setItems(viewPlaylists.getSelectionModel().getSelectedItem().getTracks());
    }

    public void viewTracksClicked(){
        if(viewTracks.getSelectionModel().getSelectedItem() != null && viewPlaylists.getSelectionModel().getSelectedItem() != null) {
            reader = new TrackReader(viewTracks.getSelectionModel().getSelectedItem(), repeat);
            reader.addListener(this);
        }
    }

    public void buttonPlayPauseClicked(){
        if(reader == null) return;
        if(reader.getStatus().equals("PAUSED")||reader.getStatus().equals("READY")||reader.getStatus().equals("STOPPED")){
            reader.play();
            buttonPlayPause.setText("Pause");
        }
        else if(reader.getStatus().equals("PLAYING")){
            reader.pause();
            buttonPlayPause.setText("Play");
        }
    }

    public void buttonStopClicked(){
        if(reader == null) return;
        reader.stop();
        buttonPlayPause.setText("Play");
    }

    public void endOfMedia(){
        if(!repeat) {
            buttonPlayPause.setText("Play");
            reader.stop();
        }
    }

    public void buttonRepeatClicked(){
        repeat = !repeat;
        buttonRepeat.setText((repeat) ? "Repeat: ON" : "Repeat: OFF");
        if(reader!=null) {
            reader.setRepeatTo(repeat);
        }
    }

}
