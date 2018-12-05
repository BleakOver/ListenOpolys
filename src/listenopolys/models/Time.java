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
       if(seconds<0 || minutes<0 || hours<0){
           this.seconds=0;
           this.minutes=0;
           this.hours=0;
           return;
       }
       minutes+=seconds/60;
       seconds%=60;
       hours+=minutes/60;
       minutes%=60;
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
   
   public void addTime(Time t){
       seconds+=t.getSeconds();
       minutes+=t.getMinutes();
       hours+=t.getHours();
       minutes+=seconds/60;
       seconds%=60;
       hours+=minutes/60;
       minutes%=60;
   }
   
   public void removeTime(Time t){
       seconds-=t.getSeconds();
       minutes-=t.getMinutes();
       hours-=t.getHours();
       minutes-=seconds/60;
       seconds%=60;
       hours-=minutes/60;
       minutes%=60;
       if(hours<0){
           seconds=0;
           minutes=0;
           hours=0;
       }
   }
}
