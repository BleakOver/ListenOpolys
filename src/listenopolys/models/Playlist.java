/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author husoeur
 */
public class Playlist implements Serializable {
    private String title;
    private Collection<Track> playlist;

    public Playlist(String title, Collection<Track> collection){
        this.title = title;
        this.playlist = collection;
    }

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
    
    public Collection<Track> getTracks(){
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

    public Playlist getSerializable(){
        Collection<Track> collectionSave = FXCollections.observableArrayList(playlist);
        try {
            return new Playlist(this.title, new ArrayList<>(this.playlist));
        }
        finally {
            playlist = collectionSave;
        }
    }

    public void setNotSerializable(){
        playlist = FXCollections.observableArrayList(playlist);
    }
}
