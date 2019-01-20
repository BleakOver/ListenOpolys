/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import listenopolys.models.Playlist;
import listenopolys.models.Track;

/**
 *
 * @author husoeur
 */
public class PlaylistService implements Serializable {
    private Collection<Playlist> playlistList;

    public PlaylistService(Collection<Playlist> collection){
        this.playlistList = collection;
    }

    public PlaylistService(){
       playlistList = FXCollections.observableArrayList();
    }
    
    
    public void addPlaylist(Playlist p){
        if(!playlistList.contains(p)){
            playlistList.add(p);
        }
        
    }
    
    public void removePlaylist(String playlistTitle){
        for (Playlist p : playlistList) {
            if(p.getTitle().equals(playlistTitle)){
                playlistList.remove(p);
                return;
            }
        }
    }
    
    public Collection<Track> getTracksFromPlaylist(String playlistTitle){
        for (Playlist p : playlistList) {
            if(p.getTitle().equals(playlistTitle)){
                return p.getTracks();
            }
        }
        return null;
    }
    
    public Playlist getPlaylist(String title){
        for (Playlist p : playlistList) {
            if(p.getTitle().equals(title)){
                return p;
            }
        }
        return null;
    }
   
    
    public Collection<Playlist> getPlaylistList(){
        return playlistList;
    }

    /**
     * @return playlist service serializable
     */
    public PlaylistService getSerializable(){
        Collection<Playlist> collectionSave = FXCollections.observableArrayList(playlistList);
        playlistList = new ArrayList<>(playlistList);
        for(int i = 0; i<playlistList.size(); i++){
            playlistList.add(((ArrayList<Playlist>)playlistList).get(i).getSerializable());
            ((ArrayList<Playlist>)playlistList).remove(i);
        }
        try {
            return new PlaylistService(playlistList);
        }
        finally {
            playlistList = collectionSave;
        }
    }

    /**
     * set observable
     */
    public void setNotSerializable() {
        playlistList = FXCollections.observableArrayList(playlistList);
        for (Playlist p : playlistList){
            p.setNotSerializable();
        }
    }
}
