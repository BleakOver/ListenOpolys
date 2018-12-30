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
import java.util.concurrent.TimeUnit;

/**
 *
 * @author husoeur
 */
public class Track {
   private String title;
   private String genre;
   private String filePath;
   private int year;
   private Duration duration;
   
   
   public Track(String title, String filePath, String genre, int year){
       this.genre=genre;
       this.filePath=filePath;
       this.title=title;
       this.year=year;
       File file = new File(filePath);
       /*AudioInputStream audio = null;
       try {
           audio = AudioSystem.getAudioInputStream(file);
       } catch (UnsupportedAudioFileException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       duration = new Duration((audio.getFrameLength()/audio.getFormat().getFrameRate()) * 1000);*/
       Media media = new Media(file.toURI().toString());
       MediaPlayer mp = new MediaPlayer(media);
       mp.setOnReady(()->{
           duration = mp.getTotalDuration();
       });

   }
   
   public int getYear(){
       return this.year;
   }
   
   public String getTitle(){
       return this.title;
   }
   
   public String getGenre(){
       return this.genre;
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
