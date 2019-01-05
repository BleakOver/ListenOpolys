/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author husoeur
 */
public class Track implements Serializable {
   private String title;
   private String filePath;
   private Duration duration;
   
   
   public Track( String filePath){
       this.filePath=filePath;
       File file = new File(filePath);
       title=file.getName();
       Media media = new Media(file.toURI().toString());
       MediaPlayer mp = new MediaPlayer(media);
       mp.setOnReady(()->{
           duration = mp.getTotalDuration();
       });

   }
   
   public String getTitle(){
       return this.title;
   }
   
   public Duration getDuration(){
       return this.duration;
   }
   
   public String getFilePath(){
       return this.filePath;
   }
   
   public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(this==o){
            return true;
        }
        if(this.getClass()!=o.getClass()){
            return false;
        }
        Track t = (Track) o;
        return filePath.equals(t.getFilePath());
                
    }
  
}
