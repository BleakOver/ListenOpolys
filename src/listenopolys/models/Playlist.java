/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.util.Duration;


/**
 *
 * @author husoeur
 */
public class Playlist {
    private String title;
    private ObservableList<Track> playlist;
    
    public Playlist(String title){
        this.title=title;
        playlist=FXCollections.observableArrayList();
    }
   
    public void addTrack(Track t){
            playlist.add(t);
    }
    
    public void removeTrack(String filePath){
        for (Track t : playlist) {
            if(t.getFilePath().equals(filePath)){
                playlist.remove(t);
                return;
            }
        }
    }
    
    public ObservableList<Track> getTracks(){
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

    public boolean contains(Track track){
        return playlist.contains(track);
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
