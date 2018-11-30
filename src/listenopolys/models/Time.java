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
public class Time {
    private int seconds;
    private int minutes;
    private int hours;
   
   public Time (int seconds, int minutes, int hours){
       this.seconds=seconds;
       this.minutes=minutes;
       this.hours=hours;
   }
   
   public int getSeconds(){
       return this.seconds;
   }
   public int getMinutes(){
       return this.minutes;
   }
   public int getHours(){
       return this.hours;
   }
}
