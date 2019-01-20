package listenopolys.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javafx.scene.media.MediaPlayer;

import java.text.DecimalFormat;
import java.util.TimerTask;

/**
 * a class used to update the slider of the song
 */
public class Updater extends TimerTask {

    @FXML
    private Slider sliderMedia;

    @FXML
    private Label labelCurrentTime;

    private MediaPlayer mediaPlayer;

    private DecimalFormat decimalFormat;

    public Updater(Slider slider, Label label, MediaPlayer mediaPlayer){
        decimalFormat = new DecimalFormat("00");
        labelCurrentTime = label;
        sliderMedia = slider;
        this.mediaPlayer = mediaPlayer;
    }

    public void run(){
        Platform.runLater(()-> {
            sliderMedia.setValue((mediaPlayer.getCurrentTime().toMillis() * 100) / mediaPlayer.getTotalDuration().toMillis());
            labelCurrentTime.setText(decimalFormat.format((int) (mediaPlayer.getCurrentTime().toMinutes())) + ":" + decimalFormat.format((int) (mediaPlayer.getCurrentTime().toSeconds()) % 60));
        }
        );
    }

}
