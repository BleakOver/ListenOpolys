/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.sun.media.jfxmedia.events.AudioSpectrumEvent;
import com.sun.media.jfxmedia.events.AudioSpectrumListener;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import listenopolys.models.*;
import listenopolys.models.PlaylistService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

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

    @FXML
    private AreaChart<String, Number> graph;

    private PlaylistService playlists;
    private TrackReader reader;
    private boolean repeat;
    private boolean random;
    private Timer timer;
    private List<Integer> randomList;
    private Playlist runningPlaylist;
    private int runningTrackIndex;
    private SpektrumListener spektrum;
    private int BANDS;
    private XYChart.Data[] graphData;

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
        BANDS = 128;
        XYChart.Series<String, Number> xyChart = new XYChart.Series<>();
        graphData = new XYChart.Data[BANDS + 2];
        for (int i = 0; i < graphData.length; i++) {
            graphData[i] = new XYChart.Data<>(Integer.toString(i + 1), 0);
            xyChart.getData().add(graphData[i]);
        }
        graph.getData().add(xyChart);
        ((NumberAxis)graph.getYAxis()).setUpperBound(70);
        graph.getYAxis().setAutoRanging(false);
        graph.getYAxis().setTickLabelsVisible(false);
        graph.getYAxis().setOpacity(0);
        graph.getXAxis().setTickLabelsVisible(false);
        graph.getXAxis().setOpacity(0);

        repeat=false;
        random=false;
        playlists = new LoaderFile().load();
        if(playlists==null) playlists = new PlaylistService();
        viewPlaylists.setItems((ObservableList<Playlist>)playlists.getPlaylistList());
        timer = new Timer();
    }


    /**
     * initialize the list-view of the tracks
     */
    public void viewPlaylistsClicked(){
        if(viewPlaylists.getSelectionModel().getSelectedItem() != null)
            viewTracks.setItems((ObservableList<Track>) viewPlaylists.getSelectionModel().getSelectedItem().getTracks());
    }

    /**
     * initialize everything for the selected song to be played
     */
    public void viewTracksClicked(){
        if(viewTracks.getSelectionModel().getSelectedItem() != null && viewPlaylists.getSelectionModel().getSelectedItem() != null) {
            if(reader!=null) {
                reader.stop();
                buttonPlayPause.setText("Play");
            }
            runningPlaylist = viewPlaylists.getSelectionModel().getSelectedItem();
            runningTrackIndex = viewTracks.getSelectionModel().getSelectedIndex();
            reader = new TrackReader(viewTracks.getSelectionModel().getSelectedItem(), repeat);
            reader.getPlayer().setVolume(sliderVolume.getValue() / 100.0);
            reader.addListener(this);
            timer.cancel();
            timer.purge();
            Duration dur = viewTracks.getSelectionModel().getSelectedItem().getDuration();
            labelTotalTime.setText(new DecimalFormat("00").format((int)(dur.toMinutes()))+":"+new DecimalFormat("00").format((int)(dur.toSeconds())%60));
            labelCurrentTime.setText("00:00");
            sliderMedia.setValue(0);
            reader.getPlayer().setAudioSpectrumListener(new SpektrumListener(reader, graphData, BANDS));
            reader.getPlayer().setAudioSpectrumNumBands(BANDS);
            reader.getPlayer().setAudioSpectrumThreshold(-80);
        }
    }

    /**
     * move to the selected time of the song
     */
    public void sliderMediaClickOut(){
        if(reader != null) {
            reader.getPlayer().seek(viewTracks.getSelectionModel().getSelectedItem().getDuration().multiply(sliderMedia.getValue() / 100.0));
            timer = new Timer();
            timer.schedule(new Updater(sliderMedia, labelCurrentTime, reader.getPlayer()), 0, 10);
        }
    }

    /**
     * stops the action of the timer
     */
    public void sliderMediaClickIn(){
        timer.cancel();
        timer.purge();
    }

    /**
     * pause or play the selected song
     */
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

    /**
     * stop the selected song
     */
    public void buttonStopClicked(){
        if(reader != null) {
            reader.stop();
            sliderMedia.setValue(0);
            labelCurrentTime.setText("00:00");
            buttonPlayPause.setText("Play");
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * perform the correct action when the song is over
     */
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

    /**
     * change the repetition mode of the song
     */
    public void buttonRepeatClicked(){
        repeat = !repeat;
        if(reader!=null) {
            reader.setRepeatTo(repeat);
        }
    }

    /**
     * randomize the list of the future songs to be played
     */
    private void randomizeRandomList() {
        randomList = new ArrayList<>();
        for (int i =0 ; i<viewTracks.getItems().size() ; i++) randomList.add(i);
        randomList.remove(viewTracks.getSelectionModel().getSelectedIndex());
        Collections.shuffle(randomList);
    }

    /**
     * change the random mode of the player
     */
    public void buttonRandomClicked(){
        random = !random;
        if(random){
            randomizeRandomList();
        }
    }

    /**
     * add a playlist to the player
     * @param playlist playlist to add
     */
    public void addPlaylist(Playlist playlist) {
        playlists.addPlaylist(playlist);
    }

    /**
     * select and play the previous song
     */
    public void buttonPreviousClicked() {
        if(reader != null){
            if(sliderMedia.getValue()>1){
                reader.getPlayer().seek(Duration.ZERO);
                return;
            }
            buttonStopClicked();
            viewPlaylists.getSelectionModel().select(runningPlaylist);
            viewPlaylistsClicked();
            viewTracks.getSelectionModel().select((runningTrackIndex>0) ? runningTrackIndex-1 : viewTracks.getItems().size()-1);
            viewTracksClicked();
            reader.getPlayer().setOnReady(this::buttonPlayPauseClicked);
        }
    }

    /**
     * select and play the next song
     */
    public void buttonNextClicked() {
        buttonStopClicked();
        viewPlaylists.getSelectionModel().select(runningPlaylist);
        viewPlaylistsClicked();
        viewTracks.getSelectionModel().select((runningTrackIndex+1 == viewTracks.getItems().size()) ? 0 : runningTrackIndex+1);
        viewTracksClicked();
        reader.getPlayer().setOnReady(this::buttonPlayPauseClicked);
    }

    /**
     * open a window to add your songs
     */
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

    /**
     * open a window to modify a playlist's name
     */
    public void buttonModifyPlaylistClicked(){
        if(viewPlaylists.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            Parent root;
            Scene scene;
            Stage stage;
            try {
                root = loader.load(getClass().getResource("../views/FXMLModif.fxml").openStream());
                scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("../style/list-style.css").toExternalForm());
                stage = new Stage();
                stage.setHeight(200);
                stage.setWidth(254);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                FXMLControllerModif controller = loader.getController();
                controller.setController(this);
                controller.setInitialValue(viewPlaylists.getSelectionModel().getSelectedItem().getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * update the title of a playlist
     * @param initialTitle initial title of the playlist to update
     * @param newTitle new title to set to the playlist
     */
    public void updatePlaylistTitle(String initialTitle, String newTitle){
        Playlist toUpdate = playlists.getPlaylist(initialTitle);
        toUpdate.setTitle(newTitle);
        viewPlaylists.refresh();
    }

    /**
     * open a window to add a new playlist
     */
    public void buttonAddPlaylistClicked(){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        Scene scene;
        Stage stage;
        try {
            root = loader.load(getClass().getResource("../views/FXMLAjout.fxml").openStream());
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../style/list-style.css").toExternalForm());
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

    /**
     * remove the selected song
     */
    public void buttonRemoveTrackClicked(){
        if(viewTracks.getSelectionModel().getSelectedItem()!=null){
            viewPlaylists.getSelectionModel().getSelectedItem().removeTrack(viewTracks.getSelectionModel().getSelectedItem().getFilePath());
        }
        viewTracksClicked();
    }

    /**
     * open a window to confirm the choice of deleting the selected playlist
     */
    public void buttonRemovePlaylistClicked(){
        if(viewPlaylists.getSelectionModel().getSelectedItem()!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Playlist removal");
            alert.setHeaderText("Are you sure to delete this playlist?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(viewPlaylists.getSelectionModel().getSelectedItem().equals(runningPlaylist)) {
                    viewTracks.setItems(null);
                    buttonStopClicked();
                    reader = null;
                }
                playlists.removePlaylist(viewPlaylists.getSelectionModel().getSelectedItem().getTitle());
                runningPlaylist = null;
            }
        }
        viewPlaylistsClicked();
    }

    private float[] createFilledBuffer(int size, float fillValue) {
        float[] floats = new float[size];
        Arrays.fill(floats, fillValue);
        return floats;
    }

    /**
     * close the timer and save the player's configuration
     */
    public void close(){
        timer.cancel();
        timer.purge();
        new SaverFile().save(playlists.getSerializable());
    }
}
