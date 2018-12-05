/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author husoeur
 */
public class Playlist {
    private String title;
    private Time duration;
    private List<Track> playlist;
    
    public Playlist(String title){
        this.title=title;
        this.duration=new Time(0,0,0);
        playlist=new ArrayList<>();
    }
   
    public void addTrack(Track t){
            playlist.add(t);
            duration.addTime(t.getDuration());
    }
    
    public void removeTrack(String filePath){
        for (Track t : playlist) {
            if(t.getFilePath().equals(filePath)){
                playlist.remove(t);
                return;
            }
        }
    }
    
    public List<Track> getTracks(){
        return playlist;
    }
    
    
    public Track getTrack(String filePath){
        for (Track t : playlist) {
            if(t.getFilePath().equals(filePath)){
                return t;
            }
        }
        return null;
    }
    
    public String getTitle(){
        return this.title;
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
        Playlist p = (Playlist) o;
        return title.equals(p.getTitle());
                
    }
}
