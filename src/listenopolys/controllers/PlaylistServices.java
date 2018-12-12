/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenopolys.controllers;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import listenopolys.models.Playlist;
import listenopolys.models.Track;

/**
 *
 * @author husoeur
 */
public class PlaylistServices {
    private ObservableList<Playlist> playlistList;
    
    public PlaylistServices(){
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
    
    public List<Track> getTracksFromPlaylist(String playlistTitle){
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
   
    
    public ObservableList<Playlist> getPlaylistList(){
        return playlistList;
    }
}
