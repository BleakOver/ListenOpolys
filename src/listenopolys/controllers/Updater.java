package listenopolys.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javafx.scene.media.MediaPlayer;
import java.util.TimerTask;

public class Updater extends TimerTask {

    @FXML
    private Slider sliderMedia;

    @FXML
    private Label labelCurrentTime;

    private MediaPlayer mediaPlayer;

    public Updater(Slider slider, Label label, MediaPlayer mediaPlayer){
        labelCurrentTime = label;
        sliderMedia = slider;
        this.mediaPlayer = mediaPlayer;
    }

    public void run(){
        Platform.runLater(()-> {
            sliderMedia.setValue((mediaPlayer.getCurrentTime().toMillis() * 100) / mediaPlayer.getTotalDuration().toMillis());
            labelCurrentTime.setText((int) (mediaPlayer.getCurrentTime().toMinutes()) + ":" + (int) (mediaPlayer.getCurrentTime().toSeconds()) % 60);
        }
        );
    }

}
