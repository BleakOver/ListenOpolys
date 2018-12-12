/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author enmora
 */
public class TrackReader {
    
    private Media music;
    private MediaPlayer player;
    
    public TrackReader(Track track){
        File file = new File(track.getFilePath());
        music = new Media(file.toURI().toString());
        player = new MediaPlayer(music);
        
    }
    
    public void pause(){
        player.pause();
    }
           
    public void play(){
        player.play();
    }
    
    public void stop(){
        player.stop();
    }
    
    public String getStatus(){
        return player.getStatus().toString();
    }
    
}
