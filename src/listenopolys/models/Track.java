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
