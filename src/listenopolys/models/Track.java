/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;

/**
 *
 * @author husoeur
 */
public class Track {
   private String title;
   private String genre;
   private int year;
   private Time duration;
   
   
   public Track(String title, String genre, int year, Time duration){
       this.genre=genre;
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
   
  
}
