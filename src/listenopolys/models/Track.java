/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author husoeur
 */
public class Track {
   private String title;
   private String genre;
   private String filePath;
   private int year;
   private Time duration;
   
   
   public Track(String title, String filePath, String genre, int year, Time duration){
       this.genre=genre;
       this.filePath=filePath;
       this.title=title;
       this.year=year;
       this.duration=duration;
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
   
   public Time getDuration(){
       return this.duration;
   }
   
   public String getFilePath(){
       return this.filePath;
   }
   
  
}
