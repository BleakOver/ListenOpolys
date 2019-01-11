/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import listenopolys.models.*;
import listenopolys.models.PlaylistService;

/**
 *
 * @author enmora
 */
public class FXMLController implements Initializable, TrackReaderListener {

    @FXML
    private Slider sliderMedia;

    @FXML
    private Slider sliderVolume;

    @FXML
    private ListView<Playlist> viewPlaylists;

    @FXML
    private ListView<Track> viewTracks;

    @FXML
    private Button buttonPlayPause;

    @FXML
    private ToggleButton buttonRepeat;

    @FXML
    private ToggleButton buttonRandom;

    @FXML
    private Label labelTotalTime;

    @FXML
    private Label labelCurrentTime;

    private PlaylistService playlists;
    private TrackReader reader;
    private boolean repeat;
    private boolean random;
    private Timer timer;
    private List<Integer> randomList;

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
        sliderVolume.valueProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable observable){
                if(reader != null){
                    reader.getPlayer().setVolume(sliderVolume.getValue() / 100.0);
                }
            }
        });
        repeat=false;
        random=false;
        playlists = new LoaderFile().load();
        if(playlists==null) playlists = new PlaylistService();
        viewPlaylists.setItems((ObservableList<Playlist>)playlists.getPlaylistList());
        timer = new Timer();
    }


    public void viewPlaylistsClicked(){
        if(viewPlaylists.getSelectionModel().getSelectedItem() != null)
            viewTracks.setItems((ObservableList<Track>) viewPlaylists.getSelectionModel().getSelectedItem().getTracks());
    }

    public void viewTracksClicked(){
        if(viewTracks.getSelectionModel().getSelectedItem() != null && viewPlaylists.getSelectionModel().getSelectedItem() != null) {
            if(reader!=null) {
                reader.stop();
                buttonPlayPause.setText("Play");
            }
            reader = new TrackReader(viewTracks.getSelectionModel().getSelectedItem(), repeat);
            reader.getPlayer().setVolume(sliderVolume.getValue() / 100.0);
            reader.addListener(this);
            timer.cancel();
            timer.purge();
            Duration dur = viewTracks.getSelectionModel().getSelectedItem().getDuration();
            labelTotalTime.setText((int)(dur.toMinutes())+":"+(int)(dur.toSeconds())%60);
            labelCurrentTime.setText("0:0");
            sliderMedia.setValue(0);
        }
    }

    public void sliderMediaClickOut(){
        if(reader != null) {
            reader.getPlayer().seek(viewTracks.getSelectionModel().getSelectedItem().getDuration().multiply(sliderMedia.getValue() / 100.0));
            timer = new Timer();
            timer.schedule(new Updater(sliderMedia, labelCurrentTime, reader.getPlayer()), 0, 10);
        }
    }

    public void sliderMediaClickIn(){
        timer.cancel();
        timer.purge();
    }

    public void buttonPlayPauseClicked(){
        if(reader == null) return;
        if(reader.getStatus().equals("PAUSED")||reader.getStatus().equals("READY")||reader.getStatus().equals("STOPPED")){
            reader.play();
            buttonPlayPause.setText("Pause");
            timer.cancel();
            timer.purge();
            timer = new Timer();
            timer.schedule(new Updater(sliderMedia, labelCurrentTime, reader.getPlayer()), 0, 10);
        }
        else if(reader.getStatus().equals("PLAYING")){
            reader.pause();
            buttonPlayPause.setText("Play");
            timer.cancel();
            timer.purge();
        }
    }

    public void buttonStopClicked(){
        if(reader != null) {
            reader.stop();
            sliderMedia.setValue(0);
            labelCurrentTime.setText("0:0");
            buttonPlayPause.setText("Play");
            timer.cancel();
            timer.purge();
        }
    }

    public void endOfMedia() {
        if (!repeat) {
            timer.cancel();
            timer.purge();
            buttonPlayPause.setText("Play");
            reader.stop();
            int nextIndex;
            if(random){
                if(randomList.isEmpty()){
                    randomizeRandomList();
                }
                nextIndex = randomList.remove(0);
            }
            else
                nextIndex = (viewTracks.getSelectionModel().getSelectedIndex() + 1 >= viewTracks.getItems().size()) ? 0 : viewTracks.getSelectionModel().getSelectedIndex() + 1;
            viewTracks.scrollTo(nextIndex);
            viewTracks.getSelectionModel().select(nextIndex);
            viewTracks.getFocusModel().focus(nextIndex);
            viewTracksClicked();
            reader.getPlayer().setOnReady(() -> {
                        buttonPlayPauseClicked();
                    }
            );
        }
    }

    public void buttonRepeatClicked(){
        repeat = !repeat;
        if(reader!=null) {
            reader.setRepeatTo(repeat);
        }
    }

    private void randomizeRandomList() {
        randomList = new ArrayList<>();
        for (int i =0 ; i<viewTracks.getItems().size() ; i++) randomList.add(i);
        randomList.remove(viewTracks.getSelectionModel().getSelectedIndex());
        Collections.shuffle(randomList);
    }

    public void buttonRandomClicked(){
        random = !random;
        if(random){
            randomizeRandomList();
        }
    }

    public void addPlaylist(Playlist playlist) {
        playlists.addPlaylist(playlist);
    }

    public void buttonAddTrackClicked(){
        if(viewPlaylists.getSelectionModel().getSelectedItem() != null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select tracks");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+"\\Music"));
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Audio(.mp3, .wav, .aac)", "*.mp3", "*.wav", "*.aac")
            );
            List<File> fileList = fileChooser.showOpenMultipleDialog(viewTracks.getScene().getWindow());
            if(fileList!=null) {
                for (File file : fileList) {
                    viewPlaylists.getSelectionModel().getSelectedItem().addTrack(new Track(file.getPath()));
                }
            }
        }
    }

    public void buttonAddPlaylistClicked(){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        Scene scene;
        Stage stage;
        try {
            root = loader.load(getClass().getResource("../views/FXMLAjout.fxml").openStream());
            scene = new Scene(root);
            stage = new Stage();
            stage.setHeight(200);
            stage.setWidth(254);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            FXMLControllerAjout controller = loader.getController();
            controller.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonRemoveTrackClicked(){
        if(viewTracks.getSelectionModel().getSelectedItem()!=null){
            viewPlaylists.getSelectionModel().getSelectedItem().removeTrack(viewTracks.getSelectionModel().getSelectedItem().getFilePath());
        }
        viewTracksClicked();
    }

    public void close(){
        timer.cancel();
        timer.purge();
        new SaverFile().save(playlists.getSerializable());
    }

}
